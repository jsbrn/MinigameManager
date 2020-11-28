package main;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerProfile {

    private UUID uuid;
    private double tickets, credits, coins;
    private int wins, losses;

    public PlayerProfile(UUID player, double tickets, double credits, double coins) {
        this.uuid = player;
        this.tickets = tickets;
        this.credits = credits;
        this.coins = coins;
    }

}
