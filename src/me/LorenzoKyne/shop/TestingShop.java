package me.LorenzoKyne.shop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.LorenzoKyne.money.TestMoney;

public class TestingShop extends JavaPlugin implements Listener {

 
	/**
	 * Hash map contenente gli item e i relativi prezzi.
	 */
	private static ConcurrentHashMap<String, Integer> items = new ConcurrentHashMap<String, Integer>();
	/**
	 * flag che indica se i prezzi sono in qualche modo variati, serve a capire se
	 * salvare le modifiche o meno.
	 */
	private static boolean changed = false;
/*
	public void onEnable() {
		try {
			FileInputStream inFile = new FileInputStream("prices.dat");
			ObjectInputStream in = new ObjectInputStream(inFile);
			items = (ConcurrentHashMap<String, Integer>) in.readObject();
			in.close();
		} catch (IOException e) {
			inizializzaPrezzi();
			changed = true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Shop plugin loaded!");
	}

	@Override
	public void onDisable() {
		if (changed) {
			try {
				FileOutputStream outFile = new FileOutputStream("prices.dat");
				ObjectOutputStream out = new ObjectOutputStream(outFile);
				out.writeObject(items);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void SignClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) { // This checks if the player is right clicking on the
																	// sign and it'll do all the code below if it is a
																	// sign.
				Sign sign = (Sign) e.getClickedBlock().getState();
				if (items.containsKey(sign.getLine(0))) {
					buy(p, Material.matchMaterial(sign.getLine(0)));
				}
			}
		} else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) { // This checks if the player is right clicking on the
				// sign and it'll do all the code below if it is a
				// sign.
				Sign sign = (Sign) e.getClickedBlock().getState();
				if (items.containsKey(sign.getLine(0))) {
					sell(p, Material.matchMaterial(sign.getLine(0)));
				}
			}
		}
	}

	public static void sell(Player p, Material it) {
		if (p.getInventory().containsAtLeast(new ItemStack(it), 1)) {
			p.getInventory().removeItem(new ItemStack(it, 1));
			TestMoney.ricevi(p, items.get(it.name()));
			p.sendMessage(ChatColor.GREEN + "Hai ricevuto " + items.get(it.name()) + "euro per aver venduto 1x "
					+ it.name() + "!");
		} else {
			p.sendMessage(ChatColor.DARK_RED + "Non hai abbastanza " + it.name());
		}
	}

	public static void buy(Player p, Material it) {
		if (TestMoney.paga(p, items.get(it.name()))) {
			p.sendMessage(ChatColor.RED + "Hai pagato " + items.get(it.name()) + "euro per aver comprato 1x "
					+ it.name() + "!");
			p.getInventory().addItem(new ItemStack(it, 1));
		}
	}

	public static ConcurrentHashMap<String, Integer> getItems() {
		return items;
	}

	public void inizializzaPrezzi() {
		items.put(Material.DIAMOND.name(), 1000);
		items.put(Material.IRON_INGOT.name(), 500);
		items.put(Material.COAL.name(), 200);
		items.put(Material.LAPIS_BLOCK.name(), 1000);
		items.put(Material.REDSTONE.name(), 100);
		items.put(Material.LOG.name(), 200);
		items.put(Material.GOLD_INGOT.name(), 800);
	}
	*/
}