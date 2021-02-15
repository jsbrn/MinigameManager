package listeners;

import games.GameController;
import games.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import profiles.PlayerProfiles;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!PlayerProfiles.profileExists(event.getPlayer())) {
            event.getPlayer().sendMessage(ChatColor.GREEN+"Welcome to the server, "+event.getPlayer().getDisplayName()+"!");
            PlayerProfiles.create(event.getPlayer());
        } else {
            event.getPlayer().sendMessage(ChatColor.GREEN+"Welcome back!");
        }
        if (GameManager.getActiveGame() == null) {
            GameManager.next();
        } else {
            Player p = event.getPlayer();
            GameController active = GameManager.getActiveGame();
            p.teleport(active.getWorld().getSpawnLocation());
            p.setGameMode(GameMode.SPECTATOR);
            p.sendTitle(ChatColor.YELLOW+""+ChatColor.BOLD+active.getMap().getFriendlyWorldName(), active.getMode().getName(), 10, 60, 10);
            active.onJoin(p);
        }
    }

}
