package games.team;

import games.MinigameMap;
import games.MinigameMode;
import games.TeamGameController;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import teams.Team;
import teams.TeamManager;

public class CastleHeistGameController extends TeamGameController {

    public CastleHeistGameController() {
        super(MinigameMode.STEAL_THE_GOLD, MinigameMap.CASTLE_HEIST, 0, Integer.MAX_VALUE, 20, GameMode.SURVIVAL);
        setSpawn(TeamManager.BLUE_TEAM, new Location(null, -48, 71, 0.5, -90, 0));
        setSpawn(TeamManager.RED_TEAM, new Location(null, 49, 71, 0.5, 90, 0));
    }

    public void onStart() {

    }

    public boolean onNext() {
        return false;
    }

    public void onStop() {

    }

    @Override
    public void onTeamSwitch(Player p, Team to) {
        super.onTeamSwitch(p, to);
    }

    public void onLeave(Player p) {

    }

    public void setupScoreboard(Scoreboard board) {
        Objective objective = board.registerNewObjective(getID(), "dummy", ChatColor.YELLOW+""+ChatColor.BOLD+getMode().getName());
        Score redGold = objective.getScore(ChatColor.BOLD+""+ChatColor.RED+"RED's "+ChatColor.GOLD+"Gold:");
        Score bluGold = objective.getScore(ChatColor.BOLD+""+ChatColor.AQUA+"BLUE's "+ChatColor.GOLD+"Gold:");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        redGold.setScore(18);
        bluGold.setScore(18);
    }

}
