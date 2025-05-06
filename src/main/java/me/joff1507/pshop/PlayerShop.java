package me.joff1507.pshop.shop;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.joff1507.pshop.PlayerShop;
import me.joff1507.pshop.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ShopManager {

    private final PlayerShop plugin;
    private final Map<String, Shop> shops = new HashMap<>();
    private final File shopFolder;

    public ShopManager(PlayerShop plugin) {
        this.plugin = plugin;
        this.shopFolder = new File(plugin.getDataFolder(), "shops");
        if (!shopFolder.exists()) {
            shopFolder.mkdirs();
        }
        loadShops();
    }

    public void loadShops() {
        File[] files = shopFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;

        for (File file : files) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            String id = file.getName().replace(".yml", "");
            String regionId = "shop_" + id;

            // Vérifie l'existence de la région WorldGuard correspondante
            if (!WorldGuardUtils.regionExists(regionId)) {
                plugin.getLogger().warning("La région WorldGuard '" + regionId + "' est introuvable pour le shop '" + id + "'.");
                continue;
            }

            OfflinePlayer owner = Bukkit.getOfflinePlayer(UUID.fromString(config.getString("owner")));
            List<Location> chests = new ArrayList<>();

            List<String> chestStrings = config.getStringList("chests");
            for (String chestStr : chestStrings) {
                String[] parts = chestStr.split(",");
                if (parts.length == 4) {
                    Location loc = new Location(
                            Bukkit.getWorld(parts[0]),
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3])
                    );
                    chests.add(loc);
                }
            }
            Shop shop = new Shop(id, owner, chests);
            shops.put(id, shop);
        }
    }

    public void saveShop(Shop shop) {
        File file = new File(shopFolder, shop.getId() + ".yml");
        YamlConfiguration config = new YamlConfiguration();
        config.set("owner", shop.getOwner().getUniqueId().toString());

        List<String> chestStrings = new ArrayList<>();
        for (Location loc : shop.getChests()) {
            chestStrings.add(loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
        }
        config.set("chests", chestStrings);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Shop getShop(String id) {
        return shops.get(id);
    }

    public boolean createShop(String id, Player owner) {
        String regionId = "shop_" + id;

        if (!WorldGuardUtils.regionExists(regionId)) {
            owner.sendMessage(ChatColor.RED + "La région '" + regionId + "' n'existe pas dans WorldGuard.");
            return false;
        }

        if (shops.containsKey(id)) return false;
        Shop shop = new Shop(id, owner, new ArrayList<>());
        shops.put(id, shop);

        // Mise à jour du propriétaire dans WorldGuard
        WorldGuardUtils.setRegionOwner(regionId, owner);

        saveShop(shop);
        return true;
    }

    public boolean deleteShop(String id) {
        if (!shops.containsKey(id)) return false;
        shops.remove(id);
        File file = new File(shopFolder, id + ".yml");
        return file.delete();
    }

    public boolean isInShopRegion(Player player) {
        return WorldGuardUtils.isInRegion(player, "shop_");
    }

    public Collection<Shop> getAllShops() {
        return shops.values();
    }
}
