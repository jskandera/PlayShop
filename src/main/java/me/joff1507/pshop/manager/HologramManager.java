package me.joff1507.pshop.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class HologramManager {
    private final Map<Location, List<Integer>> holograms = new HashMap<>();
    private final int refreshTicks = 100;

    public HologramManager(PlayerShop plugin) {
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateAllHolograms, 20L, refreshTicks);
    }

    public void showHologram(Player player, Location location, String itemName, double price, int stock) {
        List<String> lines = Arrays.asList(
                ChatColor.YELLOW + "[" + itemName + "]",
                ChatColor.GREEN + "Prix : " + price + "$",
                ChatColor.GRAY + "Stock : " + stock
        );

        int entityId = 200000 + new Random().nextInt(100000);
        int yOffset = 0;

        for (String line : lines) {
            Location displayLoc = location.clone().add(0.5, 2.3 - yOffset * 0.3, 0.5);
            player.sendMessage(ChatColor.GRAY + "(Hologramme) " + line); // Remplacer par packet via ProtocolLib ou NMS si voulu
            yOffset++;
        }
    }

    public void removeHolograms(Location loc) {
        holograms.remove(loc);
    }

    public void updateAllHolograms() {
        // À connecter plus tard à une liste de coffres actifs
    }
}
