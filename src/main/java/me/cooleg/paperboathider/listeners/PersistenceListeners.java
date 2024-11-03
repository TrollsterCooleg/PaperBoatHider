package me.cooleg.paperboathider.listeners;

import me.cooleg.paperboathider.nms.NmsJunk;
import org.bukkit.Chunk;
import org.bukkit.entity.Boat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class PersistenceListeners implements Listener {

    private final JavaPlugin plugin;

    public PersistenceListeners(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void worldLoadEvent(WorldLoadEvent event) {
        for (Chunk chunk : event.getWorld().getLoadedChunks()) {
            for (Boat boat : Arrays.stream(chunk.getEntities())
                    .filter(entity -> entity.getType().getKey().getKey().contains("BOAT"))
                    .map((entity -> (Boat) entity))
                    .toList()) {
                replaceBoat(boat);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void chunkLoadEvent(ChunkLoadEvent event) {
        for (Boat boat : Arrays.stream(event.getChunk().getEntities())
                .filter(entity -> entity.getType().getKey().getKey().contains("BOAT"))
                .map((entity -> (Boat) entity))
                .toList()) {
            replaceBoat(boat);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void boatSpawn(VehicleCreateEvent event) {
        if (!(event.getVehicle() instanceof Boat boat) || NmsJunk.isCollisionless(boat)) {return;}
        new BukkitRunnable() {
            @Override
            public void run() {
                if (boat.isDead()) {return;}
                replaceBoat(boat);
            }
        }.runTaskLater(plugin, 1L);
    }

    private void replaceBoat(Boat boat) {
        NmsJunk.replaceBoat(boat);
    }

}
