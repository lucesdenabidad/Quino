package datta.core.paper;

import datta.core.paper.commands.BuildModeCMD;
import datta.core.paper.events.DeathEvent;
import datta.core.paper.listeners.GameCallEvents;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.Utils.countdown;
import static datta.core.paper.utilities.Utils.send;
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class Game {

    long gameTime = 0;
    String name;

    public Game(String name, boolean breakblocks, boolean buildblocks, boolean falldamage, boolean pvp, boolean kick, boolean spectator) {
        this.name = name;

        GameListeners.setBreakblocks(breakblocks);
        GameListeners.setBuildblocks(buildblocks);
        GameListeners.setFalldamage(falldamage);
        GameListeners.setPvp(pvp);

        DeathEvent.setKick(kick);
        DeathEvent.setSpectator(spectator);
    }

    public abstract void start();

    public abstract void teleport();

    public abstract void stop();

    public abstract void cancel();

    public void defaultCancel() {
        callEvent(new GameCallEvents.GameCancelEvent(this.name));
    }

    public void defaultStart(int time, Runnable runnable) {
        callEvent(new GameCallEvents.GameStartEvent(this.name));
        countdown(time, runnable);
    }

    public void defaultStop() {
        callEvent(new GameCallEvents.GameStopEvent(this.name));

    }

    public void teleportAll(Location location) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(location.toCenterLocation());
        }
    }

    public void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    protected static void broadcast(String... s) {
        for (String e : s) {
            Bukkit.broadcastMessage(format(e));
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
        }
    }


    public static class GameListeners implements Listener {

        @Getter
        @Setter
        public static boolean breakblocks;
        @Getter
        @Setter
        public static boolean buildblocks;
        @Getter
        @Setter
        public static boolean falldamage;
        @Getter
        @Setter
        public static boolean pvp;

        @EventHandler
        public void onBlockBreak(BlockBreakEvent event) {
            if (!isBreakblocks()) {
                Player player = event.getPlayer();
                if (!BuildModeCMD.buildMode.contains(player)) {

                    event.setCancelled(true);
                }
            }
        }

        @EventHandler
        public void onBlockPlace(BlockPlaceEvent event) {
            if (!isBuildblocks()) {
                Player player = event.getPlayer();
                if (!BuildModeCMD.buildMode.contains(player)) {
                    event.setCancelled(true);
                }
            }
        }

        @EventHandler
        public void onEntityDamage(EntityDamageByEntityEvent event) {
            if (event.getDamager() instanceof Player damager) {
                if (event.getEntity() instanceof Player entity) {
                    if (!isPvp()) {
                        send(damager, "&cEl daño entre jugadores está desactivado.");
                        event.setCancelled(true);
                    }
                }
            }
        }

        @EventHandler
        public void onEntityDamage(EntityDamageEvent event) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                EntityDamageEvent.DamageCause cause = event.getCause();

                if (cause.equals(EntityDamageEvent.DamageCause.FALL) && !isFalldamage()) {
                    send(player, "&cEl daño por caída está desactivado.");
                    event.setCancelled(true);
                }
            }
        }

        @EventHandler
        public void onGameCancel(GameCallEvents.GameCancelEvent event) {
            String gameName = event.getGameName();

            broadcast("&e&lEvento &8> &fEl juego &c" + gameName + "&f fue cancelado.");
            Core.getInstance().setStage(Stage.Waiting);
        }

        @EventHandler
        public void onGameStart(GameCallEvents.GameStartEvent event) {
            Core.getInstance().setStage(Stage.Game);
        }

        @EventHandler
        public void onGameStart(GameCallEvents.GameStopEvent event) {
            String gameName = event.getGameName();

            broadcast("&e&lEvento &8> &fEl juego &c" + gameName + "&f ha terminado.");
            Core.getInstance().setStage(Stage.Waiting);
        }
    }
}
