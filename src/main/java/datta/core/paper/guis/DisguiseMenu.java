package datta.core.paper.guis;

import co.aikar.commands.BaseCommand;
import datta.core.paper.utilities.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.utilities.MenuBuilder.slot;

public class DisguiseMenu extends BaseCommand implements Listener {

    public static void open(Player player, int page) {
        menuBuilder.createMenu(player, "         Menu de bloques", 9 * 6, false);
        menuBuilder.setContents(player, () -> {
            menuBuilder.setItem(player, slot(5, 6), new ItemBuilder(Material.BARRIER, "&cCerrar menú",
                    "", "&7Haz clic para volver al menú.").build(), player::closeInventory);

            List<Material> list = new ArrayList<>(List.of(
                    Material.QUARTZ_PILLAR,
                    Material.LIGHT_GRAY_TERRACOTTA));
            int[] allowedSlots = {10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34};
            for (int i = 0; i < allowedSlots.length; i++) {
                int slot = allowedSlots[i];
                if (page * allowedSlots.length + i < list.size()) {
                    Material p = list.get(page * allowedSlots.length + i);
                    menuBuilder.setItem(player, slot, new ItemBuilder(Material.PLAYER_HEAD, "&a" + p.name(),
                            "", "&7Haz clic para ver la información del jugador.").buildPlayerHead(p.name()), () -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "disguiseplayer "+player.getName()+" Falling_Block "+p.name());
                    });
                } else {
                    menuBuilder.setItem(player, slot, new ItemBuilder(Material.BARRIER, "&cVacío",
                            "", "&7No hay jugadores en esta posición.").build(), null);
                }
            }
        });
    }
}