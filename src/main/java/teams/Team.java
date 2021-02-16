package teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class Team {

    private String name, id;
    private ChatColor chatColor;
    private Color teamColor;
    private ArrayList<Player> players;

    public Team(String name, String teamID, ChatColor chatColor, Color teamColor) {
        this.name = name;
        this.chatColor = chatColor;
        this.teamColor = teamColor;
        this.id = teamID;
        this.players = new ArrayList<Player>();
    }

    public void addPlayer(Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join "+id+" "+p.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "luckperms user "+p.getName()+" parent add "+ id);
        players.add(p);
        onAdd(p);
    }

    public void removePlayer(Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team leave "+id);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "luckperms user "+p.getName()+" parent remove "+ id);
        players.remove(p);
        onRemove(p);
    }

    public boolean hasPlayer(Player p) {
        return players.contains(p);
    }

    public abstract void onAdd(Player p);
    public abstract void onRemove(Player p);

    public String getName() {
        return chatColor+name;
    }

    public String getID() {
        return id;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Color getTeamColor() {
        return teamColor;
    }

    public int size() {
        return players.size();
    }
}
