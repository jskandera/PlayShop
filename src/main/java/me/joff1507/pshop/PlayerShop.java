package me.joff1507.pshop;

import me.joff1507.pshop.command.CommandHandler;
import me.joff1507.pshop.manager.*;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerShop extends JavaPlugin {
    private static PlayerShop instance;
    private ShopManager shopManager;
    private VaultManager vaultManager;
    private HologramManager hologramManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        shopManager = new ShopManager(this);
        vaultManager = new VaultManager(this);
        hologramManager = new HologramManager(this);
        getCommand("pshop").setExecutor(new CommandHandler(this));
        getLogger().info("PlayerShop plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerShop plugin disabled.");
    }

    public static PlayerShop getInstance() {
        return instance;
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
}
