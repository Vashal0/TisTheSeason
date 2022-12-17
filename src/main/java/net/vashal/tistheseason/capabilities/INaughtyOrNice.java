package net.vashal.tistheseason.capabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.vashal.tistheseason.block.entity.StockingBlockEntity;

public interface INaughtyOrNice extends INBTSerializable<CompoundTag> {

    int getCurrentScore();

    int getMaxScore();

    int getMinScore();

    void setMaxScore(int max);

    void setMinScore(int min);

    int setScore(final int score);

    int addScore(final int scoreToAdd);

    double removeScore(final int scoreToRemove);

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
