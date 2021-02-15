package games.teams;

import games.GameController;
import games.MinigameMap;
import games.MinigameMode;
import games.TeamGameController;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class CastleHeistGameController extends TeamGameController {

    public CastleHeistGameController() {
        super(MinigameMode.STEAL_THE_GOLD, MinigameMap.CASTLE_HEIST, 0, Integer.MAX_VALUE);
    }

    public void onStart() {

    }

    public boolean onNext() {
        return false;
    }

    public void onStop() {

    }

    public void onJoin(Player p) {
        super.onJoin(p);
        p.sendMessage("Joined the game, added a team!");
    }

    public void onLeave(Player p) {

    }

    public void setupScoreboard(Scoreboard board) {
        Team red = board.registerNewTeam("Red");
        Team blu = board.registerNewTeam("Blue");
        Objective objective = board.registerNewObjective(getMode().getAcronym(), "dummy", ChatColor.YELLOW+""+ChatColor.BOLD+getMode().getName());
        Score redGold = objective.getScore(ChatColor.BOLD+""+ChatColor.RED+"RED's "+ChatColor.GOLD+"Gold:");
        Score bluGold = objective.getScore(ChatColor.BOLD+""+ChatColor.AQUA+"BLUE's "+ChatColor.GOLD+"Gold:");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        redGold.setScore(18);
        bluGold.setScore(18);
        red.setPrefix(ChatColor.RED+"[RED]"+ChatColor.WHITE);
        blu.setPrefix(ChatColor.BLUE+"[BLUE]"+ChatColor.WHITE);
        red.setAllowFriendlyFire(false);
        blu.setAllowFriendlyFire(false);
    }

}
