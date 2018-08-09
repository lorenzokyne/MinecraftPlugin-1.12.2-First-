package me.LorenzoKyne.test;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstPlugin extends JavaPlugin {

	private static FirstPlugin instance;

	@Override
	public void onEnable() {
		instance = this;
		getLogger().info("Hello, onEnable enabled!");
	}

	@Override
	public void onDisable() {

	}

	public static FirstPlugin getInstance() {
		return instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("hello")) {
				player.sendMessage("Hello, " + player.getName() + "!");
			} else if (cmd.getName().equalsIgnoreCase("creative")) {
				player.setGameMode(GameMode.CREATIVE);
				player.sendMessage("Modalita' cambiata in CREATIVA");
			} else if (cmd.getName().equalsIgnoreCase("sopravvivenza")) {
				player.setGameMode(GameMode.SURVIVAL);
				player.sendMessage("Modalita' cambiata in SOPRAVVIVENZA");
			}
		}
		return false;
	}
}
