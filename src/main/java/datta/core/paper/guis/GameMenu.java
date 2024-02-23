package datta.core.paper.guis;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.Core;
import datta.core.paper.Game;
import datta.core.paper.Stage;
import datta.core.paper.games.DropperGame;
import datta.core.paper.games.EsconditeGame;
import datta.core.paper.games.WaterDropGame;
import datta.core.paper.items.*;
import datta.core.paper.utilities.ItemBuilder;
import datta.core.paper.utilities.TCT.Parameters;
import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.MenuBuilder.slot;
import static datta.core.paper.utilities.Utils.send;

public class GameMenu extends BaseCommand {
    public static final List<Parameters<Game, Integer, String, Material>> games = new ArrayList<>(List.of(
            new Parameters<>(new EsconditeGame(), slot(2, 2), "217995a7af2fae701a082cda1d385606c7ccbf0b2186114be900c9138cee690f", Material.BEDROCK),
            new Parameters<>(new DropperGame.Dropper1(), slot(3, 2), "f516fa8b94174b0f79b1212c15ee305a3071b8929da001c94b54eac2580488e6", Material.BEDROCK),
            new Parameters<>(new DropperGame.Dropper2(), slot(4, 2), "223fa5119fe995ebd180da01a27c660a92421c887422dc1aa06e1869eee2523b", Material.BEDROCK),
            new Parameters<>(new DropperGame.Dropper3(), slot(5, 2), "e6c5e1a2288a706163ed98071e486bf7693f385a71d2eeb5324485fef08a21e2", Material.BEDROCK),
            new Parameters<>(new DropperGame.Dropper4(), slot(6, 2), "52ba0a93686bdef436fef5c1564aa0a09da7d6db057165f72390ea7cf2c1960e", Material.BEDROCK),
            new Parameters<>(new DropperGame.Dropper5(), slot(7, 2), "6968509976ca67e8952cd78ad3960ab86a1c156b26265c5a31f7b190c16cad10", Material.BEDROCK),
            new Parameters<>(new WaterDropGame(), slot(8, 2), "b543bbd90571b1e35aa039a5ebad65f41427c8b8871ddf767580f3a5b1022fbf", Material.BEDROCK)
    ));


    @CommandPermission("quino.gui.gamemenu")
    @CommandAlias("gamemenu")
    public static void open(Player player) {
        menuBuilder.createMenu(player, "          Menu de juegos", 9 * 5, false);
        menuBuilder.setContents(player, () -> {


            menuBuilder.setItem(player, slot(2, 5), new ItemBuilder(Material.STICK, "&eObjetos", "", "&7Haz clic para abrir menú.").build(), () -> {
                itemsMenu(player);
            });

            menuBuilder.setItem(player, slot(3, 5), new ItemBuilder(Material.APPLE, "&ePreferencias", "", "&7Haz clic para abrir menú.").build(), () -> {
                itemsMenu(player);
            });

            menuBuilder.setItem(player, slot(4, 5), new ItemBuilder(Material.PLAYER_HEAD, "&eJugadores", "", "&7Haz clic para abrir menú.").buildTexture("3ab2a950256613a3dcf3814acedd6ea788f8403b69d1ed7d3efc41c64cd045aa"), () -> {
                PlayersMenu.open(player, 0);
            });

            menuBuilder.setItem(player, slot(5, 5), new ItemBuilder(Material.CHAIN_COMMAND_BLOCK, "&eAcciones", "", "&7Haz clic para abrir menú.").build(), () -> {
                actionsMenu(player);
            });


            for (Parameters<Game, Integer, String, Material> parameters : games) {
                Game game = parameters.getP1();
                Integer slot = parameters.getP2();
                String texture = parameters.getP3();
                Material p4 = parameters.getP4();

                String name = game.getName();

                menuBuilder.setItem(player, slot, new ItemBuilder(Material.PLAYER_HEAD, "&e" + name, "", "&7Haz clic para manejar este nivel.")
                        .buildTexture(texture), () -> {
                    subMenu(player, game);
                });
            }
        });
    }

    private static void actionsMenu(Player player) {
        menuBuilder.createMenu(player, "          Acciones", 9 * 5, false);
        menuBuilder.setContents(player, () -> {

            menuBuilder.setItem(player, slot(2, 2), new ItemBuilder(Material.STICK, "&eRemover bloques transformados", "", "&7Haz clic para abrir menú.").build(), () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    DisguiseAPI.undisguiseToAll(p);
                }
                send(player, "&eRemoviste la transformación de todos los jugadores transformados.");
            });
            menuBuilder.setItem(player, slot(3, 2), new ItemBuilder(Material.COMPASS, "&eTeletransportar a todos los jugadores", "", "&7Haz clic para teletransportar a todos los jugadores.").build(), () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p != player) {
                        p.teleport(player.getLocation());
                    }
                }
                send(player, "&eTeletransportaste a todos los jugadores a tu ubicación.");
            });
            menuBuilder.setItem(player, slot(4, 2), new ItemBuilder(Material.BEDROCK, "&eSacar a todos los jugadores del servidor", "", "&7Haz clic para sacar a todos los jugadores del servidor.").build(), () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p != player) {
                        p.kickPlayer(format("&c¡Has sido expulsado del servidor!"));
                    }
                }
                send(player, "&eExpulsaste a todos los jugadores del servidor.");
            });
            menuBuilder.setItem(player, slot(5, 2), new ItemBuilder(Material.CLOCK, "&eCambiar la hora del día", "", "&7Haz clic para cambiar la hora del día.").build(), () -> {
                World world = player.getWorld();
                long time = world.getTime();

                // Cambiar la hora del día
                if (time >= 0 && time < 6000) {
                    world.setTime(6000); // Amanecer
                } else if (time >= 6000 && time < 12000) {
                    world.setTime(12000); // Mediodía
                } else if (time >= 12000 && time < 18000) {
                    world.setTime(18000); // Atardecer
                } else {
                    world.setTime(0); // Noche
                }

                send(player, "&eHora del día cambiada.");
            });
            menuBuilder.setItem(player, slot(6, 2), new ItemBuilder(Material.CHEST, "&eDar objetos a todos los jugadores", "", "&7Haz clic para dar objetos a todos los jugadores.").build(), () -> {

                ItemStack itemToGive = new ItemStack(Material.DIAMOND, 1); // Por ejemplo, dar un diamante
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.getInventory().addItem(itemToGive);
                }
                send(player, "&eHas dado objetos a todos los jugadores.");
            });
            menuBuilder.setItem(player, slot(7, 2), new ItemBuilder(Material.DIAMOND_SWORD, "&eActivar/desactivar PvP global", "", "&7Haz clic para activar o desactivar el PvP global.").build(), () -> {

                boolean pvpEnabled = !Game.GameListeners.isPvp();
                Game.GameListeners.setPvp(pvpEnabled);
                String statusMessage = pvpEnabled ? "&ePvP global activado." : "&ePvP global desactivado.";
                send(player, statusMessage);
            });
            menuBuilder.setItem(player, slot(8, 2), new ItemBuilder(Material.REDSTONE_BLOCK, "&eReiniciar el servidor", "", "&7Haz clic para reiniciar el servidor.").build(), () -> {
                Bukkit.getServer().shutdown();
            });
            menuBuilder.setItem(player, slot(2, 3), new ItemBuilder(Material.SPAWNER, "&eInvocar zombies", "", "&7Haz clic para invocar un jefe o enemigo especial.").build(), () -> {
                World world = player.getWorld();
                for (var i = 0; i < 15; i++) {
                    world.spawnEntity(player.getLocation().toCenterLocation(), EntityType.ZOMBIE);
                }
            });
            menuBuilder.setItem(player, slot(3, 3), new ItemBuilder(Material.ENCHANTING_TABLE, "&eEstablecer estado de juego", "", "&7Haz clic para cambiar el estado de juego.").build(), () -> {
                Core core = Core.getInstance();
                Stage currentStage = core.getStage();
                Stage[] stages = Stage.values();

                int currentIndex = -1;
                for (int i = 0; i < stages.length; i++) {
                    if (stages[i] == currentStage) {
                        currentIndex = i;
                        break;
                    }
                }

                if (currentIndex != -1) {
                    Stage nextStage = stages[(currentIndex + 1) % stages.length];
                    core.setStage(nextStage);
                    send(player, "&eAvanzado al siguiente stage: " + nextStage);
                } else {
                    send(player, "&cNo se pudo encontrar el stage actual.");
                }

            });
            menuBuilder.setItem(player, slot(4, 3), new ItemBuilder(Material.BOOK, "&eAnunciar un mensaje global", "", "&7Haz clic para enviar un mensaje global.").build(), () -> {
                send(player, "&eUtiliza: /bc [mensaje]");
            });
            menuBuilder.setItem(player, slot(5, 3), new ItemBuilder(Material.BUCKET, "&eLimpiar inventario de todos los jugadores", "", "&7Haz clic para limpiar los inventarios.").build(), () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.getInventory().clear();
                }
                send(player, "&aHas limpiado el inventario de todos los jugadores.");
            });

            menuBuilder.setItem(player, slot(5, 5), new ItemBuilder(Material.BARRIER, "&cVolver al menú",
                    "", "&7Haz clic para volver al menú.").build(), () -> {
                open(player);
            });
        });
    }

    private static void itemsMenu(Player player) {
        menuBuilder.createMenu(player, "          Menu de objetos", 9 * 4, false);
        menuBuilder.setContents(player, () -> {
            menuBuilder.setItem(player, slot(2, 2), new ItemBuilder(Material.STICK, "&9Palo kickeador",
                    "",
                    "&fExpulsa a los jugadores con un clic.",
                    "",
                    "&eHaz clic para obtener el item.").build(), () -> {
                GiveMenu.open(player, Kickstick.itemStack, 0);
            });
            menuBuilder.setItem(player, slot(3, 2), new ItemBuilder(Material.STICK, "&cPalo de gracia",
                    "",
                    "&fAsesina al objetivo con un clic.",
                    "",
                    "&eHaz clic para obtener el item.").build(), () -> {
                GiveMenu.open(player, Killstick.itemStack, 0);
            });

            menuBuilder.setItem(player, slot(4, 2), new ItemBuilder(Material.GLOW_BERRIES, "&eGlowing temporal",
                    "",
                    "&fIlumina a los jugadores durante",
                    "&f30 segundos al consumir.",
                    "",
                    "&eHaz clic para obtener el item.").build(), () -> {
                GiveMenu.open(player, GlowItem.itemStack, 0);

            });
            menuBuilder.setItem(player, slot(5, 2), new ItemBuilder(Material.BRICK, "&eCambiar bloque",
                    "",
                    "&fObten la pistola que al clickear",
                    "&fun bloque a una distancia maxima de",
                    "&f5 bloques te convertiras en el.",
                    "",
                    "&eHaz clic para obtener el item.").buildCustomModelData(1), () -> {
                GiveMenu.open(player, DisguiseItem.itemStack, 0);

            });

            menuBuilder.setItem(player, slot(6, 2), new ItemBuilder(Material.CLOCK, "&eCentrar en el bloque",
                    "",
                    "&fCentrate en el bloque donde",
                    "&fte encuentres.",
                    "",
                    "&eHaz clic para obtener el item.").buildCustomModelData(1), () -> {
                GiveMenu.open(player, CenterItem.itemStack, 0);
            });

            menuBuilder.setItem(player, slot(7, 2), new ItemBuilder(Material.PLAYER_HEAD, "&aMenu de juegos",
                    "",
                    "&fObten el item de menu de juegos.",
                    "",
                    "&eHaz clic para obtener el item.")
                    .buildTexture("dade199d5b3a8b8c6fe2eeb628af38daada305f7c90d1bd1224cf1f071fcbe8b"), () -> {
                GiveMenu.open(player, GameItem.itemStack, 0);

            });

            menuBuilder.setItem(player, slot(5, 4), new ItemBuilder(Material.BARRIER, "&cVolver al menú",
                    "", "&7Haz clic para volver al menú.").build(), () -> {
                open(player);
            });
        });
    }


    public static void subMenu(Player player, Game game) {
        menuBuilder.createMenu(player, "            " + game.getName(), 9 * 4, false);
        menuBuilder.setContents(player, () -> {

            menuBuilder.setItem(player, slot(4, 2), new ItemBuilder(Material.LIME_DYE, "&a[✔] Iniciar juego",
                    "", "&7Haz clic para iniciar este nivel.").build(), () -> {
                game.start();
                send(player, "&e&lEvento &8> &fHas iniciado el nivel &a" + game.getName() + "&f.");
                subMenu(player, game);
            });

            menuBuilder.setItem(player, slot(6, 4), new ItemBuilder(Material.ENDER_PEARL, "&e[☻] Teletransportar",
                    "", "&7Haz clic para teletransportar a este nivel.").build(), () -> {
                game.teleport();
                subMenu(player, game);
            });

            menuBuilder.setItem(player, slot(5, 2), new ItemBuilder(Material.ORANGE_DYE, "&6[⏸] Terminar",
                    "", "&7Haz clic para cancelar este nivel.").build(), () -> {
                game.stop();
                send(player, "&e&lEvento &8> &fHas terminado el nivel &c" + game.getName() + "&f.");
                subMenu(player, game);
            });

            menuBuilder.setItem(player, slot(6, 2), new ItemBuilder(Material.RED_DYE, "&c[❌] Cancelar",
                    "", "&7Haz clic para cancelar este nivel.").build(), () -> {
                game.cancel();
                send(player, "&e&lEvento &8> &fHas cancelado el nivel &c" + game.getName() + "&f.");
                subMenu(player, game);
            });

            menuBuilder.setItem(player, slot(5, 4), new ItemBuilder(Material.BARRIER, "&cVolver al menú",
                    "", "&7Haz clic para volver al menú.").build(), () -> {
                open(player);
            });
        });
    }
}