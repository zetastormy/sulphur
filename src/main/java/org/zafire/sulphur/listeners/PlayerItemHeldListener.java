package org.zafire.sulphur.listeners;

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
import dev._2lstudios.hamsterapi.hamsterplayer.HamsterPlayer;

public class PlayerItemHeldListener implements Listener {
    private final SulphurPlugin plugin;
    private final MessageUtils messageUtils;

    public PlayerItemHeldListener(final SulphurPlugin plugin, final MessageUtils messageUtils) {
        this.plugin = plugin;
        this.messageUtils = messageUtils;
    }

    @EventHandler
    public void onPlayerItemHeld(final PlayerItemHeldEvent event) {
        final HamsterAPI hamsterInstance = HamsterAPI.getInstance();

        new BukkitRunnable() {
            @Override
            public void run() {
                Player player = event.getPlayer();
                ItemStack handItem = player.getItemInHand();
                HamsterPlayer hamsterPlayer = hamsterInstance.getHamsterPlayerManager().get(player);

                if (handItem.getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                    hamsterPlayer.sendTitle(messageUtils.replaceManager("&c&lOh no", player),
                            "¡Ten cuidado con caerte!", 3, 5, 3);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.getItemInHand().getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                addEffect(PotionEffectType.CONFUSION, player, 100);
                            } else {
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 100);
                }

                if (handItem.getItemMeta().getDisplayName().equals("§6Palo Rapidín")) {
                    hamsterPlayer.sendTitle(messageUtils.replaceManager("&c&l¡Cuidado!", player),
                            "Podrías ir demasiado rápido.", 3, 5, 3);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.getItemInHand().getItemMeta().getDisplayName().equals("§6Palo Rapidín")) {
                                player.removePotionEffect(PotionEffectType.SPEED);
                                player.removePotionEffect(PotionEffectType.JUMP);
                                addEffect(PotionEffectType.SPEED, player, 50);
                                addEffect(PotionEffectType.JUMP, player, 30);
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

    private boolean addEffect(PotionEffectType effectType, Player player, int amplifier) {
        return player.addPotionEffect(new PotionEffect(effectType, 450, amplifier, true, false));
    }
}
