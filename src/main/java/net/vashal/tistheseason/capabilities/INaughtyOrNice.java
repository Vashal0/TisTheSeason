package net.vashal.tistheseason.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface INaughtyOrNice extends INBTSerializable<CompoundTag> {

    int getCurrentScore();

    int getMaxScore();

    int getMinScore();

    void setMaxScore(int max);

    void setMinScore(int min);

    int setScore(final int score);

    int addScore(final int scoreToAdd);

    double removeScore(final int scoreToRemove);

    boolean IsReadyForGift();

    void setGiftStatus(boolean bool);
}
