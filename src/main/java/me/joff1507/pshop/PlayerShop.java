package me.joff1507.pshop;

import me.joff1507.pshop.command.CommandHandler;
import me.joff1507.pshop.manager.*;
import me.joff1507.pshop.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerShop extends JavaPlugin {

    private static PlayerShop instance;
    private ShopManager shopManager;
    private VaultManager vaultManager;
    private HologramManager hologramManager;
    private LogUtils logUtils;

    public static PlayerShop getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Load config
        saveDefaultConfig();
        logUtils = new LogUtils(this);
        logUtils.log("&aConfiguration loaded");

        // Setup dependencies
        if (!setupVault()) {
            logUtils.log("&cVault not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!WorldGuardUtils.init()) {
            logUtils.log("&cWorldGuard not found or incompatible! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize managers
        vaultManager = new VaultManager(this);
        shopManager = new ShopManager(this);
        hologramManager = new HologramManager(this);

        // Register command
        CommandHandler commandHandler = new CommandHandler(this);
        getCommand("pshop").setExecutor(commandHandler);
        getCommand("pshop").setTabCompleter(commandHandler);

        logUtils.log("&aPlayerShop enabled");
    }

    @Override
    public void onDisable() {
        if (shopManager != null) shopManager.saveShops();
        logUtils.log("&cPlayerShop disabled");
    }

    private boolean setupVault() {
        return Bukkit.getPluginManager().getPlugin("Vault") != null;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public LogUtils getLogUtils() {
        return logUtils;
    }

    @Override
    public FileConfiguration getConfig() {
        return super.getConfig();
    }
}
