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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.vashal.tistheseason.constants.ToySoldierConstants;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.ai.ToySoldierFollow;
import net.vashal.tistheseason.entity.variant.ToySoldierVariant;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.sounds.TTS_Sounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class ToySoldierEntity extends TamableAnimal implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final EntityDataAccessor<Integer> WIND_POSITION = SynchedEntityData.defineId(ToySoldierEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ACTIVATED_TICKS = SynchedEntityData.defineId(ToySoldierEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(ToySoldierEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(ToySoldierEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> MUFFLED = SynchedEntityData.defineId(ToySoldierEntity.class, EntityDataSerializers.BOOLEAN);


    public ToySoldierEntity(EntityType<? extends ToySoldierEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ToySoldierEntity(Level world) {
        super(TTS_EntityTypes.TOY_SOLDIER.get(), world);
    }


    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, ToySoldierConstants.MAX_HEALTH)
                .add(Attributes.MOVEMENT_SPEED, ToySoldierConstants.MOVEMENT_SPEED).build();
    }

    @Nullable
    public static ToySoldierEntity create(Level world) {
        return TTS_EntityTypes.TOY_SOLDIER.get().create(world);
    }


    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<ToySoldierEntity> windController = new AnimationController<>(this, "windController", 0, this::windPredicate); //spins the wind when active
        AnimationController<ToySoldierEntity> feetController = new AnimationController<>(this, "feetController", 0, this::feetPredicate); //moves the feet when active
        AnimationController<ToySoldierEntity> deactivatedController = new AnimationController<>(this, "deactivatedController", 0, this::deactivatedPredicate);
        AnimationController<ToySoldierEntity> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        AnimationController<ToySoldierEntity> deathController = new AnimationController<>(this, "deathController", 0, this::deathPredicate);
        data.addAnimationController(windController);
        data.addAnimationController(feetController);
        data.addAnimationController(deactivatedController); //10 frames of different wind positions while 'deactivated'
        data.addAnimationController(idleController); //plays both the 'walking' and idle animations currently
        data.addAnimationController(deathController); //plays a death animation
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private <E extends IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            if (getActivatedStatus()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToySoldierConstants.ANIMATION_IDLE, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }


    private <E extends IAnimatable> PlayState deathPredicate(AnimationEvent<E> event) {
        if (deathTime > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ToySoldierConstants.ANIMATION_DEATH, ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState windPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            if (getActivatedStatus()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToySoldierConstants.ANIMATION_WIND, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState feetPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            if (getActivatedStatus()) {
                this.playSound(TTS_Sounds.TOY_WALK.get());
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToySoldierConstants.ANIMATION_FEET_MOVEMENT, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState deactivatedPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            String animation = "animation.toydrummer.deactivated";
            if (!getActivatedStatus() && getWindCount() >= 0) {
                animation += getWindCount();
                event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
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
                        setTickCount(600);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
                return InteractionResult.SUCCESS;
            } else if (stack.isEmpty()) {
                CompoundTag nbt = new CompoundTag();
                nbt.putString("toy", EntityType.getKey(this.getType()).toString());
                this.saveWithoutId(nbt);
                player.setItemInHand(hand, TTS_Items.TOY_SOLDIER_ITEM.get().getDefaultInstance());
                ItemStack stack1 = player.getItemInHand(hand);
                stack1.setTag(nbt);
                this.remove(Entity.RemovalReason.KILLED);

            }
        }
        return super.mobInteract(player, hand);
    }


    @Override
    public void tick() { //tracks activated state
        super.tick();
        Level level = this.level;
        BlockPos pos = this.getOnPos();
        if (getActivatedTicks() > 0) {
            setTickCount(getActivatedTicks() - 1);
            if (tickCount % 20 == 0) {
                if (getTypeVariant() == 1) {
                    applyEffects(level, pos, MobEffects.DAMAGE_RESISTANCE);
                } else if (getTypeVariant() == 0) {
                    applyEffects(level, pos, MobEffects.NIGHT_VISION);
                }

            }
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
        }
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new ToySoldierFollow(this, 1.0f, 2.0f, 12.0f));
        this.goalSelector.addGoal(3, new FloatGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

        super.registerGoals();
    }



    //sounds without a special use case

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.WOOD_STEP, ToySoldierConstants.STEP_SOUND_VOLUME, ToySoldierConstants.STEP_SOUND_PITCH);
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return TTS_Sounds.TOY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return TTS_Sounds.TOY_DEATH.get();
    }

    protected float getSoundVolume() {
        return ToySoldierConstants.SOUND_VOLUME;
    }

    public boolean canBeLeashed(@NotNull Player player) {
        return true;
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    // Variant stuff

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance instance,
                                        @NotNull MobSpawnType type, @Nullable SpawnGroupData data,
                                        @Nullable CompoundTag tag) {
        ToySoldierVariant variant = Util.getRandom(ToySoldierVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(levelAccessor, instance, type, data, tag);
    }

    public ToySoldierVariant getVariant() {
        return ToySoldierVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    public void setVariant(ToySoldierVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    //Effects

    private static void applyEffects(Level pLevel, BlockPos pPos, @Nullable MobEffect effect) {
        if (!pLevel.isClientSide && effect != null) {
            AABB aabb = (new AABB(pPos)).inflate(8);
            List<Player> list = pLevel.getEntitiesOfClass(Player.class, aabb);
            for (Player player : list) {
                player.addEffect(new MobEffectInstance(effect, 400, 0, true, false));
            }
        }
    }
}

