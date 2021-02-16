package listeners;

import games.GameController;
import games.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import teams.Team;
import teams.TeamManager;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Team team = TeamManager.getTeam(event.getPlayer());
        if (team != null) team.removePlayer(event.getPlayer());
        GameController active = GameManager.getActiveGame();
        if (active != null)
            active.onLeave(event.getPlayer());
    }

}
