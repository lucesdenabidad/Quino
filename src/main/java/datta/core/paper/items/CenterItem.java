package datta.core.paper.items;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.utilities.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static datta.core.paper.utilities.Utils.send;

public class CenterItem extends BaseCommand implements Listener {

    public static ItemStack itemStack = new ItemBuilder(Material.CLOCK, "&eCentrarse en el bloque &7(Clic derecho)", "&7Centrate en el centro del bloque.").build();

    @CommandPermission("quino.item.centeritem")
    @CommandAlias("centeritem")
    public static void get(Player player) {
        player.getInventory().addItem(itemStack);
        send(player, "&aObtuviste el '" + itemStack.getItemMeta().getDisplayName().substring(0, 17) + "&a' en tu inventario.");
    }

    @EventHandler
    public void event(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasItem()) {
            ItemStack item = e.getItem();
            if (item.isSimilar(itemStack)) {
                Location location = player.getLocation();
                int blockY = location.getBlockY();

                Location clone = location.toCenterLocation().clone();
                clone.setY(blockY);
                player.teleport(clone);

                send(player, "&2(&a!&2) &aTe teletransportaste al centro del bloque.");
            }
        }
    }
}