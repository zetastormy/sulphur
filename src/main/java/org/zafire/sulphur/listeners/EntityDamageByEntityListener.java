package org.zafire.sulphur.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossAPI;
import org.zafire.sulphur.handlers.CacheHandler;

public class EntityDamageByEntityListener implements Listener {
    private final CacheHandler cacheHandler;

    public EntityDamageByEntityListener(final CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.getEntity() == null || !(event.getDamager() instanceof Player)) {
            return;
        }

        final Entity entityBoss = event.getEntity();
        final Boss witherInfernalBoss = BossAPI.getBoss("WitherInfernal");
        final Player player = (Player) event.getDamager();

        if (BossAPI.isBoss(entityBoss) && BossAPI.getBoss(entityBoss).getName().equals(witherInfernalBoss.getName())) {
            double currentDamage = cacheHandler.getDamageCacheMap().getOrDefault(player.getUniqueId(), 0.0);
            cacheHandler.getDamageCacheMap().put(player.getUniqueId(), currentDamage + event.getDamage());
        }
    }
}
