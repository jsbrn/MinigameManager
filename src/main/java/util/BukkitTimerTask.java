package util;

import main.GameManagerPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public abstract class BukkitTimerTask {

    private BukkitRunnable runnable;
    private long startTime, lastRun;
    private int millsDelay, millsPeriod, runCount, maxRuns, perXTicks;

    public BukkitTimerTask(int millsDelay, int millsPeriod) {
        this(millsDelay, millsPeriod, Integer.MAX_VALUE);
    }

    public BukkitTimerTask(int millsDelay, int millsPeriod, int totalRuns) {
        this.millsDelay = millsDelay;
        this.millsPeriod = millsPeriod;
        this.maxRuns = totalRuns;
        this.perXTicks = 1;
        final BukkitTimerTask that = this;
        this.runnable = new BukkitRunnable() {
            public void run() {
                that.checkTime();
            }
        };
    }

    public BukkitTimerTask(int millsDelay, int millsPeriod, int totalRuns, int perXTicks) {
        this(millsDelay, millsPeriod, totalRuns);
        this.perXTicks = perXTicks;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.lastRun = 0;
        this.runCount = 0;
        this.runnable.runTaskTimer(GameManagerPlugin.getInstance(), 0, perXTicks);
    }

    private void checkTime() {
        long timeSinceStart = System.currentTimeMillis() - startTime;
        long timeSinceLastRun = System.currentTimeMillis() - lastRun;
        if (runCount >= maxRuns) {
            this.runnable.cancel();
            return;
        }
        if (timeSinceStart >= millsDelay && timeSinceLastRun >= millsPeriod) {
            run();
            runCount++;
            lastRun = System.currentTimeMillis();
        }
    }

    protected abstract void run();

    public final void stop() {
        this.runnable.cancel();
    }

}
