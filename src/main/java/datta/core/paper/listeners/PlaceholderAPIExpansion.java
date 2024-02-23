package datta.core.paper.listeners;

import datta.core.paper.Core;
import datta.core.paper.services.Stars;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "quino";
    }

    @Override
    public @NotNull String getAuthor() {
        return "datta";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.3";
    }
    @Override
    public @NotNull String onRequest(OfflinePlayer offlinePlayer, String param) {
        Player player = offlinePlayer.getPlayer();

        if (param.equalsIgnoreCase("stars")){
            return ""+ Stars.getStars(player);
        }

        if (param.equalsIgnoreCase("level")){
            return Core.getInstance().getStage().getShowString();
        }


        if (param.equalsIgnoreCase("survival")) {
            return "" + Core.getInstance()
                    .getServer()
                    .getOnlinePlayers()
                    .stream()
                    .filter(p -> p.getGameMode() == GameMode.SURVIVAL)
                    .count();
        }

        return param;
    }
}
