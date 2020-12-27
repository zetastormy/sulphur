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
        final Player player = event.getPlayer();
        final HamsterPlayer hamsterPlayer = hamsterInstance.getHamsterPlayerManager().get(player);
        final ItemStack handItem = player.getItemInHand();

        if (handItem == null || !handItem.hasItemMeta() || !handItem.getItemMeta().hasDisplayName()) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {

                if (handItem.getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                    hamsterPlayer.sendTitle(messageUtils.replaceManager("&c&lOh no", player),
                            "¡Ten cuidado con caerte!", 3, 5, 3);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!player.isOnline() || !player.getItemInHand().hasItemMeta()) {
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                cancel();
                                return;
                            }

                            if (player.getItemInHand().getItemMeta().getDisplayName().equals("§2Palo Vomitivo")) {
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                addEffect(PotionEffectType.CONFUSION, player, 100);
                            } else {
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                cancel();
                                return;
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 100);
                }

                if (handItem.getItemMeta().getDisplayName().equals("§6Palo Rapidín")) {
                    if (!player.getName().equals("Logan_BR11")) {
                        player.setHealth(0);
                        messageUtils.sendMessage(player, false,
                                "&4&lError &8|| &7Tu cuerpo no es capaz de soportar tal poder, únicamente &cLogan_BR11 &7puede soportarlo.");
                    }

                    hamsterPlayer.sendTitle(messageUtils.replaceManager("&6&l¡Cuidado!", player),
                            "Podrías ir demasiado rápido.", 3, 5, 3);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!player.isOnline() || !player.getItemInHand().hasItemMeta()) {
                                player.removePotionEffect(PotionEffectType.CONFUSION);
                                cancel();
                                return;
                            }

                            if (player.getItemInHand().getItemMeta().getDisplayName().equals("§6Palo Rapidín")) {
                                player.removePotionEffect(PotionEffectType.SPEED);
                                player.removePotionEffect(PotionEffectType.JUMP);
                                addEffect(PotionEffectType.SPEED, player, 50);
                                addEffect(PotionEffectType.JUMP, player, 30);
                            } else {
                                player.removePotionEffect(PotionEffectType.SPEED);
                                player.removePotionEffect(PotionEffectType.JUMP);
                                cancel();
                                return;
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
