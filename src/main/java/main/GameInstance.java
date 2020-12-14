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

    private ArrayList<UUID> registeredPlayers, joinedPlayers;
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
        this.joinedPlayers = new ArrayList<UUID>();
    }

    public void start() {
        started = true;
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

    public ArrayList<UUID> getActivePlayers() {
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

    public @NotNull BukkitTask schedule(BukkitRunnable r, int delay, int interval) {
        Plugin this_ = JavaPlugin.getPlugin(GameManagerPlugin.class);
        System.out.println("Plugin: "+this_);
        return Bukkit.getServer().getScheduler().runTaskTimer(this_, r, delay, interval);
    }

}
