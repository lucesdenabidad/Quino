package datta.core.paper.guis;

import datta.core.paper.services.Stars;
import datta.core.paper.utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.utilities.Color.formatList;
import static datta.core.paper.utilities.MenuBuilder.slot;
import static datta.core.paper.utilities.Utils.send;

public class PlayersMenu {

    public static void open(Player player, int page) {
        menuBuilder.createMenu(player, "         Menu de jugadores", 9 * 6, false);
        menuBuilder.setContents(player, () -> {
            menuBuilder.setItem(player, slot(5, 6), new ItemBuilder(Material.BARRIER, "&cVolver al menú",
                    "", "&7Haz clic para volver al menú.").build(), () -> {
                GameMenu.open(player);
            });

            menuBuilder.setItem(player, slot(1, 6), new ItemBuilder(Material.ARROW, "&ePagina anterior",
                    "", "&7Haz clic para ir a la página anterior.").build(), () -> {
                if (page > 0) {
                    open(player, page - 1);
                }
            });

            menuBuilder.setItem(player, slot(9, 6), new ItemBuilder(Material.ARROW, "&aSiguiente página",
                    "", "&7Haz clic para ir a la siguiente página.").build(), () -> {
                open(player, page + 1);
            });


            List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
            int[] allowedSlots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};

            for (int i = 0; i < allowedSlots.length; i++) {
                int slot = allowedSlots[i];
                if (page * allowedSlots.length + i < list.size()) {
                    Player p = list.get(page * allowedSlots.length + i);
                    menuBuilder.setItem(player, slot, new ItemBuilder(Material.PLAYER_HEAD, "&a" + p.getName(),
                            "", "&7Haz clic para ver la información del jugador.").buildPlayerHead(p.getName()), () -> {
                        subMenu(player, p);
                    });
                } else {
                    menuBuilder.setItem(player, slot, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, "&cVacío",
                            "", "&7No hay jugadores en esta posición.").build(), () ->{

                    });
                }
            }
        });
    }

    public static void subMenu(Player player, Player target) {
        menuBuilder.createMenu(player, "         Jugador > " + target.getName(), 9 * 4, false);
        menuBuilder.setContents(player, () -> {
            menuBuilder.setItem(player, slot(4, 4), new ItemBuilder(Material.PLAYER_HEAD, "&a" + target.getName(),
                    formatList(target,
                            "&fEstrellas: &6" + Stars.getStars(target) + " ⭐",
                            "&fRango: &a%luckperms_prefix%",
                            "&fUbicacion: &a%player_x%, %player_y%, %player_z%",
                            "")).buildPlayerHead(target.getName()), () -> {

            });
            menuBuilder.setItem(player, slot(5, 4), new ItemBuilder(Material.BARRIER, "&cVolver al menú",
                    "", "&7Haz clic para volver al menú.").build(), () -> {
                open(player, 0);
            });

            menuBuilder.setItem(player, slot(2, 2), new ItemBuilder(Material.BARREL, "&eDar objeto",
                    "", "&7Haz clic para abrir al menú.").build(), () -> {
            });

            menuBuilder.setItem(player, slot(3, 2), new ItemBuilder(Material.REPEATING_COMMAND_BLOCK, "&dCeder/Quitar operador",
                    "", "&7Haz clic para dar/quitar operador a " + target.getName() + ".").build(), () -> {
                boolean op = target.isOp();
                target.setOp(!op);
                send(player, "&2(&a!&2) &aHas alternado el operador al jugador &e" + target.getName() + "&a a &e" + !op + "&f.");
            });

        });
    }
}