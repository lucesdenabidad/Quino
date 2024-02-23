package datta.core.paper.events;

import datta.core.paper.Core;
import datta.core.paper.Stage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static datta.core.paper.utilities.Color.format;

public class DeathEvent implements Listener {


    @Getter
    @Setter
    public static boolean kick = false;
    @Getter
    @Setter
    public static boolean spectator = true;
    @Getter
    @Setter
    public static boolean dropItems = true;

    @EventHandler
    public void death(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location location = player.getLocation();
        Stage currentStage = Core.getInstance().getStage();
        String msg = "";
        if (player.getKiller() == null) {
            msg = "&c{0} ha muerto.";
        } else {
            msg = "&c{0} fue asesinado por " + player.getKiller().getName() + ".";
        }

        msg = format(msg, player.getName());
        event.setDeathMessage(msg);

        if (!currentStage.equals(Stage.Waiting)) {

            Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> {

                if (!player.isOnline()) {
                    return;
                }

                if (dropItems) {
                    List<ItemStack> list = Arrays.stream(player.getInventory().getContents().clone()).toList();
                    player.getInventory().clear();

                    for (ItemStack i : list) {
                        if (i != null && i.getType() != Material.AIR) {
                            world.dropItem(location, i);
                        }
                    }
                }
                if (kick && !player.isOp()) {
                    player.kickPlayer(format("&c¡Gracias por participar!"));
                }

                if (spectator) {
                    player.setGameMode(GameMode.SPECTATOR);
                }

            }, 1L);

            event.setCancelled(true);
        }
    }
}