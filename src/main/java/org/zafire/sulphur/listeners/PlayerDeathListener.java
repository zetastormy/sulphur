package org.zafire.sulphur.listeners;

import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.zafire.sulphur.handlers.CacheHandler;
import org.zafire.sulphur.utils.EconomyUtils;
import org.zafire.sulphur.utils.MessageUtils;

import net.milkbowl.vault.economy.Economy;

public class PlayerDeathListener implements Listener {
    private final Economy economy;
    private final MessageUtils messageUtils;
    private final EconomyUtils economyUtils;
    private final CacheHandler cacheHandler;

    public PlayerDeathListener(final MessageUtils messageUtils, final EconomyUtils economyUtils, final Economy economy,
            final CacheHandler cacheHandler) {
        this.economy = economy;
        this.messageUtils = messageUtils;
        this.economyUtils = economyUtils;
        this.cacheHandler = cacheHandler;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        takeExperience(event, player);
        takeMoney(event, player);
    }

    private void takeExperience(PlayerDeathEvent event, Player player) {
        Map<UUID, Integer> playerExperienceCache = cacheHandler.getPlayerExperienceCacheMap();

        if (player.hasPermission("sulphur.zafire")) {
            event.setDroppedExp(0);
            playerExperienceCache.put(player.getUniqueId(), (int) Math.floor(player.getTotalExperience() * 0.75));
        }

        if (player.hasPermission("sulphur.arcane")) {
            event.setDroppedExp(0);
            playerExperienceCache.put(player.getUniqueId(), (int) Math.floor(player.getTotalExperience() * 0.50));
        }

        if (player.hasPermission("sulphur.sparkle")) {
            event.setDroppedExp(0);
            playerExperienceCache.put(player.getUniqueId(), (int) Math.floor(player.getTotalExperience() * 0.35));
        }
    }

    private void takeMoney(PlayerDeathEvent event, Player player) {
        if (player.getKiller() != null && player.getKiller() != player && player.getKiller() instanceof Player) {
            double totalLostPvp = economyUtils.getMoneyLost("50", player);
            String moneyFormatted = economy.format(totalLostPvp);

            if (player.hasPermission("sulphur.deathtax.bypass") && totalLostPvp != 0.0) {
                messageUtils.sendMessage(player, false,
                        "&2&lSurvival &8|| &7Te encuentras exento del impuesto por muerte.");
                messageUtils.sendMessage(player.getKiller(), false, "&2&lSurvival &8|| &c" + player.getDisplayName()
                        + " &7se encuentra exento del impuesto por muerte");
                return;
            }

            if (economy.getBalance(player) <= 0.0) {
                messageUtils.sendMessage(player, false,
                        "&4&lCombate &8|| &7No se te ha cobrado el impuesto por muerte, ya que no tienes suficiente dinero.");
                return;
            }

            if (totalLostPvp != 0.0) {
                messageUtils.sendMessage(player, false,
                        "&4&lCombate &8|| &7Se te ha cobrado &a" + moneyFormatted + " &7por morir.");
                economy.withdrawPlayer(player, totalLostPvp);
            }
        }

        if (player.getKiller() == event.getEntity() || !(player.getKiller() instanceof Player)
                || player.getKiller() == null) {
            double totalLostPve = economyUtils.getMoneyLost("35", player);
            String moneyFormatted = economy.format(totalLostPve);

            if (player.hasPermission("sulphur.deathtax.bypass")) {
                messageUtils.sendMessage(player, false,
                        "&2&lSurvival &8|| &7Te encuentras exento del impuesto por muerte.");
                return;
            }

            if (economy.getBalance(player) <= 0.0) {
                messageUtils.sendMessage(player, false,
                        "&2&lSurvival &8|| &7No se te ha cobrado el impuesto por muerte, ya que no tienes suficiente dinero.");
                return;
            }

            if (totalLostPve != 0.0) {
                messageUtils.sendMessage(player, false,
                        "&2&lSurvival &8|| &7Se te ha cobrado &a" + moneyFormatted + " &7por morir.");
                economy.withdrawPlayer(player, totalLostPve);
            }
        }
    }
}
