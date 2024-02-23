package datta.core.paper.items;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.guis.GameMenu;
import datta.core.paper.utilities.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static datta.core.paper.utilities.Utils.send;

public class GameItem extends BaseCommand implements Listener {

    public static ItemStack itemStack = new ItemBuilder(Material.PLAYER_HEAD, "&aMenu de juego &7(Clic derecho)", "&7Abrir menu de juegos..").buildTexture("dade199d5b3a8b8c6fe2eeb628af38daada305f7c90d1bd1224cf1f071fcbe8b");

    @CommandPermission("quino.item.gamemenuitem")
    @CommandAlias("gamemenuitem")
    public static void get(Player player) {
        player.getInventory().setItem(8, itemStack);
        send(player, "&aObtuviste el '" + itemStack.getItemMeta().getDisplayName().substring(0, 17) + "&a' en tu inventario.");
    }

    @EventHandler
    public void event(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.hasItem()) {
            ItemStack item = e.getItem();
            if (item.isSimilar(itemStack)) {
                GameMenu.open(player);
            }
        }
    }
}