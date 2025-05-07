package me.joff1507.pshop.manager;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultManager {

    private static Economy econ = null;

    public static boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        econ = rsp.getProvider();
        return econ != null;
    }

    public static boolean hasEnough(String playerName, double amount) {
        if (econ == null) return false;
        return econ.has(Bukkit.getOfflinePlayer(playerName), amount);
    }

    public static boolean withdraw(String playerName, double amount) {
        if (econ == null) return false;
        return econ.withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount).transactionSuccess();
    }

    public static void deposit(String playerName, double amount) {
        if (econ != null) {
            econ.depositPlayer(Bukkit.getOfflinePlayer(playerName), amount);
        }
    }
}
