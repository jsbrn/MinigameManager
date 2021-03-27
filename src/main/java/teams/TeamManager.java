package teams;

import games.GameManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public final class TeamManager {

    public static Team RED_TEAM = new Team("Red", "red", ChatColor.RED, Color.RED) {
        public void onAdd(Player p) {

        }

        public void onRemove(Player p) {

        }
    };

    public static Team BLUE_TEAM = new Team("Blue", "blue", ChatColor.AQUA, Color.AQUA) {
        public void onAdd(Player p) {

        }

        public void onRemove(Player p) {

        }
    };

    public static Team SPECTATORS_TEAM = new Team("Spectators", "spectators", ChatColor.GRAY, Color.GRAY) {
        public void onAdd(Player p) {
            p.setGameMode(GameMode.SPECTATOR);
        }

        public void onRemove(Player p) {
            if (GameManager.getActiveGame() != null)
                p.setGameMode(GameManager.getActiveGame().getDefaultGameMode());
        }
    };

    private static Team[] TEAM_LIST = new Team[]{
            RED_TEAM,
            BLUE_TEAM,
            SPECTATORS_TEAM
    };

    public static void switchTeam(Player p, Team to, String reason, boolean verbose) {
        for (Team t: TEAM_LIST) t.removePlayer(p);
        to.addPlayer(p);
        if (verbose) {
            p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0f, 1.0f);
            if (to.equals(TeamManager.SPECTATORS_TEAM))
                p.sendTitle(ChatColor.GRAY+"Now Spectating", "Type "+ChatColor.YELLOW+"/jointeam "+ChatColor.BOLD+""+ChatColor.RED+"red"+ChatColor.GRAY+""+ChatColor.RESET+" or "+ChatColor.BOLD+""+ChatColor.AQUA+"blue"+ChatColor.RESET+""+ChatColor.WHITE+" to play.", 10, 80, 10);
            else
                p.sendTitle(to.getChatColor()+"Team "+to.getName(), reason != null ? reason : "", 10, 80, 10);
        }
        if (GameManager.getActiveGame() != null)
            GameManager.getActiveGame().onTeamSwitch(p, to);
    }

    public static Team getTeam(Player p) {
        for (Team t: TEAM_LIST)
            if (t.hasPlayer(p)) return t;
        return null;
    }

    public static Team getTeam(String id) {
        for (Team t: TEAM_LIST)
            if (t.getID().equals(id)) return t;
        return null;
    }

    public static Team getSmallestTeam() {
        int count = Integer.MAX_VALUE;
        Team smallest = RED_TEAM;
        for (Team t : TEAM_LIST) {
            if (t != SPECTATORS_TEAM && t.size() < count) {
                count = t.size();
                smallest = t;
            }
        }
        return smallest;
    }

    public static Team[] getTeamList() {
        return TEAM_LIST;
    }

    /**
     * This plugin uses the default teams to apply collision and visibility rules natively.
     * TeamManager is essentially a wrapper for the built in teams functionality.
     */
    private static boolean initialized = false;
    public static void initMinecraftTeams() {
        if (initialized) return;
        for (Team t: TEAM_LIST) {
            Bukkit.getLogger().info("Setting up vanilla team "+t.getName()+" (id: "+t.getID()+")");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team remove "+t.getID());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add "+t.getID()+" \""+t.getName()+"\"");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team modify "+t.getID()+" friendlyFire false");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team modify "+t.getID()+" collisionRule pushOtherTeams");
        }
        initialized = true;
    }

}
