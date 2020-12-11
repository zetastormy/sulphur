package org.zafire.sulphur.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.zafire.sulphur.handlers.CacheHandler;

public class InventoryClickListener implements Listener {
    private final CacheHandler cacheUtils;

    public InventoryClickListener(final CacheHandler cacheUtils) {
        this.cacheUtils = cacheUtils;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (cacheUtils.isConnectionCached(event.getWhoClicked().getUniqueId())){
            event.setCancelled(true);
        }
    }
}