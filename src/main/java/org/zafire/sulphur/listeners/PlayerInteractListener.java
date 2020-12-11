package org.zafire.sulphur.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zafire.sulphur.handlers.CacheHandler;

public class PlayerInteractListener implements Listener {
    private final CacheHandler cacheUtils;

    public PlayerInteractListener(final CacheHandler cacheUtils) {
        this.cacheUtils = cacheUtils;
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (cacheUtils.isConnectionCached(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}