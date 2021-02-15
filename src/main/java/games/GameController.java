package games;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.ScoreboardManager;
import util.BukkitTimerTask;
import util.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Random;
import java.util.UUID;

public abstract class GameController implements Listener {

    private String id;
    private int minimumPlayerCount, maximumPlayerCount;
    private boolean started, finished, cancelled;

    private MinigameMode mode;
    private MinigameMap map;

    private int stage = 0;

    protected GameController(MinigameMode mode, MinigameMap map, int minPlayers, int maxPlayers) {
        Random r = new Random();
        this.mode = mode;
        this.map = map;
        this.id = mode.getAcronym().toUpperCase()+(100+r.nextInt(900));
        this.minimumPlayerCount = minPlayers;
        this.maximumPlayerCount = maxPlayers;
    }

    public void start() {

        ScoreboardManager manager = Bukkit.getScoreboardManager();

        started = true;
        onStart();
        save();

    }

    public void stop() {
        cancelled = true;
        onStop();
        save();
    }

    public void next() {
        stage++;
        onNext();
        save();
    }

    public MinigameMode getMode() {
        return mode;
    }

    public void finish() {
        finished = true;
        onFinish();
        BukkitTimerTask nextGame = new BukkitTimerTask(6000, 0, 1) {
            @Override
            protected void run() {
                GameManager.next();
            }
        };
        nextGame.start();
        save();
    }

    public abstract void onStart(); //things to do when the game starts
    public abstract boolean onNext(); //advance the game stage
    public abstract void onStop(); //things to do when the game is cancelled
    public abstract void onFinish(); //things to do when the game finishes

    public abstract void onJoin(Player p);
    public abstract void onLeave(Player p);

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

    public boolean isFinished() {
        return finished;
    }

    public int save() {
        return 0;
        //return DatabaseManager.executeUpdate("update_game_instance.sql", startDate, started, finished, cancelled, winner, id);
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
