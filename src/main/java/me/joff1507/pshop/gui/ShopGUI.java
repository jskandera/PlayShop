package me.joff1507.pshop.gui;

import me.joff1507.pshop.manager.LogUtils;
import me.joff1507.pshop.manager.ShopManager;
import me.joff1507.pshop.manager.VaultManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopGUI {

    private final ShopManager shopManager;
    private final VaultManager vaultManager;
    private final LogUtils log;

    public ShopGUI(ShopManager shopManager, VaultManager vaultManager, LogUtils log) {
        this.shopManager = shopManager;
        this.vaultManager = vaultManager;
        this.log = log;
    }

    public void openShop(Player player, String shopId) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Shop: " + shopId);

        // Example item to sell
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("Buy Diamond - $100");
            item.setItemMeta(meta);
        }

        inventory.setItem(13, item); // center slot

        player.openInventory(inventory);
    }

    public void handleClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getView().getTitle() == null || !event.getView().getTitle().startsWith("Shop: ")) return;

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getType() == Material.AIR) return;

        String shopId = event.getView().getTitle().replace("Shop: ", "");

        // Example interaction
        if (clicked.getType() == Material.DIAMOND) {
            double cost = 100.0;
            if (vaultManager.withdraw(player, cost)) {
                player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                log.message(player, "&aYou bought a diamond for $" + cost);
            } else {
                log.message(player, "&cYou don't have enough money!");
            }
        }
    }
}
