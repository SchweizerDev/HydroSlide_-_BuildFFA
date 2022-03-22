package ch.luca.hydroslide.buildffa.util;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ActionbarAPI {
	
	  public static void send(Player p, String Nachricht) {
	    String NachrichtNeu = Nachricht.replace("_", " ");
	    String s = ChatColor.translateAlternateColorCodes('&', NachrichtNeu);
	    IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + s + 
	      "\"}");
	    PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
	  }
  }