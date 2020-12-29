package commands;

import games.HelltowerGameInstance;
import games.WaterDropGameInstance;
import games.GameInstance;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import games.GameManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateGameInstanceCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length <= 0) return false;
        GameInstance instance = null;

        if (strings[0].equals("hell_tower")) instance = new HelltowerGameInstance();
        if (strings[0].equals("water_dtop")) instance = new WaterDropGameInstance();

        if (instance == null) {
            commandSender.sendMessage(ChatColor.RED+"Sorry, that game mode does not exist!");
            return true;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mma");
        Date startDate = null;
        try {
            if (strings.length >= 3) {
                startDate = dateFormat.parse(strings[1]+" "+strings[2]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            commandSender.sendMessage(ChatColor.RED+e.getMessage());
            return true;
        }

        commandSender.sendMessage("Creating new "+instance.getMode().getName()+" game...");

        if (GameManager.createGameInstance(instance, startDate)) {
            for (Player p: Bukkit.getOnlinePlayers()) {
                if (startDate == null) {
                    p.sendMessage(ChatColor.YELLOW+"A "+instance.getMode().getName()+" game is about to start! Do ");
                }
            }
            commandSender.sendMessage(ChatColor.GREEN+"Success! Do "+ChatColor.YELLOW+"/join "+instance.getName()+ChatColor.GREEN+" to reserve your spot!");
        } else {
            commandSender.sendMessage(ChatColor.RED+"Failed to create game!");
        }
        return true;
    }

}
