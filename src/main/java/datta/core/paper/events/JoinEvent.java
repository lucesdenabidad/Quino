package datta.core.paper.events;

import datta.core.paper.Core;
import datta.core.paper.Stage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static datta.core.paper.utilities.Color.format;

public class JoinEvent implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Stage stage = Core.getInstance().getStage();
        World world = player.getWorld();
        Location spawnLocation = world.getSpawnLocation().toCenterLocation();

        Bukkit.getScheduler().runTask(Core.getInstance(), () -> {
            player.teleport(spawnLocation);
        });
        event.setJoinMessage(format("&#92ff5c{0} se unio al servidor", player.getName()));
    }
}
