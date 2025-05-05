package me.joff1507.pshop.manager;

import me.joff1507.pshop.PlayerShop;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class VaultManager {
    private final PlayerShop plugin;
    private Economy economy;

    public VaultManager(PlayerShop plugin) {
        this.plugin = plugin;
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            this.economy = plugin.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
            plugin.getLogger().info("Vault hook réussi.");
        } else {
            plugin.getLogger().warning("Vault est manquant, économie désactivée !");
        }
    }

    public boolean hasEnough(Player player, double amount) {
        return economy != null && economy.has(player, amount);
    }

    public boolean withdraw(Player player, double amount) {
        if (economy == null) return false;
        return economy.withdrawPlayer(player, amount).transactionSuccess();
    }

    public boolean deposit(OfflinePlayer target, double amount) {
        if (economy == null) return false;
        return economy.depositPlayer(target, amount).transactionSuccess();
    }

    public String format(double amount) {
        return (economy != null) ? economy.format(amount) : amount + "$";
    }
}
