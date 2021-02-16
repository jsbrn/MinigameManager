package games;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import teams.Team;
import util.BukkitTimerTask;
import util.Notifier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public abstract class GameController implements Listener {

    private String id;
    private int minimumPlayerCount, maximumPlayerCount, secondsRemaining;
    private BukkitTimerTask timerCountdown;
    private GameMode defaultGameMode;
    private boolean started, finished, cancelled;

    private MinigameMode mode;
    private MinigameMap map;

    private Scoreboard board;

    private int stage = 0;

    protected GameController(MinigameMode mode, MinigameMap map, int minPlayers, int maxPlayers, final int timeLimitSeconds, GameMode defaultMode) {
        Random r = new Random();
        this.mode = mode;
        this.map = map;
        this.defaultGameMode = defaultMode;
        this.id = mode.getAcronym().toUpperCase()+(100+r.nextInt(900));
        this.minimumPlayerCount = minPlayers;
        this.maximumPlayerCount = maxPlayers;
        this.secondsRemaining = timeLimitSeconds;
        this.board = Bukkit.getScoreboardManager().getMainScoreboard();
        final GameController that = this;
        this.timerCountdown = new BukkitTimerTask(0, 1000) {
            @Override
            protected void run() {
                if (!Bukkit.getOnlinePlayers().isEmpty()) secondsRemaining--;
                that.onTimeChange(secondsRemaining);
                if (secondsRemaining == 0) {
                    this.stop();
                    that.finish();
                }
            }
        };
        setupScoreboard(board);
    }

    public final void start() {
        started = true;
        this.timerCountdown.start();
        onStart();
        save();
    }

    public final void stop() {
        cancelled = true;
        onStop();
        save();
    }

    public final void next() {
        stage++;
        onNext();
        save();
    }

    public final MinigameMode getMode() {
        return mode;
    }

    public final void finish() {
        finished = true;
        HandlerList.unregisterAll(this);
        onFinish();
        //10 second delay for win message, then 10 second countdown to next game
        BukkitTimerTask nextCountdown = new BukkitTimerTask(10000, 1000, 11) {
            @Override
            protected void run()
            {
                if (getRunCount() < 10) {
                    Notifier.sendToAllPlayers(ChatColor.YELLOW+"Next game in "+(10-getRunCount())+"...");
                } else {
                    GameManager.next();
                }
            }
        };
        nextCountdown.start();
        save();
    }

    public abstract void onStart(); //things to do when the game starts
    public abstract boolean onNext(); //advance the game stage
    public abstract void onStop(); //things to do when the game is cancelled
    public abstract void onFinish(); //things to do when the game finishes

    public abstract void onTimeChange(int secondsRemaining);

    public abstract void onDeath(Player p);
    public abstract void respawn(Player p);
    public abstract void setupScoreboard(Scoreboard board);

    public void onJoin(Player p) {
        Notifier.sendToAllPlayers(ChatColor.GREEN+p.getDisplayName()+" joined the match");
        p.sendTitle(ChatColor.YELLOW+""+ChatColor.BOLD+getMap().getFriendlyWorldName(), getMode().getName(), 10, 60, 10);
    }

    public abstract void onLeave(Player p);
    public abstract void onTeamSwitch(Player p, Team to);

    public Scoreboard getScoreboard() {
        return board;
    }

    public final String getWorldName() {
        return "game_"+ id;
    }

    public final World getWorld() {
        return Bukkit.getWorld(getWorldName());
    }

    public final String getID() {
        return id;
    }

    public MinigameMap getMap() { return map; }

    public final boolean isFinished() {
        return finished;
    }

    public int save() {
        return 0;
        //return DatabaseManager.executeUpdate("update_game_instance.sql", startDate, started, finished, cancelled, winner, id);
    }

    public GameMode getDefaultGameMode() {
        return defaultGameMode;
    }

    public boolean from(ResultSet row) throws SQLException {
        //setStartDate(row.getDate("start_date"));
        id = row.getString("game_id");
        //winner = row.getString("winner");
        started = row.getBoolean("started");
        finished = row.getBoolean("finished");
        cancelled = row.getBoolean("cancelled");
        return true;
    }

    private String generateRandomName() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder random = new StringBuilder();
        for (int i = 0; i < 6; i++)
            random.append(chars.charAt(new Random().nextInt(chars.length())));
        return random.toString();
    }

}
