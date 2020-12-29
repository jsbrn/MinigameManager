package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import games.GameManager;

public class StartGameInstanceCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length <= 0) return false;
        if (!(commandSender instanceof Player)) return false;
        commandSender.sendMessage("Starting game "+strings[0]+"...");
        if (GameManager.startGameInstance(strings[0])) {
            commandSender.sendMessage(ChatColor.GREEN+"Success!");
        } else {
            commandSender.sendMessage(ChatColor.RED+"Failed!");
        }
        return true;
    }

}
