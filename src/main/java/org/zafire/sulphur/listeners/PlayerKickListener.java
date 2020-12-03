package org.zafire.sulphur.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent event) {
        if (event.getReason().equalsIgnoreCase("disconnect.spam")) {
            event.setCancelled(true);
        }
    }
}
