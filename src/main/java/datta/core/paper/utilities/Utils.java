package datta.core.paper.utilities;

import datta.core.paper.Core;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.utilities.Color.format;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class Utils {


    public static void send(CommandSender sender, String... msg) {
        if (sender instanceof Player) {
            ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        }

        for (String s : msg) {
            sender.sendMessage(format(s));
        }
    }

    public static ItemStack buildItemSkull(String name, String owner, int amount) {
        ItemStack Item = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta ItemM = (SkullMeta) Item.getItemMeta();
        ItemM.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        ItemM.setOwner(owner);
        Item.setItemMeta(ItemM);
        return Item;
    }

    public static List<Location> createCylinder2(Location base, double radius, int quantity) {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            double angle, x, z;
            angle = 2 * Math.PI * i / quantity;
            x = Math.cos(angle) * radius;
            z = Math.sin(angle) * radius;
            locations.add(base.clone().add(x, 0, z));
        }
        return locations;
    }

    public static Location genLocationInPos(Location pos1, Location pos2, boolean mantenerY) {
        World world = pos1.getWorld();

        double x = randomDouble(pos1.getX(), pos2.getX());
        double y = mantenerY ? pos1.getY() : randomDouble(pos1.getY(), pos2.getY());
        double z = randomDouble(pos1.getZ(), pos2.getZ());

        return new Location(world, x, y, z).toCenterLocation();
    }

    private static double randomDouble(double min, double max) {
        return min + Math.random() * (max - min);
    }


    public static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    static int countdownTask;


    public static void countdown(int time) {
        countdown(time, () -> {
        });
    }

    public static void countdown(int time, Runnable runnable) {
        int[] countdownTime = new int[]{time};
        countdownTask = Bukkit.getScheduler().runTaskTimer(Core.getInstance(), () -> {
            if (countdownTime[0] > 0) {
                Bukkit.broadcastMessage(format("&e&lEvento &8> &fEl nivel inicia en &a" + countdownTime[0] + "&f."));
                Bukkit.getOnlinePlayers().forEach(t -> t.playSound(t.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2));

                countdownTime[0]--;
            } else {
                runnable.run();
                Bukkit.getScheduler().cancelTask(countdownTask);
            }
        }, 0L, 20L).getTaskId();
    }

    public static void eliminateDeath(Player player) {
        Bukkit.broadcastMessage(format("&e&lEvento &8> &f¡Se ha eliminado a &a" + player.getName() + "&f!"));
        player.getLocation().getWorld().spigot().strikeLightningEffect(player.getLocation(), false);
        player.setHealth(0);
        player.removeMetadata("palitoxdddd", Core.getInstance());
    }

    public static void eliminate(Player player) {
        Bukkit.broadcastMessage(format("&e&lEvento &8> &f¡Se ha eliminado a &a" + player.getName() + "&f!"));
        player.getLocation().getWorld().spigot().strikeLightningEffect(player.getLocation(), false);
        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&c¡Gracias por participar!"));
        player.removeMetadata("palitoxdddd", Core.getInstance());
    }
}