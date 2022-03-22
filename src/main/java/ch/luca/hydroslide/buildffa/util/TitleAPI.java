package ch.luca.hydroslide.buildffa.util;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class TitleAPI implements Listener {
	
public static void all(Player p, String tmsg, String submsg, String actionmsg, int ein, int stay, int out) {
  try {
	  
    IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(tmsg);
    IChatBaseComponent smsg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(submsg);
    IChatBaseComponent amsg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(actionmsg);
    
    PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, msg, ein, stay, out);
    PacketPlayOutTitle time1 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, smsg, ein, stay, out);
    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, msg);
    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, smsg);
    PacketPlayOutChat atitle = new PacketPlayOutChat(amsg, (byte)2);
    
    CraftPlayer pl = (CraftPlayer)p;
    
    pl.getHandle().playerConnection.sendPacket(time);
    pl.getHandle().playerConnection.sendPacket(time1);
    pl.getHandle().playerConnection.sendPacket(title);
    pl.getHandle().playerConnection.sendPacket(subtitle);
    pl.getHandle().playerConnection.sendPacket(atitle);
  }
  catch (Exception localException) {}
}

public static void titleandsubtitle(Player p, String tmsg, String submsg, int ein, int stay, int out)
{
  try
  {
    IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(tmsg);
    IChatBaseComponent smsg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(submsg);
    
    PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, msg, ein, stay, out);
    PacketPlayOutTitle time1 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, smsg, ein, stay, out);
    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, msg);
    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, smsg);
    
    CraftPlayer pl = (CraftPlayer)p;
    
    pl.getHandle().playerConnection.sendPacket(time);
    pl.getHandle().playerConnection.sendPacket(time1);
    pl.getHandle().playerConnection.sendPacket(title);
    pl.getHandle().playerConnection.sendPacket(subtitle);
  }
  catch (Exception localException) {}
}

public static void title(Player p, String tmsg, int ein, int stay, int out)
{
  try
  {
    IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(tmsg);
    IChatBaseComponent smsg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(" ");
    
    PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, msg, ein, stay, out);
    PacketPlayOutTitle time1 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, smsg, ein, stay, out);
    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, msg);
    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, smsg);
    
    CraftPlayer pl = (CraftPlayer)p;
    
    pl.getHandle().playerConnection.sendPacket(time);
    pl.getHandle().playerConnection.sendPacket(time1);
    pl.getHandle().playerConnection.sendPacket(title);
    pl.getHandle().playerConnection.sendPacket(subtitle);
  }
  catch (Exception localException) {}
}

public static void subtitle(Player p, String submsg, int ein, int stay, int out)
{
  try
  {
    IChatBaseComponent msg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(" ");
    IChatBaseComponent smsg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(submsg);
    
    PacketPlayOutTitle time = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, msg, ein, stay, out);
    PacketPlayOutTitle time1 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, smsg, ein, stay, out);
    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, msg);
    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, smsg);
    
    CraftPlayer pl = (CraftPlayer)p;
    
    pl.getHandle().playerConnection.sendPacket(time);
    pl.getHandle().playerConnection.sendPacket(time1);
    pl.getHandle().playerConnection.sendPacket(title);
    pl.getHandle().playerConnection.sendPacket(subtitle);
  }
  catch (Exception localException) {}
}

public static void actionbar(Player p, String actionmsg)
{
  try
  {
    IChatBaseComponent amsg = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(actionmsg);
    
    PacketPlayOutChat atitle = new PacketPlayOutChat(amsg, (byte)2);
    
    CraftPlayer pl = (CraftPlayer)p;
    
    pl.getHandle().playerConnection.sendPacket(atitle);
  }
  catch (Exception localException) {}

}
}