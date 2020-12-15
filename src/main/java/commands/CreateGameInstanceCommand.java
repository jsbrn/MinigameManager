package commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import games.RisingLavaGameInstance;
import games.WaterDropGameInstance;
import main.GameInstance;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import util.GameManager;

public class CreateGameInstanceCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length <= 0) return false;
        GameInstance instance = null;
        if (strings[0].equals("rising_lava")) instance = new RisingLavaGameInstance();
        if (strings[0].equals("water_drop")) instance = new WaterDropGameInstance();
        if (instance == null) {
            commandSender.sendMessage(ChatColor.RED+"Sorry, that game mode does not exist!");
            return true;
        }
        commandSender.sendMessage("Creating new Rising Lava game...");
        if (GameManager.createGameInstance(instance)) {
            for (Player p: Bukkit.getOnlinePlayers()) {
                TextComponent message = new TextComponent("A new game has started! Click here to join!");
                //message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joingame "+instance.getName()));
                p.spigot().sendMessage(message);
            }
            commandSender.sendMessage(ChatColor.GREEN+"Success! Do "+ChatColor.YELLOW+"/join "+instance.getName()+ChatColor.GREEN+" to reserve your spot!");
        } else {
            commandSender.sendMessage(ChatColor.RED+"Failed!");
        }
        return true;
    }

}
