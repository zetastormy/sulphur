package org.zafire.sulphur.listeners;

import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.zafire.sulphur.SulphurPlugin;
import org.zafire.sulphur.handlers.CacheHandler;

public class PlayerRespawnListener implements Listener {
    private final CacheHandler cacheHandler;
    private final SulphurPlugin plugin;

    public PlayerRespawnListener(final CacheHandler cacheHandler, final SulphurPlugin plugin) {
        this.cacheHandler = cacheHandler;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        Map<UUID, Integer> playerExperienceCache = cacheHandler.getPlayerExperienceCacheMap();
        Player player = event.getPlayer();

        if (playerExperienceCache.containsKey(player.getUniqueId())) {
            new BukkitRunnable(){
                @Override
                public void run() {
                    if (player != null) {
                        player.giveExp(playerExperienceCache.get(player.getUniqueId()));
                        playerExperienceCache.remove(player.getUniqueId());
                    }
                }
            }.runTaskLaterAsynchronously(plugin, 1L);
        }
    }
}
