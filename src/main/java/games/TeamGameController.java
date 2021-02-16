package games;

import org.bukkit.*;
import org.bukkit.entity.Player;
import teams.*;
import util.BukkitTimerTask;
import util.Notifier;

import java.util.HashMap;

public abstract class TeamGameController extends GameController {

    private HashMap<Team, Location> spawns;

    protected TeamGameController(MinigameMode mode, MinigameMap map, int minPlayers, int maxPlayers, int timeLimitSeconds, GameMode defaultMode) {
        super(mode, map, minPlayers, maxPlayers, timeLimitSeconds, defaultMode);
        spawns = new HashMap<Team, Location>();
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

    @Override
    public void onTimeChange(int secondsRemaining) {
        Notifier.sendToAllPlayers(secondsRemaining+" seconds remaining!");
    }

    public final void setSpawn(Team t, Location l) {
        spawns.put(t, l);
    }

    public final Location getSpawn(Team t) {
        Location loc = spawns.get(t);
        if (loc != null)
            //need to set world at this stage because when controller is initiated, world does not exist
            loc.setWorld(getWorld());
        return loc;
    }

    @Override
    public void onDeath(Player p) {

    }

    public void onTeamSwitch(Player p, Team to) {
        Notifier.sendToAllPlayers(p.getDisplayName() + " has switched to team " + to.getName());
        respawn(p);
    }

    @Override
    public void respawn(Player p) {
        Location spawn = getSpawn(TeamManager.getTeam(p));
        if (spawn != null) p.teleport(spawn);
        p.setHealth(20); //full health
    }

    public void onJoin(final Player p) {
        super.onJoin(p);
        BukkitTimerTask showTeamSwitcher = new BukkitTimerTask(6000, 0, 1) {
            @Override
            protected void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cp team_switcher "+p.getName());
            }
        };
        showTeamSwitcher.start();
        p.setScoreboard(getScoreboard());
    }

    public void onLeave(Player p) {
        //Notifier.sendToAllPlayers(ChatColor.YELLOW+p.getDisplayName()+" left the match");
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

}
