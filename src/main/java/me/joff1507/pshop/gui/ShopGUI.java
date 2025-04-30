package me.joff1507.pshop.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class ShopGUI {

    public static void openSetupGUI(Player player, Consumer<ShopSetupResult> callback) {
        Inventory gui = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.GREEN + "Configurer votre vente");

        ItemStack confirm = new ItemStack(Material.LIME_WOOL);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GREEN + "Confirmer");
        confirm.setItemMeta(confirmMeta);

        ItemStack cancel = new ItemStack(Material.RED_WOOL);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.RED + "Annuler");
        cancel.setItemMeta(cancelMeta);

        gui.setItem(0, new ItemStack(Material.GOLD_INGOT)); // prix
        gui.setItem(2, new ItemStack(Material.CHEST));      // stock
        gui.setItem(3, confirm);
        gui.setItem(4, cancel);

        player.openInventory(gui);

        // Tu devras ensuite créer un listener InventoryClickEvent pour interpréter les clics
    }

    public record ShopSetupResult(int price, int stock) {}
}