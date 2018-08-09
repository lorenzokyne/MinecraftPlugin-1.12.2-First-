package me.LorenzoKyne.time;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TestTime extends JavaPlugin {

	public static final int seconds = 0;// seconds
	public static final int minutes = 1;// minutes
	public static final int hours = 2;// hours
	public static final int days = 3;// days
	private static int[] timer = new int[4]; // array of time

	public void onEnable() {
		try {
			FileInputStream inFile = new FileInputStream("timer.dat");
			ObjectInputStream in = new ObjectInputStream(inFile);
			timer = (int[]) in.readObject();
			in.close();
		} catch (IOException e) {
			System.out.println("File non trovato!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			countTime();
		}
		getLogger().info("Timer plugin loaded!");
	}

	public void onDisable() {
		try {
			FileOutputStream outFile = new FileOutputStream("timer.dat");
			ObjectOutputStream out = new ObjectOutputStream(outFile);
			out.writeObject(timer);
			out.close();
		} catch (IOException e) {

		}
		getLogger().info("Timer plugin disabled!");
	}

	public void countTime() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				timer[seconds]++;
				if (timer[seconds] == 60) {
					timer[seconds] = 0;
					timer[minutes]++;
				}
				if (timer[minutes] == 60) {
					timer[minutes] = 0;
					timer[hours]++;
				}
				if (timer[hours] == 24) {
					timer[hours] = 0;
					timer[days]++;
				}
			}
		}, 0, 20); // 20 ticks equals to 1 second.
	}

	public static int[] getTimer() {
		return timer;
	}

	// in seconds
	public static int getTotalTime() {
		int tot = 0;
		tot += timer[days] * 24 * 60 * 60;
		tot += timer[hours] * 60 * 60;
		tot += timer[minutes] * 60;
		tot += timer[seconds];
		return tot;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("time")) {
				p.sendMessage(timer[days] + " days, " + timer[hours] + " hours, " + timer[minutes] + " minutes, "
						+ timer[seconds] + " seconds.");
				return true;
			} else if (cmd.getName().equalsIgnoreCase("totaltime")) {
				p.sendMessage(getTotalTime() + " seconds.");
				return true;
			}
		}
		return false;
	}
}
