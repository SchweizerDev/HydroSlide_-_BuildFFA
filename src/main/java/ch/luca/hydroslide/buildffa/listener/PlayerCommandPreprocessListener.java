package ch.luca.hydroslide.buildffa.listener;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void UnknownCommand(PlayerCommandPreprocessEvent e) {
        if(!e.isCancelled()) {
            Player p = e.getPlayer();
            String msg = e.getMessage().split(" ")[0];
            HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(msg);
            if(topic == null) {
                p.sendMessage(BuildFFA.getInstance().getPrefix() + "§cDieser Befehl wurde nicht gefunden. Nutze §e/help");
                e.setCancelled(true);
            }
        }
    }
}
