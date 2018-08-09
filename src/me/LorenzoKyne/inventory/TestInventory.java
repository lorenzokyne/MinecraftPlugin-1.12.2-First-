package me.LorenzoKyne.inventory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.LorenzoKyne.money.TestMoney;

public class TestInventory extends JavaPlugin implements Listener {

	/**
	 * Hash map which contains items and relative prices.
	 */
	private static ConcurrentHashMap<String, Integer> items = new ConcurrentHashMap<String, Integer>();
	/**
	 * Flag that indicate if prices have changed, if so changes will be saved.
	 */
	private static boolean changed = false;

	public static Inventory myInventory = Bukkit.createInventory(null, 9, "My custom Inventory!");
	// The first parameter, is the inventory owner. I make it null to let everyone
	// use it.
	// The second parameter, is the slots in a inventory. Must be a multiple of 9.
	// Can be up to 54.
	// The third parameter, is the inventory name. This will accept chat colors.

	static {
		// The first parameter, is the slot that is assigned to. Starts counting at 0
		myInventory.setItem(0, new ItemStack(Material.DIAMOND, 1));
		myInventory.setItem(1, new ItemStack(Material.REDSTONE, 1));
		myInventory.setItem(2, new ItemStack(Material.LAPIS_BLOCK, 1));
		myInventory.setItem(4, new ItemStack(Material.IRON_INGOT, 1));
		myInventory.setItem(6, new ItemStack(Material.LOG, 1));
		myInventory.setItem(7, new ItemStack(Material.GOLD_INGOT, 1));
		myInventory.setItem(8, new ItemStack(Material.COAL, 1));
	}

	public void onEnable() {
		try {
			FileInputStream inFile = new FileInputStream("prices.dat");
			ObjectInputStream in = new ObjectInputStream(inFile);
			items = (ConcurrentHashMap<String, Integer>) in.readObject();
			in.close();
		} catch (IOException e) {
			initializePrices();
			changed = true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("ShopInventory plugin loaded!");
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
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked(); // The player that clicked the item
		ItemStack clicked = event.getCurrentItem(); // The item that was clicked
		Inventory inventory = event.getInventory(); // The inventory that was clicked in
		if (inventory.getName().equals(myInventory.getName())) {
			if (items.containsKey(clicked.getType().name())) {
				if (event.isRightClick()) {
					buy(player, clicked.getType());
					event.getResult();
					event.setResult(Result.DENY);
				} else {
					sell(player, clicked.getType());
					event.getResult();
					event.setResult(Result.DENY);
				}
			}
		}

	}

	public static void sell(Player p, Material it) {
		if (p.getInventory().containsAtLeast(new ItemStack(it), 1)) {
			p.getInventory().removeItem(new ItemStack(it, 1));
			TestMoney.receive(p, items.get(it.name()));
			p.sendMessage(ChatColor.GREEN + "Hai ricevuto " + items.get(it.name()) + "euro per aver venduto 1x "
					+ it.name() + "!");
		} else {
			p.sendMessage(ChatColor.DARK_RED + "Non hai abbastanza " + it.name());
		}
	}

	public static void buy(Player p, Material it) {
		if (TestMoney.pay(p, items.get(it.name()))) {
			p.sendMessage(ChatColor.RED + "Hai pagato " + items.get(it.name()) + "euro per aver comprato 1x "
					+ it.name() + "!");
			p.getInventory().addItem(new ItemStack(it, 1));
		}
	}

	public static ConcurrentHashMap<String, Integer> getItems() {
		return items;
	}

	public void initializePrices() {
		items.put(Material.DIAMOND.name(), 1000);
		items.put(Material.IRON_INGOT.name(), 500);
		items.put(Material.COAL.name(), 200);
		items.put(Material.LAPIS_BLOCK.name(), 1000);
		items.put(Material.REDSTONE.name(), 100);
		items.put(Material.LOG.name(), 200);
		items.put(Material.GOLD_INGOT.name(), 800);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("shop")) {
				p.openInventory(myInventory);
			}
		}
		return false;
	}

}
