package me.joff1507.pshop.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramManager {

    private final Plugin plugin;
    private final Map<String, UUID> holograms = new HashMap<>();

    public HologramManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void spawnHologram(String shopId, Location location, String text) {
        removeHologram(shopId); // Supprime le précédent si présent

        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName(text);
        armorStand.setMarker(true);
        armorStand.setSmall(true);

        holograms.put(shopId, armorStand.getUniqueId());
    }

    public void removeHologram(String shopId) {
        UUID uuid = holograms.get(shopId);
        if (uuid == null) return;

        for (World world : Bukkit.getWorlds()) {
            ArmorStand armorStand = (ArmorStand) world.getEntity(uuid);
            if (armorStand != null && armorStand.getType() == EntityType.ARMOR_STAND) {
                armorStand.remove();
                break;
            }
        }

        holograms.remove(shopId);
    }

    public void updateHologram(String shopId, String newText) {
        UUID uuid = holograms.get(shopId);
        if (uuid == null) return;

        for (World world : Bukkit.getWorlds()) {
            ArmorStand armorStand = (ArmorStand) world.getEntity(uuid);
            if (armorStand != null && armorStand.getType() == EntityType.ARMOR_STAND) {
                armorStand.setCustomName(newText);
                break;
            }
        }
    }
}
