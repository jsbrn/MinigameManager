package main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public abstract class GameInstance implements Listener {

    private ArrayList<UUID> registeredPlayers;
    private ArrayList<Player> joinedPlayers;
    private final String map, name;
    private int minimumPlayerCount, maximumPlayerCount;
    private boolean started, finished, cancelled;

    private int stage = 0;

    public GameInstance(String map, int minPlayers, int maxPlayers) {
        Random r = new Random();
        this.map = map;
        this.name = map+"_"+(10000+r.nextInt(89999));
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

    public void finish() {
        onFinish();
    }

    public abstract void onStart(); //things to do when the game starts
    public abstract boolean onNext(); //advance the game stage
    public abstract void onStop(); //things to do when the game is cancelled
    public abstract void onFinish(); //things to do when the game finishes

    public final String getWorldName() {
        return "game_"+ name;
    }

    public final World getWorld() {
        return Bukkit.getWorld(getWorldName());
    }

    public final String getName() {
        return name;
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

}
