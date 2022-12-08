package net.vashal.tistheseason.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IRedstoneTouch extends INBTSerializable<CompoundTag> {

    int getCurrentState();

    void setState(int state);

}
