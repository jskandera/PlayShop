package me.joff1507.pshop;

import me.joff1507.pshop.command.CommandHandler;
import me.joff1507.pshop.manager.ShopManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerShop extends JavaPlugin {
    private static PlayerShop instance;
    private ShopManager shopManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        shopManager = new ShopManager(this);
        getCommand("pshop").setExecutor(new CommandHandler(this));
        getLogger().info("PlayerShop plugin enabled.");
    }

    public static PlayerShop getInstance() {
        return instance;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }
}