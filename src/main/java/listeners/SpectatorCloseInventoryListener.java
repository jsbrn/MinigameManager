package listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class SpectatorCloseInventoryListener implements Listener {

    @EventHandler
    public void onSpectatorCloseMenu(InventoryCloseEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))
            event.getPlayer().sendMessage(ChatColor.YELLOW+"Type /jointeam "+ChatColor.RED+"red"+ChatColor.YELLOW+" or "+ChatColor.AQUA+"blue"+ChatColor.YELLOW+" to exit spectator mode.");
    }

}
