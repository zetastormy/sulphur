package org.zafire.sulphur;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.zafire.sulphur.commands.BossCommand;
import org.zafire.sulphur.commands.DiscordCommand;
import org.zafire.sulphur.commands.LobbyCommand;
import org.zafire.sulphur.commands.StoreCommand;
import org.zafire.sulphur.commands.SulphurCommand;
import org.zafire.sulphur.handlers.CacheHandler;
import org.zafire.sulphur.listeners.BossDeathListener;
import org.zafire.sulphur.listeners.BossPostSpawnListener;
import org.zafire.sulphur.listeners.EntityDamageByEntityListener;
import org.zafire.sulphur.listeners.InventoryClickListener;
import org.zafire.sulphur.listeners.PlayerDeathListener;
import org.zafire.sulphur.listeners.PlayerDropItemListener;
import org.zafire.sulphur.listeners.PlayerInteractListener;
import org.zafire.sulphur.listeners.PlayerItemHeldListener;
import org.zafire.sulphur.listeners.PlayerJoinListener;
import org.zafire.sulphur.listeners.PlayerKickListener;
import org.zafire.sulphur.listeners.PlayerQuitListener;
import org.zafire.sulphur.listeners.PlayerRespawnListener;
import org.zafire.sulphur.tasks.AnnounceTask;
import org.zafire.sulphur.tasks.BossSpawnTask;
import org.zafire.sulphur.utils.EconomyUtils;
import org.zafire.sulphur.utils.MessageUtils;

import net.milkbowl.vault.economy.Economy;

/**
 * Represents the main class of the plugin.
 * 
 * @author ZetaStormy
 */
public class SulphurPlugin extends JavaPlugin {
    private MessageUtils messageUtils;
    private PluginManager pluginManager;
    private BukkitScheduler bukkitScheduler;
    private EconomyUtils economyUtils;
    private CacheHandler cacheHandler;
    private Economy economy;
    private Server server;
    private SulphurPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        server = getServer();
        pluginManager = server.getPluginManager();
        bukkitScheduler = server.getScheduler();
        registerHooks();
        cacheHandler = new CacheHandler();
        messageUtils = new MessageUtils();
        economyUtils = new EconomyUtils(economy);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        registerListeners();
        registerCommands();
        scheduleTasks();
    }

    @Override
    public void onDisable() {
        bukkitScheduler.cancelTasks(plugin);
        plugin = null;
    }

    private void registerListeners() {
        if (plugin != null) {
            pluginManager.registerEvents(new BossDeathListener(cacheHandler, bukkitScheduler, plugin, messageUtils),
                    plugin);
            pluginManager.registerEvents(new BossPostSpawnListener(messageUtils), plugin);
            pluginManager.registerEvents(new EntityDamageByEntityListener(cacheHandler), plugin);
            pluginManager.registerEvents(new InventoryClickListener(cacheHandler), plugin);
            pluginManager.registerEvents(new PlayerDeathListener(messageUtils, economyUtils, economy, cacheHandler),
                    plugin);
            pluginManager.registerEvents(new PlayerDropItemListener(cacheHandler), plugin);
            pluginManager.registerEvents(new PlayerInteractListener(cacheHandler), plugin);
            pluginManager.registerEvents(new PlayerItemHeldListener(plugin, messageUtils), plugin);
            pluginManager.registerEvents(new PlayerJoinListener(messageUtils, cacheHandler), plugin);
            pluginManager.registerEvents(new PlayerKickListener(), plugin);
            pluginManager.registerEvents(new PlayerQuitListener(cacheHandler), plugin);
            pluginManager.registerEvents(new PlayerRespawnListener(cacheHandler, plugin), plugin);
        }
    }

    private void registerCommands() {
        if (plugin != null) {
            getCommand("store").setExecutor(new StoreCommand(messageUtils));
            getCommand("lobby").setExecutor(new LobbyCommand(cacheHandler, plugin, messageUtils));
            getCommand("discord").setExecutor(new DiscordCommand(messageUtils));
            getCommand("boss").setExecutor(new BossCommand(messageUtils, plugin));
            getCommand("sulphur").setExecutor(new SulphurCommand(messageUtils));
        }
    }

    private void registerHooks() {
        if (plugin != null) {
            if (pluginManager.getPlugin("PlaceholderAPI") == null) {
                getLogger().severe("Could not find PlaceholderAPI, the plugin will disable!");
                pluginManager.disablePlugin(plugin);
                return;
            }

            if (pluginManager.getPlugin("Vault") == null) {
                getLogger().severe("Could not find Vault, the plugin will disable!");
                pluginManager.disablePlugin(plugin);
                return;
            } else {
                RegisteredServiceProvider<Economy> registeredServiceProvider = server.getServicesManager()
                        .getRegistration(Economy.class);
                if (registeredServiceProvider == null) {
                    getLogger().severe("Could not register Economy service provider, the plugin will disable!");
                } else {
                    economy = registeredServiceProvider.getProvider();
                }
            }

            if (pluginManager.getPlugin("Boss") == null) {
                getLogger().severe("Could not find Boss, the plugin will disable!");
                pluginManager.disablePlugin(plugin);
                return;
            }

            if (pluginManager.getPlugin("NBTAPI") == null) {
                getLogger().severe("Could not find NBTAPI, the plugin will disable!");
                pluginManager.disablePlugin(plugin);
                return;
            }
        }
    }

    private void scheduleTasks() {
        if (plugin != null) {
            new BossSpawnTask(plugin, 216000L);
            new AnnounceTask(plugin, messageUtils, bukkitScheduler, 6000L);
        }
    }
}
