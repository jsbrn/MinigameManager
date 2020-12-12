package util;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import main.GameInstance;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

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

}
