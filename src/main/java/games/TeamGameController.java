package games;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import teams.*;
import util.Notifier;

public abstract class TeamGameController extends GameController {

    Scoreboard board;

    protected TeamGameController(MinigameMode mode, MinigameMap map, int minPlayers, int maxPlayers) {
        super(mode, map, minPlayers, maxPlayers);
        board = Bukkit.getScoreboardManager().getMainScoreboard();
        setupScoreboard(board);
    }

    public void onStart() {

    }

    public boolean onNext() {
        return false;
    }

    public void onStop() {

    }

    public void onFinish() {

    }

    public void onTeamSwitch(Player p, Team to) {
        Notifier.sendToAllPlayers(p.getDisplayName() + " has switched to team " + to.getName());
    }

    public void onJoin(Player p) {
        Notifier.sendToAllPlayers(p.getDisplayName()+" joined the game");
        TeamManager.switchTeam(p, TeamManager.SPECTATORS_TEAM);
        p.setScoreboard(board);
    }

    public void onLeave(Player p) {
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public abstract void setupScoreboard(Scoreboard board);

}
