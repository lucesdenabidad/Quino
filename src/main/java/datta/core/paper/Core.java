package datta.core.paper;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import datta.core.paper.commands.AnimationsCMD;
import datta.core.paper.commands.BuildModeCMD;
import datta.core.paper.commands.GlobalCMD;
import datta.core.paper.commands.StageCMD;
import datta.core.paper.events.DeathEvent;
import datta.core.paper.events.JoinEvent;
import datta.core.paper.guis.GameMenu;
import datta.core.paper.items.*;
import datta.core.paper.listeners.PlaceholderAPIExpansion;
import datta.core.paper.services.Stars;
import datta.core.paper.services.Timer;
import datta.core.paper.utilities.MenuBuilder;
import datta.core.paper.utilities.NegativeSpaces;
import datta.core.paper.utilities.TCT.DelayTask;
import datta.core.paper.utilities.score.ScoreHolder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


public class Core extends JavaPlugin implements @NotNull Listener {

    private static @Getter Core instance;
    private @Getter Game game;
    private @Getter PaperCommandManager commandManager;
    @Getter
    @Setter
    public Stage stage;

    public static MenuBuilder menuBuilder;
    PlaceholderAPIExpansion expansion = new PlaceholderAPIExpansion();

    @Override
    public void onEnable() {
        instance = this;
        menuBuilder = new MenuBuilder(this);
        stage = Stage.Waiting;

        new DelayTask(this);
        NegativeSpaces.registerCodes();

        //LISTENERS
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new Game.GameListeners(), this);

        //COMMANDS
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new GlobalCMD(this));
        commandManager.registerCommand(new BuildModeCMD());
        commandManager.registerCommand(new AnimationsCMD());
        commandManager.registerCommand(new GameMenu());
        commandManager.registerCommand(new StageCMD());
        commandManager.registerCommand(new Stars());


        registerBoth(new Kickstick(),commandManager, this);
        registerBoth(new Killstick(),commandManager, this);
        registerBoth(new GlowItem(),commandManager, this);
        registerBoth(new CenterItem(),commandManager, this);
        registerBoth(new DisguiseItem(),commandManager, this);
        registerBoth(new GameItem(),commandManager, this);

        ScoreHolder holder = new ScoreHolder(this,
                "&e&lEvento",
                "",
                "%quino_level%",
                "",
                "&fEstrellas: &6%quino_stars% ⭐",
                "&fVivos: &a%quino_survival%",
                "",
                "&ediscord.holy.gg");

        holder.start(0, 20L);

        expansion.register();
    }

    @Override
    public void onDisable() {
        expansion.unregister();
        Timer.removeBar();
    }

    public void registerBoth(Object object, PaperCommandManager manager, JavaPlugin plugin){
        manager.registerCommand((BaseCommand) object);
        Bukkit.getPluginManager().registerEvents((Listener) object, plugin);
    }
}