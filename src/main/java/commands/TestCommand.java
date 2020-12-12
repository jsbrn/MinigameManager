package commands;

import main.PlayerProfile;
import main.TicketEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.awt.AWTIcon32_java_icon16_png;
import util.PlayerProfiles;

import java.util.Timer;
import java.util.TimerTask;

public class TestCommand implements CommandExecutor {

    public boolean onCommand(final CommandSender commandSender, Command command, String label, String[] args) {
        TimerTask delayedTeleport = new TimerTask() {
            private int seconds = 5;
            @Override
            public void run() {
                if (seconds > 0) {
                    commandSender.sendMessage(ChatColor.YELLOW+"Starting in "+ChatColor.RED+seconds+ChatColor.YELLOW+"...");
                    seconds--;
                } else {
                    commandSender.sendMessage(ChatColor.GREEN+"Now!");
                    cancel();
                }
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(delayedTeleport, 0, 1000);
        return true;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EMAIL_REGEX);
    }

}
