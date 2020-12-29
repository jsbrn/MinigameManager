package games;

import main.GameManagerPlugin;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.Random;

public class WaterDropGameInstance extends GameInstance {

    private final Rectangle dropBounds;
    private final int dropHeight, dropInterval, amountPerDrop;
    private final Random random;

    public WaterDropGameInstance() {
        super(MinigameMode.WATER_DROP, "cod_arena", 5, 16);
        this.dropHeight = 129;
        this.dropInterval = 20;
        this.amountPerDrop = 2;
        this.random = new Random();
        this.dropBounds = new Rectangle(-20, -20, 41, 41);
    }

    private BukkitRunnable dropRandomIngots = new BukkitRunnable() {
        public void run() {
            World w = getWorld();
            int amount = 1 + random.nextInt(amountPerDrop);
            for (int i = 0; i < amount; i++) {
                Location l = new Location(
                        w,
                        dropBounds.x + random.nextInt(dropBounds.width),
                        dropHeight,
                        dropBounds.y + random.nextInt(dropBounds.height));
                int distFromCenter = (int)l.distance(new Location(w, 0, dropHeight, 0));
                w.dropItem(l, new ItemStack(random.nextInt(distFromCenter) == 0 ? Material.DIAMOND : Material.GOLD_NUGGET, random.nextInt(2)+1));
            }
        }
    };

    public void onStart() {
        dropRandomIngots.runTaskTimer(GameManagerPlugin.getInstance(), 0, dropInterval);
        for (Player p: getActivePlayers()) {
            addRequiredItems(p);
        }
    }

    private void addRequiredItems(Player p) {
        ItemStack trident = new ItemStack(Material.TRIDENT, 1);
        trident.addEnchantment(Enchantment.RIPTIDE, 3);
        trident.addEnchantment(Enchantment.RIPTIDE, 3);
        ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS, 1);
        boots.addEnchantment(Enchantment.DEPTH_STRIDER, 3);
        boots.addEnchantment(Enchantment.SOUL_SPEED, 3);
        p.getInventory().addItem(trident);
        p.getInventory().setBoots(boots);
    }

    public boolean onNext() {
        return false;
    }

    public void onStop() {
        dropRandomIngots.cancel();
    }

    public void onFinish() {
        dropRandomIngots.cancel();
    }

    @EventHandler
    public void onChestClick(InventoryOpenEvent event) {
        System.out.println("inv opened");
        if (isFinished()) return;
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (!event.getPlayer().getLocation().getWorld().equals(getWorld())) return;
        System.out.println("passed requirements");
        if (event.getInventory().getType().equals(InventoryType.CHEST)) {
            System.out.println("is of type chest");
            ItemStack[] items = event.getPlayer().getInventory().getContents();
            System.out.println("there are "+items.length+" items");
            for (ItemStack i: items)
                if (i.getType().equals(Material.GOLD_NUGGET) || i.getType().equals(Material.DIAMOND)) event.getPlayer().getInventory().remove(i);
        }
    }

    @EventHandler
    public void onPlayerTakesDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        if (!event.getEntity().getLocation().getWorld().equals(getWorld())) return;
        Player p = (Player)event.getEntity();
        if (p.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1.0f, 1.0f);
            getWorld().createExplosion(p.getLocation(), 1);
            p.sendTitle(ChatColor.RED+"You died.", "", 0, 10, 5);
            p.teleport(new Location(getWorld(), dropBounds.x + random.nextInt(dropBounds.width), 67, dropBounds.x + random.nextInt(dropBounds.width)));
            p.getInventory().remove(Material.GOLD_NUGGET);
            p.getInventory().remove(Material.DIAMOND);
            p.setHealth(20);
            addRequiredItems(p);
        }
    }

}
