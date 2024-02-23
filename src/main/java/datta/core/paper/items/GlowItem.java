package datta.core.paper.items;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import datta.core.paper.utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static datta.core.paper.utilities.Utils.send;

public class GlowItem extends BaseCommand implements Listener {

    public static ItemStack itemStack = new ItemBuilder(Material.GLOW_BERRIES, "&eElemento de luz &7(Clic derecho)", "&7Dales 'GLOWING' a todos jugadores", "&7durante 30 segundos!").build();

    @CommandPermission("quino.item.kickstick")
    @CommandAlias("glowitem")
    public static void get(Player player, @Optional @Default("1") int m) {
        for (var i = 0; i < m; i++) {
            player.getInventory().addItem(itemStack);
        }

        send(player, "&aObtuviste el '"+itemStack.getItemMeta().getDisplayName().substring(0,17)+"&a' en tu inventario.");
    }

    @EventHandler
    public void playerTryDamage(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasItem()) {
            ItemStack item = e.getItem();
            if (item.isSimilar(itemStack)) {
                handle(player, item);
            }
        }
    }

    public void handle(Player player, ItemStack itemStack) {
        int amount = itemStack.getAmount();
        itemStack.setAmount(amount - 1);
        for (Player t : Bukkit.getOnlinePlayers()) {
            t.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,20*30,1));
        }

        send(player, "&2(&a!&2) &aConsumiste un elemento de luz. Los jugadores brillarán durante 30 segundos.");
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