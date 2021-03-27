package commands;

import games.GameController;
import games.GameManager;
import games.MinigameMap;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import util.Notifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NextGameCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Notifier.playForAllPlayers(Sound.ENTITY_VILLAGER_NO);
        GameManager.next();
        return true;
    }

}
