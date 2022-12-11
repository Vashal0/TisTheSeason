package net.vashal.tistheseason.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class NaughtyOrNice implements INaughtyOrNice {

    private final LivingEntity livingEntity;

    private int score = 0;

    private int maxScore = 500;

    private int minScore = -500;

    private int festiveMultiplier = 1;

    private boolean status = false;

    public NaughtyOrNice(@Nullable final LivingEntity entity) {
        this.livingEntity = entity;
    }

    @Override
    public int getCurrentScore() {
        return score;
    }

    @Override
    public int getMaxScore() {
        return maxScore;
    }

    @Override
    public int getMinScore() {
        return minScore;
    }

    @Override
    public void setMaxScore(int max) {
        this.maxScore = max;
    }

    @Override
    public void setMinScore(int min) {
        this.minScore = min;
    }

    @Override
    public int setScore(int score) {
        if (score > getMaxScore()) {
            this.score = getMaxScore();
        } else this.score = Math.max(score, getMinScore());
        return this.getCurrentScore();
    }

    @Override
    public int addScore(int scoreToAdd) {
        this.setScore(Math.min(score + scoreToAdd * this.festiveMultiplier, getMaxScore()));
        return this.getCurrentScore();
    }

    @Override
    public double removeScore(int scoreToRemove) {
        this.setScore(Math.max(score - scoreToRemove * this.festiveMultiplier, getMinScore()));
        return this.getCurrentScore();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("current", getCurrentScore());
        tag.putInt("max", getMaxScore());
        tag.putInt("min", getMinScore());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setMaxScore(tag.getInt("max"));
        setScore(tag.getInt("current"));
        setMinScore(tag.getInt("min"));
    }

    @Override
    public void setGiftStatus(boolean bool) {
        status = bool;
    }

    @Override
    public boolean IsReadyForGift() {
        return status;
    }

    @Override
    public void addFestiveMultiplier(int stack) {
        this.festiveMultiplier = getFestiveMultiplier() + stack;
    }

    @Override
    public void removeFestiveMultiplier(int stack) {
        this.festiveMultiplier = getFestiveMultiplier() - stack;
    }
    @Override
    public int getFestiveMultiplier() {
        return this.festiveMultiplier;
    }
}
