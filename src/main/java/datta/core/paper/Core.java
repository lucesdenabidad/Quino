package datta.core.paper;

import co.aikar.commands.PaperCommandManager;
import datta.core.paper.commands.GlobalCMD;
import datta.core.paper.guis.ExampleGUI;
import datta.core.paper.listeners.GlobalListener;
import datta.core.paper.utilities.MenuBuilder;
import datta.core.paper.utilities.NegativeSpaces;
import datta.core.paper.utilities.TCT.BukkitTCT;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class Core extends JavaPlugin {

    private static @Getter Core instance;
    private @Getter Game game;
    private @Getter PaperCommandManager commandManager;

    public static MenuBuilder menuBuilder;
    @Override
    public void onEnable() {
        instance = this;
        menuBuilder = new MenuBuilder(this);

        BukkitTCT.registerPlugin(this);
        NegativeSpaces.registerCodes();

        game = new Game(this);
        game.runTaskTimerAsynchronously(this, 0L, 20L);

        //LISTENERS
        Bukkit.getPluginManager().registerEvents(new GlobalListener(this), this);

        //COMMANDS
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new GlobalCMD(this));
        commandManager.registerCommand(new ExampleGUI());
    }

    @Override
    public void onDisable() {

    }

}