package org.zafire.sulphur.commands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.zafire.sulphur.SulphurPlugin;
import org.zafire.sulphur.utils.MessageUtils;
import org.zafire.sulphur.handlers.CacheHandler;

public class LobbyCommand implements CommandExecutor {
    private final CacheHandler cacheUtils;
    private final SulphurPlugin plugin;
    private final MessageUtils messageUtils;

    public LobbyCommand(final CacheHandler cacheUtils, final SulphurPlugin plugin, final MessageUtils messageUtils) {
        this.cacheUtils = cacheUtils;
        this.plugin = plugin;
        this.messageUtils = messageUtils;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            cacheUtils.addConnectionCache(player.getUniqueId());

            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                public void run() {
                    cacheUtils.removeConnectionCache(player.getUniqueId());
                }
            }, 4000L);

            player.saveData();
            messageUtils.sendMessage(player, true, "Teletransportándote al &6Lobby&7, por favor, espera.");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeUTF("Connect");
                dataOutputStream.writeUTF("Lobby");
            } catch (IOException e) {
                plugin.getLogger().severe("Could not send " + player.getDisplayName() + " to the Lobby server!");
            }

            player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
            return true;
        }
        return false;
    }
    
}
