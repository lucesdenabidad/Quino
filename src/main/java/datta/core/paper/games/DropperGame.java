package datta.core.paper.games;

import datta.core.paper.Core;
import datta.core.paper.Game;
import datta.core.paper.Stage;
import datta.core.paper.services.Stars;
import datta.core.paper.utilities.WorldEditTool;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitScheduler;

import static datta.core.paper.services.Stars.giveAllStarsWithCondition;
import static datta.core.paper.utilities.Color.stringToLocation;

public class DropperGame {

    public static void llenarConRetraso(String p1, String p2) {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        String[] materiales = {"RED_STAINED_GLASS", "ORANGE_STAINED_GLASS", "YELLOW_STAINED_GLASS", "GREEN_STAINED_GLASS", "LIME_STAINED_GLASS", "AIR"};

        for (int i = 0; i < materiales.length; i++) {
            int ticks = i * 20;
            final Material material = Material.valueOf(materiales[i]);
            scheduler.runTaskLater(Core.getInstance(), () -> {
                WorldEditTool.fill(stringToLocation(p1), stringToLocation(p2), material);
            }, ticks);
        }
    }

    public static class Dropper1 extends Game {

        public Dropper1() {
            super("Dropper #1", false, false, true, false, false, true);
        }

        Location location = stringToLocation("350 43 219");
        String s1 = "366 42 203";
        String s2 = "351 42 218";

        @Override
        public void start() {
            llenarConRetraso(s1, s2);
            start(5, () -> {
                Core.getInstance().setStage(Stage.Game);
                Bukkit.getOnlinePlayers().forEach(t -> t.playSound(t.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1));
            });
        }

        @Override
        public void teleport() {
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);

            teleportAll(location);
        }

        @Override
        public void stop() {
            giveAllStarsWithCondition(2, Stars.Condition.ALIVE);
        }

        @Override
        public void cancel() {
            defaultCancel();
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);
            teleport();
        }
    }

    public static class Dropper2 extends Game {

        public Dropper2() {
            super("Dropper #2", false, false, true, false, false, true);
        }

        Location location = stringToLocation("330 43 202");
        String s1 = "328 42 204";
        String s2 = "315 42 217";

        @Override
        public void start() {
            llenarConRetraso(s1, s2);
            start(5, () -> {

                Bukkit.getOnlinePlayers().forEach(t -> t.playSound(t.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1));

            });
        }

        @Override
        public void teleport() {
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);
            teleportAll(location);

        }

        @Override
        public void stop() {
            giveAllStarsWithCondition(2, Stars.Condition.ALIVE);
        }

        @Override
        public void cancel() {
            defaultCancel();
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);
            teleport();
        }
    }

    public static class Dropper3 extends Game {

        public Dropper3() {
            super("Dropper #3", false, false, true, false, false, true);
        }

        Location location = stringToLocation("293 43 202");
        String s1 = "291 42 204";
        String s2 = "278 42 217";

        @Override
        public void start() {
            llenarConRetraso(s1, s2);
            start(5, () -> {

                Bukkit.getOnlinePlayers().forEach(t -> t.playSound(t.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1));

            });
        }

        @Override
        public void teleport() {
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);
            teleportAll(location);
        }

        @Override
        public void stop() {
            giveAllStarsWithCondition(2, Stars.Condition.ALIVE);
        }

        @Override
        public void cancel() {
            defaultCancel();
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);
            teleport();
        }
    }

    public static class Dropper4 extends Game {

        public Dropper4() {
            super("Dropper #4", false, false, true, false, false, true);
        }


        Location location = stringToLocation("256 43 202");
        String s1 = "254 42 204";
        String s2 = "241 42 217";

        @Override
        public void start() {
            llenarConRetraso(s1, s2);
            start(5, () -> {

                Bukkit.getOnlinePlayers().forEach(t -> t.playSound(t.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1));

            });
        }

        @Override
        public void teleport() {
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);
            teleportAll(location);
        }

        @Override
        public void stop() {
            giveAllStarsWithCondition(2, Stars.Condition.ALIVE);
        }

        @Override
        public void cancel() {
            defaultCancel();
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);
            teleport();
        }
    }

    public static class Dropper5 extends Game {

        public Dropper5() {
            super("Dropper #5", false, false, true, false, false, true);
        }


        Location location = stringToLocation("219 43 202");
        String s1 = "213 42 208";
        String s2 = "210 42 211";

        @Override
        public void start() {
            llenarConRetraso(s1, s2);
            start(5, () -> {

                Bukkit.getOnlinePlayers().forEach(t -> t.playSound(t.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1));

            });
        }

        @Override
        public void teleport() {
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);

            teleportAll(location);
        }

        @Override
        public void stop() {
            giveAllStarsWithCondition(2, Stars.Condition.ALIVE);
        }

        @Override
        public void cancel() {
            defaultCancel();
            WorldEditTool.fill(stringToLocation(s1), stringToLocation(s2), Material.GRAY_STAINED_GLASS);
            teleport();
        }
    }
}