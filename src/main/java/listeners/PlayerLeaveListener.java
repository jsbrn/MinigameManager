package listeners;

import games.GameController;
import games.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import profiles.PlayerProfiles;
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
