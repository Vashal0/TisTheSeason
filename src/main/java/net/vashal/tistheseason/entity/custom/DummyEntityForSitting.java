package net.vashal.tistheseason.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class DummyEntityForSitting extends Entity {
    boolean rising = false;
    double rise = 0.35;
    public DummyEntityForSitting(EntityType<? extends DummyEntityForSitting> type, Level world) {
        super(type, world);
        noPhysics = true;
    }

    public void setSeatPos(BlockPos pos) {
        setPos(pos.getX() + 0.5D, pos.getY() + 1D, pos.getZ() + 0.5D);
    }

    @Override
    public double getPassengersRidingOffset() {
            return this.getBbHeight() * 0.65F - rise;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount % 20 == 0) {
            this.rising = !this.rising;
        }
        if (rising) {
            rise = rise + 0.01;
        }
        if (!rising) {
            rise = rise - 0.01;
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {
    }

    @Override
    protected void removePassenger(@NotNull Entity entity) {
        super.removePassenger(entity);
        if (!this.isRemoved() && !this.level.isClientSide()) {
            entity.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), entity.yRotO, entity.xRotO);
            this.discard();
        }
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}