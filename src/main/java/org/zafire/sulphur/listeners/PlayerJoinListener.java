package org.zafire.sulphur.listeners;

import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.zafire.sulphur.handlers.CacheHandler;
import org.zafire.sulphur.utils.MessageUtils;

public class PlayerJoinListener implements Listener {
    private final MessageUtils messageUtils;
    private final CacheHandler cacheHandler;

    public PlayerJoinListener(final MessageUtils messageUtils, final CacheHandler cacheHandler) {
        this.messageUtils = messageUtils;
        this.cacheHandler = cacheHandler;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Map<UUID, Integer> playerExperienceCache = cacheHandler.getPlayerExperienceCacheMap();
        if (playerExperienceCache.containsKey(player.getUniqueId())) {
            player.giveExp(playerExperienceCache.get(player.getUniqueId()));
            playerExperienceCache.remove(player.getUniqueId());
        }

        if (player == null || (player.hasPlayedBefore() && !(player.hasPermission("sulphur.donator"))) || player.hasPermission("sulphur.staff")) {
            event.setJoinMessage(null);
            return;
        }

        if (!player.hasPlayedBefore() && !player.hasPermission("sulphur.donator")) {
            event.setJoinMessage(messageUtils.replaceManager("&5&lZafire &8|| &7El usuario &6" + player.getDisplayName() + " &7ha entrado por primera vez al servidor &8(&6#%server_unique_joins%&8)&7.", player));
        }

        if (player.hasPermission("sulphur.donator") && player.hasPlayedBefore()) {
            event.setJoinMessage(messageUtils.replaceManager("&5&lZafire &8|| &7El usuario &6" + player.getDisplayName() + " &7ha entrado al servidor.", player));
        }
    }
}
