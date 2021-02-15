package games;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import teams.*;
import util.BukkitTimerTask;
import util.Notifier;

import java.util.HashMap;

public abstract class TeamGameController extends GameController {

    private Scoreboard board;
    private HashMap<Team, Location> spawns;

    protected TeamGameController(MinigameMode mode, MinigameMap map, int minPlayers, int maxPlayers, GameMode defaultMode) {
        super(mode, map, minPlayers, maxPlayers, defaultMode);
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

    public final void setSpawn(Team t, Location l) {
        spawns.put(t, l);
    }

    public void onTeamSwitch(Player p, Team to) {
        Notifier.sendToAllPlayers(p.getDisplayName() + " has switched to team " + to.getName());
        respawn(p);
    }

    @Override
    public void respawn(Player p) {
        p.teleport(spawns.get(TeamManager.getTeam(p)));
        p.setHealth(20); //full health
        p.getInventory().addItem(new ItemStack(Material.SPRUCE_WOOD, 64));
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
        p.setScoreboard(board);
    }

    public void onLeave(Player p) {
        //Notifier.sendToAllPlayers(ChatColor.YELLOW+p.getDisplayName()+" left the match");
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public abstract void setupScoreboard(Scoreboard board);

}
