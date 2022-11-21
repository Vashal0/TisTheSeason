package net.vashal.tistheseason.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.sounds.TTSSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;


import java.util.UUID;

public abstract class WindUpToys extends TamableAnimal implements NeutralMob, IAnimatable {

    public WindUpToys(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);

    }

    private static final EntityDataAccessor<Integer> WIND_POSITION = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ACTIVATED_TICKS = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WIND_POSITION, 0);
        this.entityData.define(ACTIVATED_TICKS, 0);
        this.entityData.define(IS_ACTIVATED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("WindPosition", this.getWindCount());
        tag.putInt("ActivatedTicks", this.getActivatedTicks());
        tag.putBoolean("isActivated", this.getActivatedStatus());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setWind(tag.getInt("WindPosition"));
        this.setTickCount(tag.getInt("ActivatedTicks"));
        this.setActivationStatus(tag.getBoolean("isActivated"));
    }

    public void setWind(int position) {
        this.entityData.set(WIND_POSITION, position);
    }

    public int getWindCount() {
        return this.entityData.get(WIND_POSITION);
    }

    public void setActivationStatus(boolean bool) {
        this.entityData.set(IS_ACTIVATED, bool);
    }

    public boolean getActivatedStatus() {
        return this.entityData.get(IS_ACTIVATED);
    }

    public int getActivatedTicks() {
        return this.entityData.get(ACTIVATED_TICKS);
    }

    public void setTickCount(int count) {
        this.entityData.set(ACTIVATED_TICKS, count);
    }


    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) { //every right click turns the wind on the back, after 10 the toy becomes active for 30 seconds
        if (player.level.isClientSide && hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
        if (this.isOwnedBy(player)) {
            if (getWindCount() < 9 && !getActivatedStatus()) {
                this.setWind(getWindCount() + 1);
                playSound(TTSSounds.WIND_TURN.get());
                return InteractionResult.SUCCESS;
            } else if (getWindCount() == 9 && !getActivatedStatus()) {
                setActivationStatus(true);
                setWind(getWindCount() - 9);
                setTickCount(600);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() { //tracks activated state
        super.tick();
        if (getActivatedTicks() > 0) {
            setTickCount(getActivatedTicks() - 1);
            playModSounds();
        }
        if (getActivatedTicks() == 0) {
            setActivationStatus(false);
        }
    }

    @Override
    protected void tickDeath() { //delays death long enough to play custom animation
        ++this.deathTime;
        if (this.deathTime == 60 && !this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    public void playModSounds() { //plays the walking sound much faster than normal
        if (this.level.isClientSide() && deathTime == 0) {
            if (tickCount % 3 == 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTSSounds.TOY_WALK.get(), this.getSoundSource(), 0.3f, 0.6f, true);
            }
        }
    }

    //sounds without a special use case

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ANVIL_STEP, ToyRobotConstants.STEP_SOUND_VOLUME, ToyRobotConstants.STEP_SOUND_PITCH);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return TTSSounds.TOY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return TTSSounds.TOY_DEATH.get();
    }

    protected float getSoundVolume() {
        return ToyRobotConstants.SOUND_VOLUME;
    }

    //stuff I haven't messed with yet

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    public boolean canBeLeashed(Player player) {
        return true;
    }

}
