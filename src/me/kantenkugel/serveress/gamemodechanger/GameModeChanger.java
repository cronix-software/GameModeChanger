package me.kantenkugel.serveress.gamemodechanger;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class GameModeChanger extends JavaPlugin {
	
	public static GameModeChanger plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		PluginDescriptionFile pdf = this.getDescription();
		logger.info("[" + pdf.getName() + "] v" + pdf.getVersion() + " is enabled!");
		
	}
	
	public void onDisable() {
		PluginDescriptionFile pdf = this.getDescription();
		logger.info("[" + pdf.getName() + "] is disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		if(sender == getServer().getConsoleSender()) {
			 return consoleexecute(cmdlabel, args);
		}
		Player player = (Player) sender;
		
		if(cmdlabel.equalsIgnoreCase("gm")) {
			if(!player.hasPermission("gamemodechanger.use")) {
				player.sendMessage(ChatColor.RED + "Your not allowed to do that!");
				return true;
			}
			if(args.length == 1 && args[0] != "help") {
				if(player.hasPermission("gamemodechanger.other")) {
					Player target = getServer().getPlayer(args[0]);
					if(target != null) changegamemode(target, player);
					else player.sendMessage(ChatColor.RED + "No player with such name found!");
				} else {
					player.sendMessage(ChatColor.RED + "Your not allowed to change others Gamemode!");
				}
			} else if(args.length > 1 || (args.length == 1 && args[0] == "help")) {
				return false;
			} else {
				changegamemode(player, null);
			}
			return true;
			
		} else if(cmdlabel.equalsIgnoreCase("getgm")) {
			if(!player.hasPermission("gamemodechanger.get")) {
				player.sendMessage(ChatColor.RED + "Your not allowed to do that!");
				return true;
			}
			if(args.length == 1) {
				Player target = getServer().getPlayer(args[0]);
				if(target == null) player.sendMessage(ChatColor.RED + "No player with such name found!");
				else player.sendMessage("The Gamemode of " + target.getDisplayName() + " is " + target.getGameMode().toString());
				return true;
			} else return false;
			
		} else return false;
	}
	
	public void changegamemode(Player target, Player refunder) {
		if(refunder == null) {
			if(target.getGameMode() == GameMode.CREATIVE) {
				target.setGameMode(GameMode.SURVIVAL);
				target.sendMessage(ChatColor.GOLD + "Your Gamemode has been changed to SURVIVAL!");
			} else {
				target.setGameMode(GameMode.CREATIVE);
				target.sendMessage(ChatColor.GOLD + "Your Gamemode has been changed to CREATIVE!");
			}
		} else {
			if(target.getGameMode() == GameMode.CREATIVE) {
				target.setGameMode(GameMode.SURVIVAL);
				target.sendMessage(ChatColor.GOLD + "Your Gamemode has been changed to SURVIVAL by " + refunder.getDisplayName() + "!");
				refunder.sendMessage(ChatColor.GOLD + "The Gamemode of " + target.getDisplayName() + " has been changed to SURVIVAL!");
			} else {
				target.setGameMode(GameMode.CREATIVE);
				target.sendMessage(ChatColor.GOLD + "Your Gamemode has been changed to CREATIVE by " + refunder.getDisplayName() + "!");
				refunder.sendMessage(ChatColor.GOLD + "The Gamemode of " + target.getDisplayName() + " has been changed to CREATIVE!");
			}
		}
	}
	
	public boolean consoleexecute(String cmd, String[] args) {
		if(args.length != 1 || (args.length == 1 && args[0] == "help")) {
			return false;
		} else {
			Player target = getServer().getPlayer(args[0]);
			if(target == null) {
				logger.info("Cant find a Player with " + args[0] + " in his name!");
			} else if(cmd.equalsIgnoreCase("gm")) {
				changegamemode(target, null);
				logger.info("Gamemode of " + target.getDisplayName() + " changed!");
			} else if(cmd.equalsIgnoreCase("getgm")) {
				logger.info("Gamemode of " + target.getDisplayName() + " is: " + target.getGameMode().toString());
			}
			return true;
		}
	}
}
