package ch.luca.hydroslide.buildffa.commands;

import ch.luca.hydroslide.buildffa.BuildFFA;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {

    private BuildFFA buildFFA;

    public Gamemode(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(buildFFA.getNoPlayer());
            return true;
        }
        Player p = (Player)sender;
        if(!p.hasPermission("buildffa.gamemode")) {
            p.sendMessage(buildFFA.getNoPermission());
            return true;
        }
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("0")) {
                p.sendMessage(buildFFA.getPrefix() + "Du bist nun im §3Überlebensmodus§7.");
                p.setGameMode(GameMode.SURVIVAL);
            } else if(args[0].equalsIgnoreCase("1")) {
                p.sendMessage(buildFFA.getPrefix() + "Du bist nun im §3Kreativmodus§7.");
                p.setGameMode(GameMode.CREATIVE);
            } else if(args[0].equalsIgnoreCase("2")) {
                p.sendMessage(buildFFA.getPrefix() + "Du bist nun im §3Erkundungsmodus§7.");
                p.setGameMode(GameMode.ADVENTURE);
            } else if(args[0].equalsIgnoreCase("3")) {
                p.sendMessage(buildFFA.getPrefix() + "Du bist nun im §3Zuschauermodus§7.");
                p.setGameMode(GameMode.SPECTATOR);
            }
        } else if(args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                p.sendMessage(buildFFA.getPlayerNotOnline());
                return true;
            }
            if(args[0].equalsIgnoreCase("0")) {
                p.sendMessage(buildFFA.getPrefix() + "§b" + target.getName() + " §7ist nun im §3Überlebensmodus§7.");
                target.sendMessage(buildFFA.getPrefix() + "Du bist nun im §3Überlebensmodus§7.");
                target.setGameMode(GameMode.SURVIVAL);
            } else if(args[0].equalsIgnoreCase("1")) {
                p.sendMessage(buildFFA.getPrefix() + "§b" + target.getName() + " §7ist nun im §3Kreativmodus§7.");
                target.sendMessage(buildFFA.getPrefix() + "Du bist nun im §3Kreativmodus§7.");
                target.setGameMode(GameMode.CREATIVE);
            } else if(args[0].equalsIgnoreCase("2")) {
                p.sendMessage(buildFFA.getPrefix() + "§b" + target.getName() + " §7ist nun im §3Erkundungsmodus§7.");
                target.sendMessage(buildFFA.getPrefix() + "Du bist nun im §3Erkundungsmodus§7.");
                target.setGameMode(GameMode.ADVENTURE);
            } else if(args[0].equalsIgnoreCase("3")) {
                p.sendMessage(buildFFA.getPrefix() + "§b" + target.getName() + " §7ist nun im §3Zuschauermodus§7.");
                target.sendMessage(buildFFA.getPrefix() + "Du bist nun im §3Zuschauermodus§7.");
                target.setGameMode(GameMode.SPECTATOR);
            }
        } else {
            p.sendMessage(buildFFA.getPrefixUse() + "gamemode <0|1|2|3> [Spieler]");
        }
        return false;
    }
}