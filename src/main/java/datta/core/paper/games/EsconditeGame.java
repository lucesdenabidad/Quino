package datta.core.paper.games;

import datta.core.paper.Core;
import datta.core.paper.Game;
import datta.core.paper.Stage;
import datta.core.paper.items.GlowItem;
import datta.core.paper.items.Killstick;
import datta.core.paper.services.Stars;
import datta.core.paper.services.Timer;
import datta.core.paper.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import static datta.core.paper.utilities.Color.stringToLocation;

public class EsconditeGame extends Game {


    public static OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer("AQUINOby02");
    public static Player player = null;
    static {
        boolean online = offlinePlayer.isOnline();
        if (online){
            player = offlinePlayer.getPlayer();
        }
    }
    public EsconditeGame() {
        super("Escondite", false, false, false, false, false,true);
    }

    @Override
    public void start() {
        start(5, () -> {
            broadcast("&6(&e!&6) &eEl buscador fue teletransportado a la jaula del mapa.");
            player.teleport(stringToLocation("54 -37 152 90 2"));
            Core.getInstance().setStage(Stage.Game);
            Timer.timer("&#DA3B0FLos buscadores saldran en {time}.", BarColor.RED, BarStyle.SOLID, 60, () -> {
                player.teleport(stringToLocation("0 -42 5 0 0"));
                broadcast("&4(&c!&4) &cEl buscador ha aparecido en el inicio del mapa.");
                GlowItem.get(player,10);
                Killstick.get(player);
            });
        });
    }
    @Override
    public void teleport() {
        teleportAll(Utils.genLocationInPos(stringToLocation("3 -42 4"), stringToLocation("-3 -42 6"), true));
    }

    @Override
    public void stop() {
        Stars.giveAllStarsWithCondition(5, Stars.Condition.ALIVE);
    }

    @Override
    public void cancel() {

    }
}