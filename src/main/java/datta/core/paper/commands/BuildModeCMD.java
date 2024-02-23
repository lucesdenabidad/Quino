package datta.core.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.Utils.send;

public class BuildModeCMD extends BaseCommand {

    public static List<Player> buildMode = new ArrayList<>();
    @CommandPermission("quino.cmds.build")
    @CommandAlias("buildmode|build")
    public void buildMode(Player player){

        if (!buildMode.contains(player)) {
            buildMode.add(player);
            player.sendTitle(format("&e&lBuild Mode"), format("&a[✔] Activado!"));
            send(player, "&aHas activado el modo de construcción correctamente.");
        }else{
            buildMode.remove(player);
            player.sendTitle(format("&e&lBuild Mode"), format("&c[❌] Deshabilitado!"));

            send(player, "&cHas desactivado el modo de construcción correctamente.");
        }

    }
}
