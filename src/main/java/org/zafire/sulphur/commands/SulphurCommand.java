package org.zafire.sulphur.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zafire.sulphur.utils.MessageUtils;

public class SulphurCommand implements CommandExecutor {
    private final MessageUtils messageUtils;

    public SulphurCommand(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("sulphur.admin")) {
            messageUtils.sendMessage(sender, false, "&4&lError &8|| &7No tienes permisos para ejecutar este comando.");
            return true;
        }

        if (args.length > 0 && !args[0].equalsIgnoreCase("help")) {
            switch (args[0]) {
                case "getitem":
                    ItemStack paloVomitivoItem = new ItemStack(Material.STICK);
                    ItemMeta paloVomitivoMeta = paloVomitivoItem.getItemMeta();

                    paloVomitivoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2Palo Vomitivo"));
                    paloVomitivoItem.setItemMeta(paloVomitivoMeta);

                    ((Player) sender).getInventory().addItem(paloVomitivoItem);
                    return true;

                case "logan":
                    ItemStack paloRapidinItem = new ItemStack(Material.BLAZE_ROD);
                    ItemMeta paloRapidinMeta = paloRapidinItem.getItemMeta();

                    paloRapidinMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Palo Rapidín"));
                    paloRapidinItem.setItemMeta(paloRapidinMeta);

                    ((Player) sender).getInventory().addItem(paloRapidinItem);
                    return true;                    

                default:
                    helpExecution(sender);
                    return true;
            }
        } else {
            helpExecution(sender);
            return true;
        }
    }

    private void helpExecution(CommandSender sender) {
        messageUtils.sendMessage(sender, true, "Mostrando ayuda de &aSulphur&8:");
        messageUtils.sendMessage(sender, false, "&8» &a/sulphur getitem &8- &7Te da el palo vomitivo.");
    }
}