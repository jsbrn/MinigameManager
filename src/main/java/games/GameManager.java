package games;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import util.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameManager {

    private static GameController activeGame;

    private static GameController createGameInstance(MinigameMap map) {
        MVWorldManager worldManager = JavaPlugin.getPlugin(MultiverseCore.class).getMVWorldManager();
        GameController controller = map.createGameController();
        if (worldManager.cloneWorld(map.getWorldName(), controller.getWorldName())) {
            worldManager.getMVWorld(controller.getWorldName()).setKeepSpawnInMemory(false);
            return controller;
        }
        return null;
    }

    public static void next() {
        GameController controller = createGameInstance(MinigameMap.CASTLE_HEIST);
        if (activeGame != null)
            activeGame.finish();
        activeGame = controller;
        for (Player p: Bukkit.getOnlinePlayers()) {
            p.teleport(controller.getWorld().getSpawnLocation());
            p.sendTitle(ChatColor.YELLOW+""+ChatColor.BOLD+controller.getMap().getFriendlyWorldName(), controller.getMode().getName(), 10, 60, 10);
            activeGame.onJoin(p);
        }
        // Bukkit.getServer().getPluginManager().registerEvents(gi, GameManagerPlugin.getInstance());
        // gi.start();
    }

    public static GameController getActiveGame() {
        return activeGame;
    }

    public static void loadAllGameInstances() {

        ResultSet games = DatabaseManager.executeQuery("select_all_games.sql");

        try {
            while (games.next()) {
                String map = games.getString("map");
                GameController instance = MinigameMap.valueOf(map).createGameController();
                instance.from(games);
                ResultSet registrations = DatabaseManager.executeQuery("select_registrations_by_game_id.sql", instance.getID());
                while (registrations.next()) {
                    //instance.register(UUID.fromString(registrations.getString("player_uuid")));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void saveAllGameInstances() {
        //for (GameController instance: GAME_INSTANCES.values()) instance.save();
    }

}
