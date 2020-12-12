package main;

import org.bukkit.entity.Player;
import util.CoinTransactionReason;
import util.DatabaseManager;

import java.util.UUID;

public class PlayerProfile {

    private UUID uuid;
    private boolean premium;
    private int tickets, credits, coins;
    private String paypalEmail;

    public PlayerProfile(UUID player, boolean premium, int tickets, int credits, int coins, String paypalEmail) {
        this.uuid = player;
        this.premium = premium;
        this.tickets = tickets;
        this.credits = credits;
        this.coins = coins;
        this.paypalEmail = paypalEmail;
    }

    public int getTickets() {
        return tickets;
    }

    public int getCoins() {
        return coins;
    }

    public int getCredits() {
        return credits;
    }

    public void addTickets(int amount, CoinTransactionReason reason, Player executor) {

    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public boolean hasRegisteredPaypal() {
        return paypalEmail != null && paypalEmail.length() > 0;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public int save() {
        return DatabaseManager.executeUpdate("update_profile.sql", premium, tickets, credits, coins, paypalEmail, uuid.toString());
    }

}
