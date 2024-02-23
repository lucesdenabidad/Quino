package datta.core.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.Core;
import datta.core.paper.Stage;
import datta.core.paper.utilities.Utils;
import org.bukkit.command.CommandSender;

import static datta.core.paper.utilities.Utils.send;


public class StageCMD extends BaseCommand {

    @CommandPermission("quino.cmds.setstage")
    @CommandAlias("setstage|stage")
    public void setstage(CommandSender sender, Stage stage){
        Core.getInstance().setStage(stage);
        send(sender, "&aStage modificada a " + stage + ".");
    }
}
