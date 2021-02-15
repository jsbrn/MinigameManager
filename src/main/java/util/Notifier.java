package util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class Notifier {

    private static Random random = new Random();

    private static String[] tips = new String[] {
            ChatColor.YELLOW+"We hold weekly competitions where you can win CASH prizes! Join our "+ChatColor.DARK_PURPLE+"Discord"+ChatColor.YELLOW+" to learn more!",
            ChatColor.GOLD+"Coins"+ChatColor.YELLOW+" are used to enter competitions for prizes. Do "+ChatColor.AQUA+"/vote"+ChatColor.YELLOW+" to vote for our server every day and get "+ChatColor.GOLD+"100 Coins"+ChatColor.YELLOW+"!"
    };

    private static BukkitTimerTask periodicTips = new BukkitTimerTask(0, 1000*60*10) {
        @Override
        protected void run() {
            showTipToAllPlayers();
        }
    };

    private static void showTipToAllPlayers() {
        for (Player p: Bukkit.getOnlinePlayers()) {
            p.sendMessage(tips[random.nextInt(tips.length)]);
        }
    }

    public static void init() {
        periodicTips.start();
    }

}
