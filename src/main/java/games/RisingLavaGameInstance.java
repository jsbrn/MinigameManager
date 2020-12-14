package games;

import main.GameInstance;
import main.GameManagerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import util.GameManager;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class RisingLavaGameInstance extends GameInstance {

    private Rectangle lavaBounds;
    private int lavaHeight;
    private final int maxLavaHeight;

    public RisingLavaGameInstance() {
        super("rising_lava", 2, 16);
        this.lavaBounds = new Rectangle(-6, -6, 13, 13);
        this.lavaHeight = 64;
        this.maxLavaHeight = 104;
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

}
