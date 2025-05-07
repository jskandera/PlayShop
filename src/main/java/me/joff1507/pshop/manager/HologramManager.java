package me.joff1507.pshop.manager;

import me.joff1507.pshop.PlayerShop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public class HologramManager {

    private final PlayerShop plugin;
    private final Map<Location, List<UUID>> holograms = new HashMap<>();
    private final int refreshTicks = 100;

    public HologramManager(PlayerShop plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateAllHolograms, 20L, refreshTicks);
    }

    public void createHologram(Location location, List<String> lines) {
        removeHologram(location);

        List<UUID> armorStandIds = new ArrayList<>();
        World world = location.getWorld();
        if (world == null) return;

        double y = location.getY();
        for (String line : lines) {
            Location hologramLocation = new Location(world, location.getX() + 0.5, y, location.getZ() + 0.5);
            ArmorStand armorStand = (ArmorStand) world.spawnEntity(hologramLocation, EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setCustomName(line);
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            armorStandIds.add(armorStand.getUniqueId());
            y -= 0.25;
        }

        holograms.put(location, armorStandIds);
    }

    public void removeHologram(Location location) {
        List<UUID> ids = holograms.remove(location);
        if (ids == null) return;

        World world = location.getWorld();
        if (world == null) return;

        for (UUID id : ids) {
            Entity entity = world.getEntity(id);
            if (entity instanceof ArmorStand) {
                entity.remove();
            }
        }
    }

    public void updateAllHolograms() {
        for (Map.Entry<Location, List<UUID>> entry : holograms.entrySet()) {
            Location location = entry.getKey();
            removeHologram(location);
            // Ici, tu peux reconstruire dynamiquement les lignes si besoin
            // Pour le moment on laisse vide ou statique
            createHologram(location, Collections.singletonList("§eShop"));
        }
    }
}
