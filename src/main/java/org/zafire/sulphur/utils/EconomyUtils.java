package org.zafire.sulphur.utils;

import java.text.DecimalFormat;
import java.util.Random;

import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;

/**
 * Utils for the economy management of the plugin.
 */
public class EconomyUtils {
    private final Economy economy;

    public EconomyUtils(Economy economy) {
        this.economy = economy;
    }

    public double getRandom(final String path) {
        if (path == null) {
            return 0.0;
        }

        if (path.contains("-")) {
            final Random rand = new Random();
            final String[] parts = path.split("-");
            final double min = Double.parseDouble(parts[0]);
            final double max = Double.parseDouble(parts[1]);
            return rand.nextDouble() * (max - min) + min;
        }
        
        return Double.valueOf(path);
    }

    public double getMoneyLost(String moneyPercentage, Player player) {
        DecimalFormat moneyFormat = new DecimalFormat("#.##");
        double percentageLostPvp = getRandom(moneyPercentage) / 100.0;

        return Double.valueOf(moneyFormat.format(Math.abs(economy.getBalance(player) * percentageLostPvp)));
    }
}
