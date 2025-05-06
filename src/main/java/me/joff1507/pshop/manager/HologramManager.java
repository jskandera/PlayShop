package me.joff1507.pshop.manager;

import me.joff1507.pshop.PlayerShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class HologramManager {
    private final Map<Location, List<ArmorStand>> holograms = new HashMap<>();
    private final int refreshTicks = 100;
    private final PlayerShop plugin;

    public HologramManager(PlayerShop plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateAllHolograms, 20L, refreshTicks);
    }

    public void showHologram(Player player, Location location, String itemName, double price, int stock) {
        removeHolograms(location); // Nettoyer les anciens si existants

        List<String> lines = Arrays.asList(
                ChatColor.YELLOW + "[" + itemName + "]",
                ChatColor.GREEN + "Prix : " + price + "$",
                ChatColor.GRAY + "Stock : " + stock
        );

        List<ArmorStand> stands = new ArrayList<>();
        double yOffset = 0.0;

        for (String line : lines) {
            Location displayLoc = location.clone().add(0.5, 2.3 - yOffset, 0.5);
            ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(displayLoc, EntityType.ARMOR_STAND);
            stand.setVisible(false);
            stand.setGravity(false);
            stand.setCustomName(ChatColor.translateAlternateColorCodes('&', line));
            stand.setCustomNameVisible(true);
            stand.setMarker(true); // Réduit la hitbox (Spigot 1.8.8+)
            stand.setSmall(true);

            stands.add(stand);
            yOffset += 0.3;
        }

        holograms.put(location, stands);
    }

    public void removeHolograms(Location loc) {
        List<ArmorStand> stands = holograms.remove(loc);
        if (stands != null) {
            for (ArmorStand stand : stands) {
                stand.remove();
            }
        }
    }

    public void updateAllHolograms() {
        // À compléter : rafraîchir dynamiquement les hologrammes si nécessaire
    }
}
