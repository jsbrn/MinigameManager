package games;

import main.GameManagerPlugin;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class HelltowerGameInstance extends GameInstance {

    private Rectangle lavaBounds;
    private int lavaHeight, finishHeight;
    private final int maxLavaHeight;

    public HelltowerGameInstance() {
        super(MinigameMode.PARKOUR_RACE, "rising_lava", 2, 16);
        this.lavaBounds = new Rectangle(-6, -6, 13, 13);
        this.lavaHeight = 64;
        this.maxLavaHeight = 104;
        this.finishHeight = 109;
    }

    private BukkitRunnable liftLava = new BukkitRunnable() {
        public void run() {
            World w = getWorld();
            lavaHeight++;
            for (int i = 0; i < lavaBounds.width; i++) {
                for (int j = 0; j < lavaBounds.height; j++) {
                    w.getBlockAt(-6 + i, lavaHeight, -6 + j).setType(Material.LAVA);
                }
            }
            if (lavaHeight >= maxLavaHeight) this.cancel();
        }
    };

    public void onStart() {
        liftLava.runTaskTimer(GameManagerPlugin.getInstance(), 20*5, 20*5);
    }

    public boolean onNext() {
        return false;
    }

    public void onStop() {
        liftLava.cancel();
    }

    public void onFinish() {
        liftLava.cancel();
    }

    @EventHandler
    public void onPlayerMoveToRooftop(PlayerMoveEvent moveEvent) {
        if (isFinished()) return;
        if (moveEvent.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (!moveEvent.getPlayer().getLocation().getWorld().equals(getWorld())) return;
        if (moveEvent.getPlayer().getLocation().getBlockY() >= 109) {
            finish();
            for (Player p: getActivePlayers()) {
                p.sendTitle(ChatColor.YELLOW + moveEvent.getPlayer().getName() + " won!", "Type /lobby to exit the game.", 0, 20 * 5, 20);
                if (!p.equals(moveEvent.getPlayer())) p.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    @EventHandler
    public void onPlayerTakesDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        if (!event.getEntity().getLocation().getWorld().equals(getWorld())) return;
        Player p = (Player)event.getEntity();
        if (p.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            p.sendTitle(ChatColor.RED + "You Died!", "Better luck next time.", 0, 20*5, 20);
            p.sendMessage(ChatColor.GRAY+"Use your navigator to go back to the lobby.");
            p.setGameMode(GameMode.SPECTATOR);
        }
    }

}
