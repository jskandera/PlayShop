package me.joff1507.pshop.manager;

import me.joff1507.pshop.PlayerShop;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultManager {

    private static Economy economy;

    public static boolean setupEconomy(PlayerShop plugin) {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().severe("Vault plugin not found!");
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            plugin.getLogger().severe("No economy provider found via Vault!");
            return false;
        }

        economy = rsp.getProvider();
        plugin.getLogger().info("Economy provider found: " + economy.getName());
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }
}
