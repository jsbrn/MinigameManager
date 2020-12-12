package main;

import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.UUID;

public abstract class GameInstance implements Listener {

    private ArrayList<UUID> registeredPlayers, joinedPlayers;
    private final String map, name;
    private int minimumPlayerCount, maximumPlayerCount;
    private boolean started, finished, cancelled;

    public GameInstance(int id, String map, int minPlayers, int maxPlayers) {
        this.map = map;
        this.name = map+"_"+id;
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

    public void finish() {
        onFinish();
        onFinish();
    }

    abstract void onStart(); //things to do when the game starts
    abstract void onNext(); //advance the game stage
    abstract void onStop(); //things to do when the game is cancelled
    abstract void onFinish(); //things to do when the game finishes

    public final String getWorldName() {
        return "game_"+ name;
    }

    public final String getName() {
        return name;
    }

    public String getMap() {
        return map;
    }
}
