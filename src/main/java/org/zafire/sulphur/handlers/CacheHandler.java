package org.zafire.sulphur.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

/** 
 * Handle the cache of the plugin. 
 */
public class CacheHandler {
    /**
     * The player connection cache is a {@link ArrayList} that is 
     * used to prevent object duplication when the user
     * is being sent to another server.
     */
    private List<UUID> playerConnectionCacheList = new ArrayList<UUID>();

    /**
     * It receives and {@link UUID} object to
     * add the player UUID to the cache.
     * @param uuid - UUID of the player.
     */
    public void addConnectionCache(UUID uuid) {
        playerConnectionCacheList.add(uuid);
    }

    /**
     * It receives and {@link UUID} object to
     * remove the player UUID from the cache.
     * @param uuid - UUID of the player.
     */
    public void removeConnectionCache(UUID uuid) {
        playerConnectionCacheList.remove(uuid);
    }

    /**
     * It receives and {@link UUID} object to
     * check if the player UUID is in the cache.
     * @param uuid - UUID of the player.
     * @return True, if the player is in cache and false, if the player isn't in the cache.
     */
    public boolean isConnectionCached(UUID uuid) {
        if (playerConnectionCacheList.contains(uuid)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The boss damage cache is a {@link HashMap} that is
     * used to save the damage that a user dealt to a boss
     * in the {@link EntityDamageByEntityEvent}.
     */
    private Map<UUID, Double> bossDamageCacheMap = new HashMap<UUID, Double>();

    /**
     * Get the damage cache map.
     * @return The damage cache map with {@link UUID} as key and {@link Double} as value.
     */
    public Map<UUID, Double> getDamageCacheMap() {
        return bossDamageCacheMap;
    }

    /**
     * Get a sorted damage cache map.
     * @return A sorted map of the damage cache.
     */
    public Map<UUID, Double> getSortedDamageCacheMap() {
        Map<UUID, Double> sortedDamageCacheMap;

        sortedDamageCacheMap = bossDamageCacheMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        return sortedDamageCacheMap;
    }

    /**
     * Get a percentage map of the damage cache.
     * @return A percentage map of the damage cache.
     */
    public Map<UUID, Double> getPercentageDamageCacheMap() {
        Map<UUID, Double> percentageMap = new HashMap<>();
        double totalDamage = 0.0;

        for (Double damage : bossDamageCacheMap.values()) {
            if (damage != null) totalDamage += damage;
        }

        double onePercent = totalDamage / 100;

        bossDamageCacheMap.forEach((uuid, damage) -> {
            if (uuid == null || damage == null) return;

            double playerPercent = damage / onePercent;

            percentageMap.put(uuid, playerPercent);
        });

        return percentageMap;
    }


    /**
     * The player experience cache is a {@link HashMap} that is
     * used to save the experience that a has in {@link PlayerDeathEvent}.
     */
    private Map<UUID, Integer> playerExperienceCacheMap = new HashMap<UUID, Integer>();

    /**
     * Get the experience cache map object.
     * @return A {@link HashMap} with the experience cache.
     */
    public Map<UUID, Integer> getPlayerExperienceCacheMap() {
        return playerExperienceCacheMap;
    }
}
