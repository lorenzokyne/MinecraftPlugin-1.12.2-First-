package me.LorenzoKyne.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.LorenzoKyne.money.TestMoney;
import me.LorenzoKyne.time.TestTime;

public class TestingScoreboard extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getLogger().info(ChatColor.GREEN + "Scoreboard Enabled!");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void join(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				if (player.isOnline()) {
					ScoreboardManager manager = Bukkit.getScoreboardManager();
					Scoreboard scoreboard = manager.getNewScoreboard();
					Objective obj = scoreboard.registerNewObjective("lvl", "");
					obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					obj.setDisplayName("Stats");

					Score lvl = obj.getScore("Livello" + ": " + ChatColor.GOLD + player.getLevel());
					lvl.setScore(1);

					Score username = obj.getScore("Username: " + ChatColor.AQUA + player.getDisplayName());
					username.setScore(2);

					Score ping = obj.getScore("Ping: " + ChatColor.WHITE + getPing(player));
					ping.setScore(3);

					Score money = obj.getScore("Money: " + ChatColor.GOLD + TestMoney.getMoney(player));
					money.setScore(4);

					Score blank = obj.getScore("");
					blank.setScore(5);

					int display = 0;
					String what = "";
					int[] time = TestTime.getTimer();

					if (time[TestTime.days] > 0) {
						display = time[TestTime.days];
						what = " days";
					} else if (time[TestTime.hours] > 0) {
						display = time[TestTime.hours];
						what = " hours";
					} else if (time[TestTime.minutes] > 0) {
						display = time[TestTime.minutes];
						what = " minutes";
					} else {
						display = time[TestTime.seconds];
						what = " seconds";
					}
					Score timer = obj
							.getScore("TotalTime: " + ChatColor.ITALIC + ChatColor.LIGHT_PURPLE + display + what);
					timer.setScore(6);

					try {
						player.setScoreboard(scoreboard);
					} catch (Exception e) {
						// TO DO FIX THIS
						// System.out.println("eccezione");
					}
				} else {
					// Thread.yield();
				}
			}
		}, 0, 10);
	}

	public int getPing(Player p) {
		return (int) ((CraftPlayer) p).getHandle().ping;
	}

	public void displayTime(int display, String what) {

	}
}
