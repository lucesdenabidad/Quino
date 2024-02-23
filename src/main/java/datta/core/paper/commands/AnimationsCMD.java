package datta.core.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import datta.core.paper.animations.Animation1;
import datta.core.paper.animations.Animation2;
import org.bukkit.entity.Player;


@CommandPermission("quino.cmds.animations")
@CommandAlias("animations")
public class AnimationsCMD extends BaseCommand {

    @Subcommand("play")
    public void play(Player player){
        Animation2 animation = new Animation2();
        animation.play(player, player.getName(), player.getLocation());

    }
}
