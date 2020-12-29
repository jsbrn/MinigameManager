package games;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import games.GameInstance;
import main.GameManagerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import util.BukkitTimerTask;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class GameManager {

    private static HashMap<String, GameInstance> GAME_INSTANCES = new HashMap<String, GameInstance>();

    public static boolean createGameInstance(GameInstance game) {
        return createGameInstance(game, null);
    }

    public static boolean createGameInstance(GameInstance game, Date start) {
        if (GAME_INSTANCES.containsKey(game.getName())) return false;
        MVWorldManager worldManager = JavaPlugin.getPlugin(MultiverseCore.class).getMVWorldManager();
        if (worldManager.cloneWorld(game.getMap(), game.getWorldName())) {
            worldManager.getMVWorld(game.getWorldName()).setKeepSpawnInMemory(false);
            if (start != null) game.setStartDate(start);
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
            final GameInstance gi = GAME_INSTANCES.get(name);

            BukkitTimerTask showCountdown = new BukkitTimerTask(0, 1000) {
                private int countdown = 5;
                public void run() {
                    //teleport all players to the world
                    //trigger the start event
                    for (UUID uuid : gi.getRegisteredPlayers()) {
                        Player player = Bukkit.getPlayer(uuid);
                        if (player != null) {
                            if (countdown <= 0) {
                                //register the game listeners
                                player.teleport(gi.getWorld().getSpawnLocation());
                            } else {
                                player.sendTitle(ChatColor.YELLOW+"Starting in "+ChatColor.RED+countdown+ChatColor.YELLOW+"...", "", 0, 0, 20);
                            }
                        }

                    }
                    if (countdown <= 0) {
                        stop();
                        Bukkit.getServer().getPluginManager().registerEvents(gi, GameManagerPlugin.getInstance());
                        gi.start();
                    }
                    countdown--;
                }
            };
            showCountdown.start();
            return true;
        }
        return false;
    }

}
