package net.vashal.tistheseason.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface INaughtyOrNice extends INBTSerializable<CompoundTag> {

    int getCurrentScore();

    int getMaxScore();

    int getMinScore();

    void setMaxScore(int max);

    void setMinScore(int min);

    void setScore(final int score);

    void addScore(final int scoreToAdd);

    void removeScore(final int scoreToRemove);

    boolean isReadyForGift();

    void setGiftStatus(boolean bool);

    boolean hasStocking();

    void addFestiveMultiplier(int stack);

    void removeFestiveMultiplier(int stack);

    int getFestiveMultiplier();

    void setStocking(BlockPos stocking);

    void removeStocking();

    BlockPos getStocking();

    int getTime();

    void setTime(int max);


}
