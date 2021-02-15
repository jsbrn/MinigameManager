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
import teams.TeamManager;

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
            TeamManager.switchTeam(p, TeamManager.SPECTATORS_TEAM);
            active.onJoin(p);
        }
    }

}
