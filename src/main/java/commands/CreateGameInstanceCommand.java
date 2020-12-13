package commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import games.RisingLavaGameInstance;
import main.GameInstance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import util.GameManager;

public class CreateGameInstanceCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length <= 0) return false;
        GameInstance risingLava = new RisingLavaGameInstance();
        commandSender.sendMessage("Creating new Rising Lava game...");
        if (GameManager.createGameInstance(risingLava)) {
            commandSender.sendMessage(ChatColor.GREEN+"Success! Do "+ChatColor.YELLOW+"/join "+risingLava.getName()+ChatColor.GREEN+" to reserve your spot!");
        } else {
            commandSender.sendMessage(ChatColor.RED+"Failed!");
        }
        return true;
    }

}
