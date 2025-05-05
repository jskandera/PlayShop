package me.joff1507.pshop.manager;

import me.joff1507.pshop.PlayerShop;
import me.joff1507.pshop.util.WorldGuardUtils;
import me.joff1507.pshop.manager.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class ShopManager {
    private final PlayerShop plugin;
    private final File shopFolder;
    private final Map<String, String> shopOwners = new HashMap<>();

    public ShopManager(PlayerShop plugin) {
        this.plugin = plugin;
        this.shopFolder = new File(plugin.getDataFolder(), "shops");
        if (!shopFolder.exists()) shopFolder.mkdirs();
    }

    public void createShop(String shopId, String playerName, CommandSender sender) {
        File file = new File(shopFolder, shopId + ".yml");
        if (file.exists()) {
            sender.sendMessage(ChatColor.RED + "Ce shop existe déjà.");
            return;
        }
        YamlConfiguration config = new YamlConfiguration();
        config.set("region", shopId);
        config.set("owner", playerName != null ? playerName : "");
        config.set("chests", new ArrayList<>());
        try {
            config.save(file);
            sender.sendMessage(ChatColor.GREEN + "Shop " + shopId + " créé.");
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Erreur lors de la création du shop", e);
            sender.sendMessage(ChatColor.RED + "Erreur lors de la création du shop.");
        }
    }

    public void assignShop(String shopId, String playerName, CommandSender sender) {
        File file = new File(shopFolder, shopId + ".yml");
        if (!file.exists()) {
            sender.sendMessage(ChatColor.RED + "Ce shop n'existe pas.");
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("owner", playerName);
        try {
            config.save(file);
            sender.sendMessage(ChatColor.GREEN + "Shop " + shopId + " assigné à " + playerName);
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Erreur d'écriture sur le shop.");
        }
    }

    public void unassignShop(String shopId, CommandSender sender) {
        File file = new File(shopFolder, shopId + ".yml");
        if (!file.exists()) {
            sender.sendMessage(ChatColor.RED + "Ce shop n'existe pas.");
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("owner", "");
        try {
            config.save(file);
            sender.sendMessage(ChatColor.GREEN + "Shop libéré.");
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Erreur d'écriture sur le shop.");
        }
    }

    public void listShops(CommandSender sender) {
        File[] files = shopFolder.listFiles();
        if (files == null || files.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Aucun shop existant.");
            return;
        }
        sender.sendMessage(ChatColor.AQUA + "Shops disponibles :");
        for (File file : files) {
            String id = file.getName().replace(".yml", "");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            String owner = config.getString("owner", "(non assigné)");
            sender.sendMessage(" - " + id + ChatColor.GRAY + " ➝ " + ChatColor.GOLD + owner);
        }
    }

    public boolean isInShopRegion(Player player) {
        return WorldGuardUtils.isInRegion(player, "shop_");
    }

    public void logTransaction(Player buyer, String itemName, double price, int quantity, String seller) {
        LogUtils.log(buyer.getName() + " a acheté " + quantity + "x " + itemName + " pour " + price + "$ à " + seller);
    }

    public File getShopFile(String shopId) {
        return new File(shopFolder, shopId + ".yml");
    }
}
