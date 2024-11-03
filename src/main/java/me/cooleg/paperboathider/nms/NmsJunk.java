package me.cooleg.paperboathider.nms;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftBoat;
import org.bukkit.craftbukkit.entity.boat.CraftOakBoat;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;

public class NmsJunk {

    public static org.bukkit.entity.Boat spawnBoat(CollisionlessBoat boat, Location location, Level level) {
        boat.setInitialPos(location.getX(), location.getY(), location.getZ());
        float yaw = Location.normalizeYaw(location.getYaw());
        boat.setYRot(yaw);
        boat.yRotO = yaw;
        boat.setYHeadRot(yaw);
        level.addFreshEntity(boat);
        return new CraftOakBoat((CraftServer) Bukkit.getServer(), boat);
    }

    public static org.bukkit.entity.Boat spawnBoat(Location location) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        return spawnBoat(new CollisionlessBoat(EntityType.OAK_BOAT, level, () -> Items.OAK_BOAT), location, level);
    }

    public static org.bukkit.entity.Boat replaceBoat(Boat boat) {
        AbstractBoat oldBoat = ((CraftBoat) boat).getHandle();
        CollisionlessBoat newBoat = new CollisionlessBoat(
                (EntityType<? extends net.minecraft.world.entity.vehicle.Boat>) oldBoat.getType(),
                oldBoat.level(), oldBoat::getDropItem);

        Boat newCraftBoat = spawnBoat(newBoat, boat.getLocation(), oldBoat.level());

        for (Entity passenger : boat.getPassengers()) {
            newCraftBoat.addPassenger(passenger);
        }

        boat.remove();

        return newCraftBoat;
    }

    public static boolean isCollisionless(org.bukkit.entity.Boat boat) {
        return ((CraftBoat) boat).getHandle() instanceof CollisionlessBoat;
    }

}
