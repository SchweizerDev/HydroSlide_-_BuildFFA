package ch.luca.hydroslide.buildffa.util;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;

import org.bukkit.entity.Player;

public class PacketScoreboard {

	/**
     * Scoreboard owner.
     */
    private Player player;
    /**
     * Saves all lines, displayed on the scoreboard.
     */
    private ConcurrentHashMap<Integer, String> lines;
    /**
     * Scoreboard shown or not shown.
     */
    private boolean displayed;

    /**
     * Creates new board for a certain player.
     *
     * @param player Player, owner of the scoreboard.
     */
    public PacketScoreboard ( Player player) {
        // fills values
        this.player = player;
        this.lines = new ConcurrentHashMap<>();
        this.displayed = false;

    }

    /**
     * Sends the scoreboard for the first time.
     * Can also be sent but not displayed, if no lines are set.
     *
     * @param displayedName Head-title of the scoreboard.
     */
	public void sendSidebar( String displayedName ) {
        // checks wether it already is displayed or not to prevent exceptions
        if( displayed )
            return;
        remove();
        // visibility check
        // scoreboard packet
        PacketContainer scoreboard = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        scoreboard.getStrings()
                .write(0, "sideboard")
                .write(1, displayedName);
        scoreboard.getIntegers()
                .write(0, 0);
        scoreboard.getSpecificModifier(IScoreboardCriteria.EnumScoreboardHealthDisplay.class)
                .write(0, IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
        // display packet
        PacketContainer playOut = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
        playOut.getIntegers()
                .write(0, 1);
        playOut.getStrings()
                .write(0, "sideboard");
        // packet sending
        try {
            ProtocolLibrary.getProtocolManager( ).sendServerPacket(this.player, scoreboard);
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, playOut);
            displayed = true;
        } catch( InvocationTargetException e ) {
            e.printStackTrace();
            displayed = false;
        }
    }

    /**
     * Removes the whole scoreboard and makes it unsent.
     */
    public void remove() {
        // checks wether it already is displayed or not to prevent exceptions
        // packet to remove the scoreboard
        PacketContainer unsend = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        unsend.getStrings()
                .write( 0, "sideboard" )
                .write(1, "");
        unsend.getIntegers( )
                .write(0, 1);
        // packet sending
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, unsend);
        } catch( InvocationTargetException e ) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a certain line on the scoreboard to a text value.
     *
     * @param line Line to be set.
     * @param text Text to be set.
     */
    public void setLine( Integer line, String text ) {
        // checks wether it already is displayed or not to prevent exceptions
        if( !displayed )
            return;
        // checks if this line already is set
        removeLine(line);

        // shortens line-text to 1.8 compatible length
        if( text.length() > 40 )
            text = text.substring(0, 40);
        // score packet to be sent
        PacketContainer score = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
        score.getStrings()
                .write(0, text)
                .write(1, "sideboard");
        score.getIntegers( )
                .write(0, line);
        score.getSpecificModifier( PacketPlayOutScoreboardScore.EnumScoreboardAction.class )
                .write(0, PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE);
        // packet sending
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, score);
            lines.put( line, text );
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a certain line from the scoreboard.
     * Only gets removed if it is displayed.
     *
     * @param line Line to be removed.
     */

    public void removeLine( Integer line ) {
        // checks wether it already is displayed or not to prevent exceptions
        if( !displayed )
            return;
        // checks if this line already is set
        if( !lines.containsKey(line))
            return;
        // gets displayed text at this line
        String text = lines.get(line);
        // remove player score packet
        PacketContainer score = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
        score.getStrings()
                .write(0, text)
                .write(1, "sideboard");
        score.getIntegers()
                .write(0, line);
        score.getSpecificModifier(PacketPlayOutScoreboardScore.EnumScoreboardAction.class)
                .write(0, PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE);
        // sending packet
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, score);
            lines.remove( line );
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sets the head-title of the scoreboard and sends it to the player.
     *
     * @param displayedName New head-title.
     */
    public void setName( String displayedName ) {
        // checks wether it already is displayed or not to prevent exceptions
        if( !displayed )
            return;
        // scoreboard packet
        PacketContainer scoreboard = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        scoreboard.getStrings()
                .write(0, "sideboard")
                .write(1, displayedName);
        scoreboard.getIntegers()
                .write(0, 2);
        // packet sending
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, scoreboard);
            displayed = true;
        } catch( InvocationTargetException e ) {
            e.printStackTrace();
            displayed = false;
        }
    }

    /**
     * Gets a certain line on the scoreboard.
     *
     * @param line Line to be returned.
     * @return Text, displayed at certain line.
     */
    public String getLine( Integer line ) {
        return this.lines.get(line);
    }
}
