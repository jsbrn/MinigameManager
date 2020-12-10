package games;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public abstract class GameInstance implements Listener, CommandExecutor {

    private ArrayList<UUID> registeredPlayers;
    private final String map;
    private final int id;

    public GameInstance(int id, String map) {
        this.map = map;
        this.id = id;
        this.registeredPlayers = new ArrayList<UUID>();
    }

    public final boolean create() {
        Bukkit.broadcastMessage(ChatColor.YELLOW+"A <game mode> game is now starting on <map name>!");
        MVWorldManager worldManager = JavaPlugin.getPlugin(MultiverseCore.class).getMVWorldManager();
        return worldManager.cloneWorld(map, "game_"+map+"_"+id);
    }

    abstract void cleanup();

}
