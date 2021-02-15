package teams;

import games.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public final class TeamManager {

    public static Team RED_TEAM = new Team("Red", "red", ChatColor.RED) {
        public void onAdd(Player p) {

        }

        public void onRemove(Player p) {

        }
    };

    public static Team BLUE_TEAM = new Team("Blue", "blue", ChatColor.AQUA) {
        public void onAdd(Player p) {

        }

        public void onRemove(Player p) {

        }
    };

    public static Team SPECTATORS_TEAM = new Team("Spectators", "spectators", ChatColor.GRAY) {
        public void onAdd(Player p) {
            p.setGameMode(GameMode.SPECTATOR);
        }

        public void onRemove(Player p) {
            p.setGameMode(GameMode.ADVENTURE);
        }
    };

    private static Team[] TEAM_LIST = new Team[]{
            RED_TEAM,
            BLUE_TEAM,
            SPECTATORS_TEAM
    };

    public static void switchTeam(Player p, Team to) {
        for (Team t: TEAM_LIST) t.removePlayer(p);
        to.addPlayer(p);
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

}
