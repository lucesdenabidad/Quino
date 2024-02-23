package datta.core.paper.games;

import datta.core.paper.Game;
import datta.core.paper.utilities.WorldEditTool;
import org.bukkit.Material;

import static datta.core.paper.utilities.Color.stringToLocation;

public class WaterDropGame {
    public static void level(Game game, int level) {
        int E0 = -59;
        int i = E0 + level;
        int it = i - 1;

        String s1 = "-5 " + it + " -53";
        String s2 = "5 " + it + " -63";

        game.teleportAll(stringToLocation("0 " + i + " -58").toCenterLocation());
        WorldEditTool.fill(stringToLocation(s1), stringToLocation(s1), Material.GRAY_STAINED_GLASS);
        DropperGame.llenarConRetraso(s1, s2);
    }
}