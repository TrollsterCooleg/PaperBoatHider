package me.cooleg.paperboathider.nms;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class CollisionlessBoat extends Boat {

    public CollisionlessBoat(EntityType<? extends Boat> type, Level world, Supplier<Item> itemSupplier) {
        super(type, world, itemSupplier);
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

}
