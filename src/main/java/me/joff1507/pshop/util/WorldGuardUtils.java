package me.joff1507.pshop.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardUtils {

    public static ProtectedRegion getRegionForPlayer(Player player) {
        Location loc = player.getLocation();

        World weWorld = BukkitAdapter.adapt(loc.getWorld());
        BlockVector3 pos = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        RegionManager regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(weWorld);
        if (regions == null) return null;

        ApplicableRegionSet set = regions.getApplicableRegions(pos);
        for (ProtectedRegion region : set) {
            return region;
        }

        return null;
    }

    public static boolean isPlayerInRegionWithPrefix(Player player, String prefix) {
        ProtectedRegion region = getRegionForPlayer(player);
        return region != null && region.getId().toLowerCase().startsWith(prefix.toLowerCase());
    }

    public static WorldGuardPlugin getWorldGuard() {
        return (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
    }
}
