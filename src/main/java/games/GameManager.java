package games;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import main.GameManagerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import util.BukkitTimerTask;
import util.DatabaseManager;
import util.Notifier;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameManager {

    private static int gamesSinceLastCleanse = 0;
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

        if (gamesSinceLastCleanse == 10) {
            GameManagerPlugin.getInstance().deleteOldWorlds();
            gamesSinceLastCleanse = 0;
        }

        if (activeGame != null) {
            activeGame.stop();
            activeGame = null;
        }

        MinigameMap map = MinigameMap.CASTLE_HEIST;
        final GameController controller = createGameInstance(map);
        gamesSinceLastCleanse++;
        Bukkit.getLogger().info("Creating new game instance ("+map.getWorldName()+")...");
        activeGame = controller;
        for (Player p: Bukkit.getOnlinePlayers()) {
            p.teleport(controller.getWorld().getSpawnLocation());
        }
        BukkitTimerTask startGame = new BukkitTimerTask(10000, 1000, 11) {
            @Override
            protected void run() {
                if (getRunCount() < 10) {
                    Notifier.showToAllPlayers(ChatColor.GREEN + "Starting in " + ChatColor.YELLOW + (10 - getRunCount()) + ChatColor.GREEN + "...", "");
                    if (getRunCount() >= 5) Notifier.playForAllPlayers(Sound.BLOCK_STONE_BUTTON_CLICK_ON);
                } else {
                    Notifier.showToAllPlayers(ChatColor.YELLOW + "Good luck!", controller.getMode().getName());
                    Notifier.playForAllPlayers(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    Notifier.playForAllPlayers(Sound.ENTITY_COW_DEATH);
                    controller.start();
                }
            }
        };
        startGame.start();

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
