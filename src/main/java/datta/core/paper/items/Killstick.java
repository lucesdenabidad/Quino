package datta.core.paper.items;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.Core;
import datta.core.paper.utilities.ItemBuilder;
import datta.core.paper.utilities.Utils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Killstick extends BaseCommand implements Listener {

    public static ItemStack itemStack = new ItemBuilder(Material.STICK, "&c¡Palo de gracia!", "&c¡Pégale a un jugador para matarlo!").build();

    @CommandPermission("quino.item.killstick")
    @CommandAlias("killstick")
    public static void get(Player player){
        player.getInventory().addItem(itemStack);
        Utils.send(player, "&aObtuviste el palo 'killstick' en tu inventario.");
    }

    @EventHandler
    public void playerTryDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player player) {
            if (player.getInventory().getItemInMainHand().isSimilar(itemStack)) {
                if (e.getEntity() instanceof Player target) {
                    handleKillStick(target, e);
                }
            }
        }
    }

    public void handleKillStick(Player victim, EntityDamageByEntityEvent event) {
        event.setDamage(0);

        if (victim.hasMetadata("palitoxdddd")) {
            return;
        }
        victim.setMetadata("palitoxdddd", new FixedMetadataValue(Core.getPlugin(Core.class), "sí"));

        new BukkitRunnable() {
            @Override
            public void run() {
                org.bukkit.util.Vector dirBetweenLocations = new Vector(0,1.5,0);
                victim.setVelocity(dirBetweenLocations);
            }
        }.runTaskLater(Core.getPlugin(Core.class), 2L);

        new BukkitRunnable() {
            @Override
            public void run() {
                victim.removeMetadata("palitoxdddd", Core.getInstance());
                if (!victim.isOnline()) {
                    cancel();
                    return;
                }
                Location location = victim.getLocation();
            }
        }.runTaskTimer(Core.getPlugin(Core.class), 2L, 3);

        new BukkitRunnable() {
            @Override
            public void run() {
                InstantFirework(victim.getLocation(), FireworkEffect
                        .builder()
                        .withColor(Color.RED, Color.RED, Color.RED, Color.RED).build());
                Utils.eliminateDeath(victim);
            }
        }.runTaskLater(Core.getPlugin(Core.class), 30L);
    }

    public static void InstantFirework(Location loc, FireworkEffect effect) {
        Firework f = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(effect);
        f.setFireworkMeta(fm);
        try {
            Object eF = f.getClass().getMethod("getHandle").invoke(f);
            Field fl = eF.getClass().getDeclaredField("expectedLifespan");
            fl.setAccessible(true);
            fl.set(eF, 1);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException |
                 NoSuchMethodError var6) {
        }
    }
}