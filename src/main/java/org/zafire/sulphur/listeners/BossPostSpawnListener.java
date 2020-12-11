package org.zafire.sulphur.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.mineacademy.boss.api.BossSpawnReason;
import org.mineacademy.boss.api.event.BossPostSpawnEvent;
import org.zafire.sulphur.utils.MessageUtils;

public class BossPostSpawnListener implements Listener {
    private final MessageUtils messageUtils;

    public BossPostSpawnListener(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @EventHandler
    public void onBossSpawn(final BossPostSpawnEvent event) {
        final BossSpawnReason bossSpawnReason = event.getSpawnReason();
        final String bossName = event.getBoss().getName();

        if (bossSpawnReason == BossSpawnReason.CUSTOM && bossName.equalsIgnoreCase("WitherInfernal")) {
            messageUtils.broadcastMessage("&6&lMazmorra &8|| &7¡Ha a aparecido el &cWither Infernal &7en la cueva de la mazmorra, sé el primero en matarlo para obtener recompensas!");
        }
    }
}
