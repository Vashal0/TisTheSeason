package net.vashal.tistheseason.entity.custom;


import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.ai.ToyRobotFollow;
import net.vashal.tistheseason.entity.variant.ToyRobotVariant;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.sounds.TTS_Sounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Objects;
import java.util.UUID;

public class ToyRobotEntity extends TamableAnimal implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final EntityDataAccessor<Integer> WIND_POSITION = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ACTIVATED_TICKS = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> MUFFLED = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.BOOLEAN);


    public ToyRobotEntity(EntityType<? extends ToyRobotEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ToyRobotEntity(Level world) {
        super(TTS_EntityTypes.TOY_ROBOT.get(), world);
    }


    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, ToyRobotConstants.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, ToyRobotConstants.ATTACK_DAMAGE)
                .add(Attributes.ATTACK_SPEED, ToyRobotConstants.ATTACK_SPEED)
                .add(Attributes.ARMOR, ToyRobotConstants.ARMOR)
                .add(Attributes.MOVEMENT_SPEED, ToyRobotConstants.MOVEMENT_SPEED).build();
    }

    @Nullable
    public static ToyRobotEntity create(Level world) {
        return TTS_EntityTypes.TOY_ROBOT.get().create(world);
    }


    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<ToyRobotEntity> windController = new AnimationController<>(this, "windController", 0, this::windPredicate); //spins the wind when active
        AnimationController<ToyRobotEntity> feetController = new AnimationController<>(this, "feetController", 0, this::feetPredicate); //moves the feet when active
        AnimationController<ToyRobotEntity> deactivatedController = new AnimationController<>(this, "deactivatedController", 0, this::deactivatedPredicate);
        AnimationController<ToyRobotEntity> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        AnimationController<ToyRobotEntity> deathController = new AnimationController<>(this, "deathController", 0, this::deathPredicate);
        AnimationController<ToyRobotEntity> meleeController = new AnimationController<>(this, "meleeController", 0, this::meleePredicate);
        idleController.registerSoundListener(this::soundListenerIdle);
        data.addAnimationController(windController);
        data.addAnimationController(feetController);
        data.addAnimationController(deactivatedController); //10 frames of different wind positions while 'deactivated'
        data.addAnimationController(idleController); //plays both the 'walking' and idle animations currently
        data.addAnimationController(deathController); //plays a death animation
        data.addAnimationController(meleeController); //plays a melee attack animation
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <E extends IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            if (getActivatedStatus() && !this.isAggressive()) {
                if (event.isMoving()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_WALK, ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }

                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_IDLE, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }


    private void soundListenerIdle(SoundKeyframeEvent<ToyRobotEntity> event) {
        ToyRobotEntity robot = event.getEntity();
        if (this.level.isClientSide()) {
            if (event.sound.equals("toyambient")) {
                this.level.playLocalSound(robot.getX(), robot.getY(), robot.getZ(), TTS_Sounds.TOY_AMBIENT.get(), robot.getSoundSource(), 0.5f, 1f, true);
            }
        }
    }

    private <E extends IAnimatable> PlayState deathPredicate(AnimationEvent<E> event) {
        if (deathTime > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.death", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState meleePredicate(AnimationEvent<E> event) {
        if (this.swinging && deathTime == 0 && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.melee", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel pLevel, @NotNull AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WIND_POSITION, 0);
        this.entityData.define(ACTIVATED_TICKS, 0);
        this.entityData.define(IS_ACTIVATED, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(MUFFLED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("WindPosition", this.getWindCount());
        tag.putInt("ActivatedTicks", this.getActivatedTicks());
        tag.putBoolean("isActivated", this.getActivatedStatus());
        tag.putInt("Variant", this.getTypeVariant());
        tag.putBoolean("Muffled", this.getMuffled());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setWind(tag.getInt("WindPosition"));
        this.setTickCount(tag.getInt("ActivatedTicks"));
        this.setActivationStatus(tag.getBoolean("isActivated"));
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
        this.setMuffled(tag.getBoolean("Muffled"));
    }

    public boolean getMuffled() {
        return this.entityData.get(MUFFLED);
    }

    public void setMuffled(boolean bool) {
        this.entityData.set(MUFFLED, bool);
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
        if (!player.level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            ItemStack stack = player.getItemInHand(hand);
            if (!player.isShiftKeyDown()) {
                if (this.getOwner() == null) {
                    this.tame(player);
                }
                if (this.isOwnedBy(player)) {
                    if (getWindCount() < 9 && !getActivatedStatus()) {
                        this.setWind(getWindCount() + 1);
                        playSound(TTS_Sounds.WIND_TURN.get());
                    } else if (getWindCount() == 9 && !getActivatedStatus()) {
                        setActivationStatus(true);
                        setWind(getWindCount() - 9);
                        setTickCount(6000);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
                return InteractionResult.SUCCESS;
            } else if (stack.isEmpty()) {
                CompoundTag nbt = new CompoundTag();
                nbt.putString("toy", EntityType.getKey(this.getType()).toString());
                this.saveWithoutId(nbt);
                player.setItemInHand(hand, TTS_Items.TOY_ROBOT_ITEM.get().getDefaultInstance());
                ItemStack stack1 = player.getItemInHand(hand);
                stack1.setTag(nbt);
                stack1.getOrCreateTag().putInt("ActivatedTicks", 0);
                stack1.getOrCreateTag().putBoolean("isActivated", false);
                this.remove(Entity.RemovalReason.KILLED);
                return InteractionResult.CONSUME;
            } else if (stack.getItem() == Items.WHITE_WOOL) {
                this.setMuffled(true);
                stack.shrink(1);
                return InteractionResult.CONSUME;
            } else if (stack.getItem() == Items.SHEARS) {
                if (this.getMuffled()) {
                    this.setMuffled(false);
                }
                if (stack.isDamageableItem()) {
                    stack.setDamageValue(stack.getDamageValue()+1);
                }
                ItemEntity itementity = this.spawnAtLocation(Items.WHITE_WOOL, 1);
                if (itementity != null) {
                    itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, (double)(this.random.nextFloat() * 0.05F), (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
                }
                return InteractionResult.CONSUME;
            } else if (stack.getItem() instanceof DyeItem) {
                this.setVariant(ToyRobotVariant.byId(Objects.requireNonNull(DyeColor.getColor(stack)).getId()));
                stack.shrink(1);
                return InteractionResult.CONSUME;
            }
        }
        return super.mobInteract(player, hand);
    }



    @Override
    public void tick() { //tracks activated state
        super.tick();
        if (getActivatedTicks() > 0) {
            setTickCount(getActivatedTicks() - 1);
            if (!getMuffled()) {
                playModSounds();
            }
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
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTS_Sounds.TOY_WALK.get(), this.getSoundSource(), 0.3f, 0.6f, true);
            }
            if (tickCount % 512 == 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTS_Sounds.TOY_GEARS.get(), this.getSoundSource(), 0.4f, 1.2f, true);
            }
        }
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ToyMeleeGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new ToyRobotFollow(this, 1.5f, 2.0f, 12.0f));
        this.goalSelector.addGoal(3, new FloatGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

        super.registerGoals();
    }

    private <E extends IAnimatable> PlayState windPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            if (getActivatedStatus()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_WIND, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState feetPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            if (getActivatedStatus()) {
                this.playSound(TTS_Sounds.TOY_WALK.get());
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_FEET_MOVEMENT, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState deactivatedPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            String animation = "animation.toyrobot.deactivated";
            if (!getActivatedStatus() && getWindCount() >= 0) {
                animation += getWindCount();
                event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    //sounds without a special use case

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ANVIL_STEP, ToyRobotConstants.STEP_SOUND_VOLUME, ToyRobotConstants.STEP_SOUND_PITCH);
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return TTS_Sounds.TOY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return TTS_Sounds.TOY_DEATH.get();
    }

    protected float getSoundVolume() {
        return ToyRobotConstants.SOUND_VOLUME;
    }

    public boolean canBeLeashed(@NotNull Player player) {
        return true;
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    //prevents attacks while deactivated
    public class ToyMeleeGoal extends MeleeAttackGoal {

        public ToyMeleeGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(mob, speedModifier, followingTargetEvenIfNotSeen);
        }

        @Override
        public boolean canUse() {
            if (!getActivatedStatus()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (!getActivatedStatus()) {
                return false;
            }
            return super.canContinueToUse();
        }
    }

    // Variant stuff

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance instance,
                                        @NotNull MobSpawnType type, @Nullable SpawnGroupData data,
                                        @Nullable CompoundTag tag) {
        ToyRobotVariant variant = Util.getRandom(ToyRobotVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(levelAccessor, instance, type, data, tag);
    }

    public ToyRobotVariant getVariant() {
        return ToyRobotVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    public void setVariant(ToyRobotVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

}

