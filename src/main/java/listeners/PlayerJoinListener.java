package listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import profiles.PlayerProfiles;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!PlayerProfiles.profileExists(event.getPlayer())) {
            event.getPlayer().sendMessage(ChatColor.GREEN+"Welcome to the server, "+event.getPlayer().getDisplayName()+"!");
            PlayerProfiles.create(event.getPlayer());
        } else {
            event.getPlayer().sendMessage(ChatColor.GREEN+"Welcome back!");
        }
    }

}
