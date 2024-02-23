package datta.core.paper.items;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.utilities.ItemBuilder;
import datta.core.paper.utilities.ParticleBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.utilities.Utils.send;

public class DisguiseItem extends BaseCommand implements Listener {

    public static ItemStack itemStack = new ItemBuilder(Material.BRICK, "&eCambiar de bloque &7(Clic derecho)", "&7Abrir menú para cambiar de bloque.").buildCustomModelData(1);

    @CommandPermission("quino.item.disguiseitem")
    @CommandAlias("disguiseitem")
    public static void get(Player player) {
        player.getInventory().setItem(0, itemStack);
        send(player, "&aObtuviste el '" + itemStack.getItemMeta().getDisplayName().substring(0, 17) + "&a' en tu inventario.");
    }

    List<Material> disableMaterials = new ArrayList<>(List.of(Material.PLAYER_HEAD, Material.CREEPER_HEAD, Material.SKELETON_SKULL, Material.WITHER_SKELETON_SKULL));

    @EventHandler
    public void event(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasItem() && e.hasBlock()) {
            ItemStack item = e.getItem();
            Block clickedBlock = player.getTargetBlock(5);
            if (disableMaterials.contains(clickedBlock.getType())) {
                send(player, "&cMaterial desactivado.");
                return;
            }
            if (clickedBlock != null && item.isSimilar(itemStack)) {
                Location centerLocation = player.getEyeLocation().subtract(0, 0.6, 0.3);
                Location location = clickedBlock.getLocation().toCenterLocation();

                ParticleBuilder.sendParticleLines(centerLocation, location);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "disguiseplayer " + player.getName() + " Falling_Block " + clickedBlock.getType().name());

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        Item itemDrop = event.getItemDrop();
        if (itemDrop.getItemStack().isSimilar(itemStack)) {
            event.setCancelled(true);
        }
    }
}