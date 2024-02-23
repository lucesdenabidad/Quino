package datta.core.paper.services;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static datta.core.paper.utilities.Utils.send;


@CommandPermission("quino.cmds.stars")
@CommandAlias("stars|estrellas|setstars")
public class Stars extends BaseCommand {
    @Subcommand("set")
    void set(CommandSender sender, OnlinePlayer onlinePlayer, int stars) {
        setStars(onlinePlayer.getPlayer(), stars);
        send(sender, "&aHas establecido " + stars + " estrellas para " + onlinePlayer.getPlayer().getName() + ".");
    }

    @Subcommand("add")
    void add(CommandSender sender, OnlinePlayer onlinePlayer, int stars) {
        addStars(onlinePlayer.getPlayer(), stars);
        send(sender, "&aHas añadido " + stars + " estrellas a " + onlinePlayer.getPlayer().getName() + ".");
    }

    @Subcommand("remove")
    void remove(CommandSender sender, OnlinePlayer onlinePlayer, int stars) {
        removeStars(onlinePlayer.getPlayer(), stars);
        send(sender, "&cHas quitado " + stars + " estrellas de " + onlinePlayer.getPlayer().getName() + ".");
    }

    @Subcommand("get")
    void get(CommandSender sender, OnlinePlayer onlinePlayer) {
        int stars1 = getStars(onlinePlayer.getPlayer());
        send(sender, "&a" + onlinePlayer.getPlayer().getName() + " tiene " + stars1 + " estrellas.");
    }


    private static final Map<Player, Integer> stars = new HashMap<>();


    public static void giveAllStarsWithCondition(int stars, Condition condition){
        for (Player player : Bukkit.getOnlinePlayers()) {
            giveStarsWithCondition(player,stars,condition);
        }
    }
    public static void giveStarsWithCondition(Player player, int stars, Condition condition) {
        if (condition == Condition.ALIVE) {
            if (player.getGameMode() != GameMode.SPECTATOR) {
                addStars(player, stars);
            }
        }


    }


    public static void setStars(Player player, int stars) {
        Stars.stars.put(player, stars);
        send(player, "&e[&6⭐&e] &eTus estrellas fueron actualizadas a " + getStars(player) + " ⭐.");
    }

    public static void addStars(Player player, int starsToAdd) {
        int currentStars = stars.getOrDefault(player, 0);
        stars.put(player, currentStars + starsToAdd);
        send(player, "&e[&6⭐&e] &eTus estrellas fueron actualizadas a " + getStars(player) + " ⭐.");
    }

    public static void removeStars(Player player, int starsToRemove) {
        int currentStars = stars.getOrDefault(player, 0);
        int newStars = Math.max(0, currentStars - starsToRemove);
        stars.put(player, newStars);
        send(player, "&e[&6⭐&e] &eTus estrellas fueron actualizadas a " + getStars(player) + " ⭐.");
    }

    public static int getStars(Player player) {
        return stars.getOrDefault(player, 0);
    }

    public enum Condition{
        ALIVE
    }
}