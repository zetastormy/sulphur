package org.zafire.sulphur.tasks;

import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.sulphur.SulphurPlugin;
import org.zafire.sulphur.utils.MessageUtils;

public class AnnounceTask {
    private final SulphurPlugin plugin;
    private final BukkitScheduler bukkitScheduler;
    private final long ticks;
    private final MessageUtils messageUtils;
    public int taskId;

    public AnnounceTask(final SulphurPlugin plugin, final MessageUtils messageUtils,
            final BukkitScheduler bukkitScheduler, final long ticks) {
        this.plugin = plugin;
        this.messageUtils = messageUtils;
        this.bukkitScheduler = bukkitScheduler;
        this.ticks = ticks;
        runAnnounceTask();
    }

    public void runAnnounceTask() {
        taskId = bukkitScheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                final Random random = new Random();
                int randomNumber = random.nextInt(11);

                switch (randomNumber) {
                    case 0:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lAnuncio &8|| &7Recuerda apoyarnos en la tienda del servidor&8: &6store.zafire.org");
                        break;
                    case 1:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lAnuncio &8|| &7No te olvides de seguirnos en Twitter&8: &6twitter.zafire.org");
                        break;
                    case 2:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lAnuncio &8|| &7??nete a nuestra comunidad de Discord&8: &6discord.zafire.org");
                        break;
                    case 3:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lConsejo &8|| &7No olvides guardar tu dinero en el banco usando el comando &6/bank&7.");
                        break;
                    case 4:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lConsejo &8|| &7Recuerda leerlas nuestras normas y respetarlas&8: &6rules.zafire.org");
                        break;
                    case 5:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lVotos &8|| &7Recuerda votar por el servidor a diario usando el comando &6/vote&7.");
                        break;
                    case 6:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lConsejo &8|| &7No olvides reclamar tu recompensa diaria usando &6/daily&7.");
                        break;
                    case 7:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lConsejo &8|| &7En la mazmorra no pierdes tu inventario, por lo que puedes obtener las cosas que quieras sin perder nada.");
                        break;
                    case 8:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lConsejo &8|| &7Usa el comando &6/mena &7para modificar tu protecci??n a tu gusto de una manera m??s interactiva.");
                        break;
                    case 9:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lConsejo &8|| &7Puedes obtener m??s minerales f??cilmente en la mina p??blica, puedes ir con el comando &a/warp Mina&7.");
                        break;
                    case 10:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lConsejo &8|| &7En la casa de subastas puedes vender tus objetos y que todos el mundo los vea, con el comando &a/ah&7.");
                        break;                        
                    default:
                        for (Player p : plugin.getServer().getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 0);
                        }
                        messageUtils.broadcastMessage(
                                "&6&lConsejo &8|| &7Puedes ver m??s informaci??n del servidor usando el comando &6/help&7.");
                        break;
                }
            }
        }, 3000L, ticks);
    }
}
