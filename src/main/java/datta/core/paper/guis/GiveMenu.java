package datta.core.paper.guis;

import datta.core.paper.utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.utilities.MenuBuilder.slot;

public class GiveMenu {

    public static void open(Player player, ItemStack itemStack, int page) {
        menuBuilder.createMenu(player, "               Dar objeto", 9 * 6, false);
        menuBuilder.setContents(player, () -> {

            menuBuilder.setItem(player, slot(5, 6), new ItemBuilder(Material.BARRIER, "&cVolver al menú",
                    "", "&7Haz clic para volver al menú.").build(), () -> {
                GameMenu.open(player);
            });

            menuBuilder.setItem(player, slot(7, 6), new ItemBuilder(Material.PLAYER_HEAD, "&a¡Obténlo en tu inventario!",
                    "", "&7Haz clic para obtener").buildPlayerHead(player.getName()), () -> {
                player.getInventory().addItem(itemStack);
            });

            menuBuilder.setItem(player, slot(1, 6), new ItemBuilder(Material.ARROW, "&ePagina anterior",
                    "", "&7Haz clic para ir a la página anterior.").build(), () -> {
                if (page > 0) {
                    open(player, itemStack, page - 1);
                }
            });

            menuBuilder.setItem(player, slot(9, 6), new ItemBuilder(Material.ARROW, "&aSiguiente página",
                    "", "&7Haz clic para ir a la siguiente página.").build(), () -> {
                open(player, itemStack, page + 1);
            });

            List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
            int[] allowedSlots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};

            for (int i = 0; i < allowedSlots.length; i++) {
                int slot = allowedSlots[i];
                if (page * allowedSlots.length + i < list.size()) {
                    Player p = list.get(page * allowedSlots.length + i);
                    menuBuilder.setItem(player, slot, new ItemBuilder(Material.PLAYER_HEAD, "&a" + p.getName(),
                            "", "&7Haz clic para ver la información del jugador.").buildPlayerHead(p.getName()), () -> {
                        p.getInventory().addItem(itemStack);
                    });
                } else {
                    menuBuilder.setItem(player, slot, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, "&cVacío",
                            "", "&7No hay jugadores en esta posición.").build(), () -> {

                    });
                }
            }
        });
    }
}