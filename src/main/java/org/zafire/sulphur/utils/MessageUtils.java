package org.zafire.sulphur.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

/**
 * Utils for the messages of the plugin.
 */
public class MessageUtils {

    private String replaceColors(final String string) {
       return ChatColor.translateAlternateColorCodes('&', string);
    }

    public String replaceManager(final String string, final Player player) {
        String stringReplacement;
        stringReplacement = PlaceholderAPI.setPlaceholders(player, string);
        stringReplacement = replaceColors(stringReplacement);
        
        return stringReplacement;
    }

    public void sendMessage(final CommandSender sender, final boolean prefix, final String message) {
        final String prefixPath = "&5&lZafire &8|| &7";

        if (prefix) {
            sender.sendMessage(replaceManager(prefixPath + message, (Player) sender));
        } else {
            sender.sendMessage(replaceManager(message, (Player) sender));
        }
    }

    public void sendMessage(final Player player, final boolean prefix, final String message) {
        final String prefixPath = "&5&lZafire &8|| &7";

        if (prefix) {
            player.sendMessage(replaceManager(prefixPath + message, player));
        } else {
            player.sendMessage(replaceManager(message, player));
        }
    }    

    public void broadcastMessage(final String message) {
        Bukkit.broadcastMessage(replaceColors(message));
    }
}
