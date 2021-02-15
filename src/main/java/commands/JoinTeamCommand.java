package commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import teams.Team;
import teams.TeamManager;

public class JoinTeamCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length <= 0) return false;
        Team to = TeamManager.getTeam(args[0]);
        if (to == null) sender.sendMessage(ChatColor.RED+"Team '"+args[0]+"' does not exist!");
        TeamManager.switchTeam((Player)sender, to);
        return true;
    }

}
