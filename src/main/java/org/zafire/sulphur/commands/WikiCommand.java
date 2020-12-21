package org.zafire.sulphur.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zafire.sulphur.utils.MessageUtils;

public class WikiCommand implements CommandExecutor {
    private final MessageUtils messageUtils;

    public WikiCommand(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        if (sender instanceof Player) {
            messageUtils.sendMessage(sender, true, "Puedes acceder a nuestra wiki desde&8: &6wiki.zafire.org");
            return true;
        }
        return true;
    }
}