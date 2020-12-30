package util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class Notifier {

    private static Random random = new Random();

    private static String[] tips = new String[] {
            ChatColor.YELLOW+"Want to stay notified of new competitions? Join our "+ChatColor.DARK_PURPLE+"Discord"+ChatColor.YELLOW+"!",
            ChatColor.GOLD+"Coins"+ChatColor.YELLOW+" are used to enter competitions for prizes. Do "+ChatColor.AQUA+"/vote"+ChatColor.YELLOW+" to vote for our server every day and get "+ChatColor.GOLD+"100 Coins"+ChatColor.YELLOW+"!",
            ChatColor.YELLOW+"Think you've got what it takes? Bet coins against another player in Duels! Do "+ChatColor.AQUA+"/duel <playername>"+ChatColor.YELLOW+" to challenge someone."
    };

    private static BukkitTimerTask periodicTips = new BukkitTimerTask(0, 1000*60*15) {
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
