package org.zafire.sulphur.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.zafire.sulphur.handlers.CacheHandler;

public class PlayerDropItemListener implements Listener {
    private final CacheHandler cacheUtils;

    public PlayerDropItemListener(final CacheHandler cacheUtils) {
        this.cacheUtils = cacheUtils;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDropItem(final PlayerDropItemEvent event) {
        if (cacheUtils.isConnectionCached(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
        }
    }
}