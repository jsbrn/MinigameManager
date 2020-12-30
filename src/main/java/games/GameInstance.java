package games;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import util.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Random;
import java.util.UUID;

public abstract class GameInstance implements Listener {

    private ArrayList<UUID> registeredPlayers;
    private ArrayList<Player> joinedPlayers;
    private final String map, id;
    private int minimumPlayerCount, maximumPlayerCount;
    private boolean started, finished, cancelled;
    private Date startDate;

    private MinigameMode mode;

    private String winner;

    private int stage = 0;

    protected GameInstance(MinigameMode mode, MinigameMap map, int minPlayers, int maxPlayers) {
        Random r = new Random();
        this.map = map.getWorldName();
        this.mode = mode;
        this.id = mode.getAcronym().toUpperCase()+(100+r.nextInt(900));
        this.minimumPlayerCount = minPlayers;
        this.maximumPlayerCount = maxPlayers;
        this.registeredPlayers = new ArrayList<UUID>();
        this.joinedPlayers = new ArrayList<Player>();
    }

    public void start() {
        started = true;
        for (UUID u: registeredPlayers) {
            Player p = Bukkit.getServer().getPlayer(u);
            if (p != null) {
                joinedPlayers.add(p);
            }
        }
        onStart();
    }

    public void stop() {
        cancelled = true;
        onStop();
    }

    public void next() {
        stage++;
        onNext();
    }

    public long getStartDate() { return startDate.getTime(); }

    public void setStartDate(java.util.Date date) {
        startDate = new java.sql.Date(date.getTime());
    }

    public void setWinner(String username) {
        this.winner = username;
    }

    public MinigameMode getMode() {
        return mode;
    }

    public void finish() {
        onFinish();
    }

    public abstract void onStart(); //things to do when the game starts
    public abstract boolean onNext(); //advance the game stage
    public abstract void onStop(); //things to do when the game is cancelled
    public abstract void onFinish(); //things to do when the game finishes

    public final String getWorldName() {
        return "game_"+ id;
    }

    public final World getWorld() {
        return Bukkit.getWorld(getWorldName());
    }

    public final String getID() {
        return id;
    }

    public String getMap() {
        return map;
    }

    public ArrayList<Player> getActivePlayers() {
        return joinedPlayers;
    }

    public ArrayList<UUID> getRegisteredPlayers() {
        return registeredPlayers;
    }

    public boolean register(Player p) {
        if (registeredPlayers.contains(p.getUniqueId())) return false;
        if (registeredPlayers.size() >= maximumPlayerCount) return false;
        registeredPlayers.add(p.getUniqueId());
        return true;
    }

    public boolean isFinished() {
        return finished;
    }

    public int save() {
        return DatabaseManager.executeUpdate("update_game_instance.sql", startDate, started, finished, cancelled, winner);
    }

    public boolean from(ResultSet row) throws SQLException {
        setStartDate(row.getDate("start_date"));
        winner = row.getString("winner");
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
