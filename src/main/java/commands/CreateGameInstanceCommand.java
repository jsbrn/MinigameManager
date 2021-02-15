package commands;

import games.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateGameInstanceCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length <= 0) return false;

        GameController instance = MinigameMap.valueOf(strings[0].toUpperCase()).createGameController();

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

//        if (GameManager.createGameInstance(instance)) {
//            commandSender.sendMessage(ChatColor.GREEN+"Successfully created game!");
//            for (Player p: Bukkit.getOnlinePlayers()) {
//                if (startDate == null) {
//                    p.sendMessage(ChatColor.YELLOW+"A "+instance.getMode().getName()+" game is about to start! " +
//                            "Do "+ChatColor.GREEN+"/jg "+instance.getID()+ChatColor.YELLOW+" to reserve a spot!");
//                    //TODO: ONLY GOLD MEMBERS CAN SPECTATE
//                    p.sendMessage(ChatColor.GRAY+"(Do "+ChatColor.BOLD+"/spec "+instance.getID()+ChatColor.RESET+" to spectate)");
//                }
//            }
//        } else {
//            commandSender.sendMessage(ChatColor.RED+"Failed to create game!");
//        }
        return true;
    }

}
