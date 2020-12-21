package org.zafire.sulphur.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.zafire.sulphur.utils.MessageUtils;

public class LinksCommand implements CommandExecutor {
    private final MessageUtils messageUtils;

    public LinksCommand(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        if (sender instanceof Player) {
            messageUtils.sendMessage(sender, true, "Lista de enlaces rápidos&8:");
            messageUtils.sendMessage(sender, false, "&8» &7Wiki&8: &6wiki.zafire.org");
            messageUtils.sendMessage(sender, false, "&8» &7Tienda&8: &6store.zafire.org");
            messageUtils.sendMessage(sender, false, "&8» &7Normas&8: &6rules.zafire.org");
            messageUtils.sendMessage(sender, false, "&8» &7Twitter&8: &6twitter.zafire.org");
            messageUtils.sendMessage(sender, false, "&8» &7Discord&8: &6discord.zafire.org");                                                
            return true;
        }
        return true;
    }
}