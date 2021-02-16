package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teams.Team;
import teams.TeamManager;

import java.util.ArrayList;
import java.util.List;

public class JoinTeamCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length <= 0) return false;
        Team to = args[0].equals("random") ? TeamManager.getSmallestTeam() : TeamManager.getTeam(args[0]);
        if (to == null) {
            sender.sendMessage(ChatColor.RED+"Team '"+args[0]+"' does not exist!");
            return true;
        }
        TeamManager.switchTeam((Player)sender, to, null, true);
        return true;
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> teams = new ArrayList<String>();
        for (Team t : TeamManager.getTeamList())
            teams.add(t.getID());
        return teams;
    }
}
