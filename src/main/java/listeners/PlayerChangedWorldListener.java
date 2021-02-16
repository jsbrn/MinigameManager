package listeners;

import games.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import teams.TeamManager;

public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        if (GameManager.getActiveGame() == null) return;
        // Trigger the game controller's join handler
        if (event.getPlayer().getWorld().getName().equals(GameManager.getActiveGame().getWorld().getName())) {
            GameManager.getActiveGame().onJoin(event.getPlayer());
            TeamManager.switchTeam(event.getPlayer(), TeamManager.SPECTATORS_TEAM, null, false);
        }
    }

}
