package games;

import main.GameInstance;
import org.bukkit.Material;
import org.bukkit.World;

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

    private Timer lavaTimer = new Timer(true);
    private TimerTask liftLava = new TimerTask() {
        @Override
        public void run() {
            World w = getWorld();
            lavaHeight++;
            for (int i = 0; i < lavaBounds.width; i++) {
                for (int j = 0; j < lavaBounds.height; j++) {
                    w.getBlockAt(-6 + i, lavaHeight, -6 + j).setType(Material.LAVA);
                }
            }
        }
    };

    public void onStart() {
        lavaTimer.scheduleAtFixedRate(liftLava, 1000, 1000);
    }

    public boolean onNext() {
        return false;
    }

    public void onStop() {
        lavaTimer.cancel();
    }

    public void onFinish() {
        lavaTimer.cancel();
    }

}
