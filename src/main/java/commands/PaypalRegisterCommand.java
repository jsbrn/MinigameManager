package commands;

import main.PlayerProfile;
import main.TicketEconomy;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.awt.AWTIcon32_java_icon16_png;
import util.PlayerProfiles;

public class PaypalRegisterCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            PlayerProfile profile = PlayerProfiles.getPlayerProfile(player.getUniqueId());
            if (args.length == 1) {
                if (isValidEmail(args[0])) {
                    profile.setPaypalEmail(args[0]);
                    commandSender.sendMessage(
                            ChatColor.GREEN+"You have successfully registered "+profile.getPaypalEmail()+" as your Paypal email. " +
                            "Use the Shop menu to cash out your credits at any time.");
                    profile.save();
                    return true;
                } else {
                    commandSender.sendMessage(ChatColor.YELLOW+"The email you entered is not a valid address. Did you misspell it?");
                    return true;
                }
            }
        } else {
            commandSender.sendMessage(ChatColor.RED+"This command can only be ran as a player.");
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EMAIL_REGEX);
    }

}
