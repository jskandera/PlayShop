package me.joff1507.pshop.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardUtils {

    public static boolean isInRegion(Player player, String regionPrefix) {
        try {
            WorldGuardPlugin wg = getWorldGuard();
            if (wg == null) return false;

            RegionManager manager = wg.getRegionManager(player.getWorld());
            if (manager == null) return false;

            Location loc = player.getLocation();
            ApplicableRegionSet set = manager.getApplicableRegions(loc);
            for (ProtectedRegion region : set) {
                if (region.getId().toLowerCase().startsWith(regionPrefix.toLowerCase())) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    private static WorldGuardPlugin getWorldGuard() {
        return (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
    }
}
