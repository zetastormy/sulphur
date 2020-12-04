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

import de.tr7zw.nbtapi.NBTItem;

public class SulphurCommand implements CommandExecutor {
    private final MessageUtils messageUtils;

    public SulphurCommand(final MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("sulphur.admin")) {
            messageUtils.sendMessage(sender, false, "&4&lError &8|| &7No tienes permisos para ejecutar este comando.");
            return true;
        }

        if (args.length > 0 && !args[0].equalsIgnoreCase("help")) {
            switch (args[0]) {
                case "getitem":
                NBTItem paloVomitivoNbt = new NBTItem(new ItemStack(Material.STICK));
                paloVomitivoNbt.setBoolean("PaloVomitivo", true);

                ItemStack paloVomitivoItem = paloVomitivoNbt.getItem();
                ItemMeta paloVomitivoMeta = paloVomitivoItem.getItemMeta();

                paloVomitivoMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2Palo Vomitivo"));
                paloVomitivoItem.setItemMeta(paloVomitivoMeta);
                
                ((Player) sender).getInventory().addItem(paloVomitivoItem);
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