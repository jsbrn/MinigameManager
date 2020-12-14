package util;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import main.GameInstance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class GameManager {

    private static HashMap<String, GameInstance> GAME_INSTANCES = new HashMap<String, GameInstance>();

    public static boolean createGameInstance(GameInstance game) {
        if (GAME_INSTANCES.containsKey(game.getName())) return false;
        MVWorldManager worldManager = JavaPlugin.getPlugin(MultiverseCore.class).getMVWorldManager();
        if (worldManager.cloneWorld(game.getMap(), game.getWorldName())) {
            GAME_INSTANCES.put(game.getName(), game);
            return true;
        }
        return false;
    }

    public static boolean joinGameInstance(Player p, String name) {
        if (GAME_INSTANCES.get(name) != null) {
            GameInstance gi = GAME_INSTANCES.get(name);
            gi.register(p);
            return true;
        }
        return false;
    }

    public static boolean startGameInstance(String name) {
        if (GAME_INSTANCES.get(name) != null) {
            GameInstance gi = GAME_INSTANCES.get(name);
            //teleport all players to the world
            //trigger the start event
            for (UUID uuid: gi.getRegisteredPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mvtp "+player.getName()+" "+gi.getWorldName());
                    player.teleport(gi.getWorld().getSpawnLocation());
                }
            }
            gi.start();
            return true;
        }
        return false;
    }

}
