package net.vashal.tistheseason.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class RedstoneTouch implements IRedstoneTouch {

    private final LivingEntity livingEntity;
    private int state = 0;

    public RedstoneTouch(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public int getCurrentState() {
        return state;
    }

    @Override
    public void setState(int state) {
        if (state > 2) {
            this.state = 2;
        } else this.state = Math.max(state, 0);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("redstone", getCurrentState());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setState(tag.getInt("redstone"));
    }
}
