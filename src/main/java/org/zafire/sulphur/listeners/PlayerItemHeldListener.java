package org.zafire.sulphur.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.zafire.sulphur.SulphurPlugin;

import dev._2lstudios.hamsterapi.HamsterAPI;

public class PlayerItemHeldListener implements Listener {
    private final SulphurPlugin plugin;

    public PlayerItemHeldListener(final SulphurPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerItemHeld(final PlayerItemHeldEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = event.getPlayer();
                ItemStack handItem = player.getItemInHand();

                if (handItem.getType() == Material.STICK && handItem.getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 100, true, false));
                    HamsterAPI.getInstance().getHamsterPlayerManager().get(player).sendTitle("&c&lOh no", "¡Ten cuidado con caerte!", 3, 5, 3);
                }
            }
        }.runTaskLater(plugin, 20L);
    }
}
