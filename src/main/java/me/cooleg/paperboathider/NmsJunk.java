package me.cooleg.paperboathider;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.Boat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftBoat;

public class NmsJunk {

    public static org.bukkit.entity.Boat spawnBoat(Location location) {
        ServerLevel level = ((CraftWorld) location.getWorld()).getHandle();
        CollisionlessBoat boat = new CollisionlessBoat(level, location.getX(), location.getY(), location.getZ());
        float yaw = Location.normalizeYaw(location.getYaw());
        boat.setYRot(yaw);
        boat.yRotO = yaw;
        boat.setYHeadRot(yaw);
        level.addFreshEntity(boat);
        boat.setVariant(Boat.Type.MANGROVE);
        return new CraftBoat((CraftServer) Bukkit.getServer(), boat);
    }

    public static boolean isCollisionless(org.bukkit.entity.Boat boat) {
        return ((CraftBoat) boat).getHandle() instanceof CollisionlessBoat;
    }

}
