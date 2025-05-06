package me.joff1507.pshop.manager;

import org.bukkit.Location;
import java.util.UUID;

public class Shop {
    private final String id;
    private final UUID owner;
    private final Location chestLocation;

    public Shop(String id, UUID owner, Location chestLocation) {
        this.id = id;
        this.owner = owner;
        this.chestLocation = chestLocation;
    }

    public String getId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }

    public Location getChestLocation() {
        return chestLocation;
    }
}
