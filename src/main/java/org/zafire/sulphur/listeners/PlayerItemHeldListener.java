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

                if (handItem.getType() == Material.STICK
                        && handItem.getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                    HamsterAPI.getInstance().getHamsterPlayerManager().get(player).sendTitle(
                            messageUtils.replaceManager("&c&lOh no", player), "¡Ten cuidado con caerte!", 3, 5, 3);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.getItemInHand().getType() == Material.STICK && player.getItemInHand()
                                    .getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                player.addPotionEffect(
                                        new PotionEffect(PotionEffectType.CONFUSION, 450, 100, true, false));
                            } else {
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 100);
                }

                if (handItem.getType() == Material.STICK
                        && handItem.getItemMeta().getDisplayName().equals("§6Palo Rapidín")) {
                    HamsterAPI.getInstance().getHamsterPlayerManager().get(player).sendTitle(
                            messageUtils.replaceManager("&c&l¡Cuidado!", player), "Podrías ir demasiado rápido.", 3, 5,
                            3);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.getItemInHand().getType() == Material.STICK
                                    && player.getItemInHand().getItemMeta().getDisplayName().equals("§6Palo Rapidín")) {
                                player.removePotionEffect(PotionEffectType.SPEED);
                                player.removePotionEffect(PotionEffectType.JUMP);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 450, 100, true, false));
                                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 450, 100, true, false));
                            } else {
                                player.removePotionEffect(PotionEffectType.SPEED);
                                player.removePotionEffect(PotionEffectType.JUMP);
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 100);
                }
            }
        }.runTaskLater(plugin, 1L);
    }
}
