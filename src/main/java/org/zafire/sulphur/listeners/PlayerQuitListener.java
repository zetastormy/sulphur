package org.zafire.sulphur.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zafire.sulphur.handlers.CacheHandler;

public class PlayerQuitListener implements Listener {
    private final CacheHandler cacheUtils;

    public PlayerQuitListener(final CacheHandler cacheUtils) {
        this.cacheUtils = cacheUtils;
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (event.getPlayer() == null) {
            return;
        }

        if (cacheUtils.isConnectionCached(event.getPlayer().getUniqueId())) {
            cacheUtils.removeConnectionCache(event.getPlayer().getUniqueId());
        }
    }
}