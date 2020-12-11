package org.zafire.sulphur.tasks;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossAPI;
import org.mineacademy.boss.api.BossSpawnReason;
import org.mineacademy.boss.api.SpawnedBoss;
import org.zafire.sulphur.SulphurPlugin;

public class BossSpawnTask {
  private final SulphurPlugin plugin;
  private final long ticks;
  private static long lastSpawnTime;
  public BukkitTask bukkitTask;

  public BossSpawnTask(final SulphurPlugin plugin, final long ticks) {
    this.plugin = plugin;
    this.ticks = ticks;
    runBossSpawnTask();
  }

  private void runBossSpawnTask() {
    new BukkitRunnable() {
      @Override
      public void run() {
        lastSpawnTime = System.currentTimeMillis();
        final Collection<SpawnedBoss> spawnedBosses = BossAPI.getBosses(plugin.getServer().getWorld("InfernalDungeon"));
        final Boss witherInfernalBoss = BossAPI.getBoss("WitherInfernal");
        final Boss guardianRealBoss = BossAPI.getBoss("GuardianReal");

        if (witherInfernalBoss != null) {
          boolean isBossSpawned = false;
          for (SpawnedBoss spawnedBoss : spawnedBosses) {
            if (spawnedBoss.getBoss().equals(witherInfernalBoss)) {
              isBossSpawned = true;
            }
          }

          if (!isBossSpawned) {
            Location spawnLocation = new Location(plugin.getServer().getWorld("InfernalDungeon"), -47, 51, 0);
            guardianRealBoss.spawn(spawnLocation, BossSpawnReason.CUSTOM);
            guardianRealBoss.spawn(spawnLocation, BossSpawnReason.CUSTOM);
            witherInfernalBoss.spawn(spawnLocation, BossSpawnReason.CUSTOM);
          }
        }
      }
    }.runTaskTimer(plugin, 0L, ticks);
  }

  public static String getSpawnTime() {
    String timeString = "Error";

    long timeLeftMills = System.currentTimeMillis() - lastSpawnTime;
    long timecountSecPass = timeLeftMills / 1000L;
    long timecountSec = (10800) - timecountSecPass;

    int days = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;

    if (timecountSec >= 86400L) {
      days = (int) (timecountSec / 86400L);
      timecountSec %= 86400L;
    }

    if (timecountSec >= 3600L) {
      hours = (int) (timecountSec / 3600L);
      timecountSec %= 3600L;
    }

    if (timecountSec >= 60L) {
      minutes = (int) (timecountSec / 60L);
      timecountSec %= 60L;
    }

    if (timecountSec > 0L) {
      seconds = (int) timecountSec;
    }

    if (days != 0) {
      timeString = days + "d " + hours + "h " + minutes + "m " + seconds + "s";
    } else if (days == 0 && hours == 0 && minutes == 0) {
      timeString = seconds + "s";
    } else if (days == 0 && hours == 0) {
      timeString = minutes + "m " + seconds + "s";
    } else {
      timeString = hours + "h " + minutes + "m " + seconds + "s";
    }
    
    return timeString;
  }
}