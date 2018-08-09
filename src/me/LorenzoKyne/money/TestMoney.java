package me.LorenzoKyne.money;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TestMoney extends JavaPlugin implements Listener {

	/**
	 * Hash map which contains players and their relative money.
	 */
	private static ConcurrentHashMap<String, Integer> players = new ConcurrentHashMap<String, Integer>();

	@Override
	public void onEnable() {
		try {
			FileInputStream inFile = new FileInputStream("money.dat");
			ObjectInputStream in = new ObjectInputStream(inFile);
			players = (ConcurrentHashMap<String, Integer>) in.readObject();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getLogger().info(ChatColor.GREEN + "Money Enabled!");

	}

	@Override
	public void onDisable() {
		try {
			FileOutputStream outFile = new FileOutputStream("money.dat");
			ObjectOutputStream out = new ObjectOutputStream(outFile);
			out.writeObject(players);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@EventHandler
	public void join(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!players.containsKey(player.getDisplayName())) {
			players.put(player.getDisplayName(), 10000);
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("money")) {
				player.sendMessage(ChatColor.GOLD + "Hai " + players.get((player.getDisplayName())) + " euro!");
			} else if (cmd.getName().equalsIgnoreCase("pay") && args.length == 2) {
				String receiverName = args[0];
				if (receiverName != player.getDisplayName()) {
					int amount = Integer.parseInt(args[1]);
					Player receiver = getServer().getPlayer(receiverName);
					if (receiver != null && receiver.isOnline()) {
						if (pay(player, amount)) {
							player.sendMessage(ChatColor.RED + "Hai inviato " + amount + " euro a " + receiverName);
							receive(receiver, amount);
							receiver.sendMessage(ChatColor.DARK_GREEN + "Hai ricevuto " + amount + " euro da "
									+ player.getDisplayName());
						}
					} else {
						player.sendMessage(ChatColor.DARK_RED + "Il giocatore non e' online!");
					}
				} else {
					player.sendMessage(ChatColor.DARK_RED + "Non puoi inviare soldi a te stesso!");
				}
			} else {
				return false;
			}
			return true;
		}
		return false;
	};

	public static boolean pay(Player p, int amount) {
		int initial = players.get(p.getDisplayName());
		if (initial - amount >= 0) {
			players.put(p.getDisplayName(), initial - amount);
			return true;
		} else {
			p.sendMessage(ChatColor.DARK_RED + "Non hai abbastanza denaro!");
			return false;
		}
	}

	public static void receive(Player p, int amount) {
		int initialReceiver = players.get(p.getDisplayName());
		players.put(p.getDisplayName(), initialReceiver + amount);
	}

	public static int getMoney(Player p) {
		return players.get(p.getDisplayName());
	}
}
