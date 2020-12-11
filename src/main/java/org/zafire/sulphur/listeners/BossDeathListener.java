package org.zafire.sulphur.listeners;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;
import org.mineacademy.boss.api.BossAPI;
import org.mineacademy.boss.api.event.BossDeathEvent;
import org.zafire.sulphur.SulphurPlugin;
import org.zafire.sulphur.handlers.CacheHandler;
import org.zafire.sulphur.utils.MessageUtils;

public class BossDeathListener implements Listener {
    private final CacheHandler cacheHandler;
    private final BukkitScheduler bukkitScheduler;
    private final SulphurPlugin plugin;
    private final MessageUtils messageUtils;

    public BossDeathListener(final CacheHandler cacheHandler, final BukkitScheduler bukkitScheduler,
            final SulphurPlugin plugin, final MessageUtils messageUtils) {
        this.cacheHandler = cacheHandler;
        this.bukkitScheduler = bukkitScheduler;
        this.plugin = plugin;
        this.messageUtils = messageUtils;
    }

    @EventHandler
    public void onBossDeath(final BossDeathEvent event) {
        if (!event.getBoss().getName().equals(BossAPI.getBoss("WitherInfernal").getName())) {
            return;
        }

        Map<UUID, Double> damageMap = cacheHandler.getSortedDamageCacheMap();
        Map<UUID, Double> damagePercentageMap = cacheHandler.getPercentageDamageCacheMap();

        bukkitScheduler.runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                messageUtils.broadcastMessage("&6&lMazmorra &8|| &7El &cWither Infernal&7 ha muerto&8:");
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###.##");
                int currentEntry = 1;

                for (Map.Entry<UUID, Double> entry : damageMap.entrySet()) {
                    if (currentEntry >= 4)
                        break;
                    String playerName = plugin.getServer().getOfflinePlayer(entry.getKey()).getName();
                    Double percentage = damagePercentageMap.getOrDefault(entry.getKey(), null);
                    percentage = percentage == null ? -1.0D : percentage;

                    switch (currentEntry) {
                        case 1:
                            messageUtils.broadcastMessage(
                                    "&8[&6#1&8] &c" + playerName + "&8- &e" + decimalFormat.format(entry.getValue())
                                            + " &8(&e" + decimalFormat.format(percentage) + "%&8)");
                            break;
                        case 2:
                            messageUtils.broadcastMessage(
                                    "&8[&e#2&8] &c" + playerName + "&8- &e" + decimalFormat.format(entry.getValue())
                                            + " &8(&e" + decimalFormat.format(percentage) + "%&8)");
                            break;
                        case 3:
                            messageUtils.broadcastMessage(
                                    "&8[&a#3&8] &c" + playerName + "&8- &e" + decimalFormat.format(entry.getValue())
                                            + " &8(&e" + decimalFormat.format(percentage) + "%&8)");
                            break;
                        default:
                            plugin.getLogger().warning("Something really unexpected ocurred inside BossDeathListener!");
                            break;
                    }

                    currentEntry++;
                }

                cacheHandler.getDamageCacheMap().clear();
                damageMap.clear();
                damagePercentageMap.clear();
            }
        });
    }
}
