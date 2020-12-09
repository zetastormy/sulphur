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
import org.zafire.sulphur.utils.MessageUtils;

import dev._2lstudios.hamsterapi.HamsterAPI;

public class PlayerItemHeldListener implements Listener {
    private final SulphurPlugin plugin;
    private final MessageUtils messageUtils;

    public PlayerItemHeldListener(final SulphurPlugin plugin, final MessageUtils messageUtils) {
        this.plugin = plugin;
        this.messageUtils = messageUtils;
    }

    @EventHandler
    public void onPlayerItemHeld(final PlayerItemHeldEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = event.getPlayer();
                ItemStack handItem = player.getItemInHand();

                if (handItem.getType() == Material.STICK && handItem.getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                    HamsterAPI.getInstance().getHamsterPlayerManager().get(player).sendTitle(messageUtils.replaceManager("&c&lOh no", player), "¡Ten cuidado con caerte!", 3, 5, 3);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.getItemInHand().getType() == Material.STICK && player.getItemInHand().getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 100, true, false));
                            } else {
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 100);
                }
            }
        }.runTaskLater(plugin, 1L);
    }
}
