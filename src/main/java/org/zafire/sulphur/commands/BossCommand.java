package org.zafire.sulphur.commands;

import java.util.Collection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossAPI;
import org.mineacademy.boss.api.SpawnedBoss;
import org.zafire.sulphur.SulphurPlugin;
import org.zafire.sulphur.tasks.BossSpawnTask;
import org.zafire.sulphur.utils.MessageUtils;

public class BossCommand implements CommandExecutor {
    private final MessageUtils messageUtils;
    private final SulphurPlugin plugin;

    public BossCommand(final MessageUtils messageUtils, final SulphurPlugin plugin) {
        this.messageUtils = messageUtils;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
            final String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0 && args[0].equalsIgnoreCase("time")) {
                final Collection<SpawnedBoss> spawnedBosses = BossAPI
                        .getBosses(plugin.getServer().getWorld("InfernalDungeon"));
                final Boss witherInfernalBoss = BossAPI.getBoss("WitherInfernal");
                boolean isBossSpawned = false;

                for (SpawnedBoss spawnedBoss : spawnedBosses) {
                    if (spawnedBoss.getBoss().equals(witherInfernalBoss)) {
                        isBossSpawned = true;
                    }
                }

                if (isBossSpawned) {
                    messageUtils.sendMessage(sender, false,
                            "&4&lError &8|| &7El &cWither Infernal &7ya apareció en la mazmorra.");
                    return true;
                } else {
                    messageUtils.sendMessage(sender, false,
                            "&2&lSurvival &8|| &7El &cWither Infernal &7aparecerá en &8[&a"
                                    + BossSpawnTask.getSpawnTime() + "&8]&7.");
                    return true;
                }
            } else {
                messageUtils.sendMessage(sender, false, "&4&lError &8|| &7Uso correcto&8: &a/boss time");
                return true;
            }
        }
        return false;
    }

}
