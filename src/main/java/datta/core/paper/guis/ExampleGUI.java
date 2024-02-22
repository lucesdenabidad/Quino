package datta.core.paper.guis;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import datta.core.paper.utilities.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.utilities.MenuBuilder.slot;

public class ExampleGUI extends BaseCommand {
    @CommandAlias("examplegui")
    public void open(Player player){
        menuBuilder.createMenu(player, "", 9*5, false);
        menuBuilder.setContents(player, () ->{
            menuBuilder.setItem(player,slot(2,2), new ItemBuilder(Material.APPLE,"&aExampleItem!", "", "")
                    .buildTexture("217995a7af2fae701a082cda1d385606c7ccbf0b2186114be900c9138cee690f"), () ->{
                player.sendMessage("Hola!");
            });
        });
    }
}
