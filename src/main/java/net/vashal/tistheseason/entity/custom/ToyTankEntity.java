package net.vashal.tistheseason.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
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
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.vashal.tistheseason.constants.ToyTankConstants;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.ai.ToyTankAttackGoal;
import net.vashal.tistheseason.entity.ai.TurretTankAttackGoal;
import net.vashal.tistheseason.entity.projectile.IronBall;
import net.vashal.tistheseason.entity.variant.ToyTankVariant;
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
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nonnull;

public class ToyTankEntity extends TamableAnimal implements IAnimatable, IAnimationTickable, RangedAttackMob {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);


    public ToyTankEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);

    }

    public ToyTankEntity(Level world) {
        super(TTS_EntityTypes.TOY_TANK.get(), world);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        if (!player.level.isClientSide && hand == InteractionHand.MAIN_HAND && this.isOwnedBy(player)) {
            ItemStack stack = player.getItemInHand(hand);
            if (!player.isShiftKeyDown() && stack.isEmpty() && this.getTurretMode()) {
                this.setDirectionState(this.getDirectionState() + 1);
                if (this.getDirectionState() > 3) {
                    this.setDirectionState(0);
                }
                return InteractionResult.SUCCESS;
            } else if (stack.isEmpty() && player.isShiftKeyDown()) {
                CompoundTag nbt = new CompoundTag();
                nbt.putString("toy", EntityType.getKey(this.getType()).toString());
                this.saveWithoutId(nbt);
                player.setItemInHand(hand, TTS_Items.TOY_TANK_ITEM.get().getDefaultInstance());
                ItemStack stack1 = player.getItemInHand(hand);
                stack1.setTag(nbt);
                this.remove(Entity.RemovalReason.KILLED);
                return InteractionResult.SUCCESS;
            } else if (!player.isShiftKeyDown()) {
                if (stack.getItem() == Items.GUNPOWDER) {
                    if (this.getAmmoCount() < 10000) {
                        this.setAmmoCount(this.getAmmoCount() + 3);
                        stack.shrink(1);
                        return InteractionResult.SUCCESS;
                    }
                } else if (stack.getItem() == Items.TNT && !this.getTurretMode()) {
                    this.setTurretMode(true);
                    stack.shrink(1);
                    this.setShootingRange(this.getShootingRange() + 0.2f);
                    return InteractionResult.SUCCESS;
                } else if (stack.getItem() == Items.TNT && this.getTurretMode() && this.getShootingRange() <= 1.0) {
                    this.setShootingRange(this.getShootingRange() + 0.2f);
                    stack.shrink(1);
                    return InteractionResult.SUCCESS;
                } else if (stack.getItem() == Items.SHEARS && this.getTurretMode()) {
                    this.setTurretMode(false);
                    int amount = (int) (this.getShootingRange() * 10);
                    if (stack.isDamageableItem()) {
                        for (int i = 0; i < amount;i++) {
                            stack.setDamageValue(stack.getDamageValue() + 1);
                            ItemEntity itementity = this.spawnAtLocation(Items.TNT, 1);
                            if (itementity != null) {
                                itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
                            }
                        }
                    }
                    this.setShootingRange(0);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.mobInteract(player, hand);
    }


    public static AttributeSupplier setAttributes() {

        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, ToyTankConstants.MAX_HEALTH)
                .add(Attributes.MOVEMENT_SPEED, ToyTankConstants.MOVEMENT_SPEED).build();
    }

    @Nullable
    public static ToyTankEntity create(Level world) {
        return TTS_EntityTypes.TOY_TANK.get().create(world);
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        super.isInvulnerableTo(pSource);
        return pSource.isExplosion();
    }

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(ToyTankEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(ToyTankEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DIRECTION_STATE = SynchedEntityData.defineId(ToyTankEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AMMO_COUNT = SynchedEntityData.defineId(ToyTankEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(ToyTankEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> TURRET_MODE = SynchedEntityData.defineId(ToyTankEntity.class, EntityDataSerializers.BOOLEAN);


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel pLevel, @NotNull AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(DIRECTION_STATE, 0);
        this.entityData.define(AMMO_COUNT, 0);
        this.entityData.define(RANGE, 0f);
        this.entityData.define(TURRET_MODE, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
        tag.putInt("AttackState", this.getAttackState());
        tag.putInt("DirectionState", this.getDirectionState());
        tag.putInt("AmmoCount", this.getAmmoCount());
        tag.putFloat("Range", this.getShootingRange());
        tag.putBoolean("TurretMode", this.getTurretMode());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
        this.setAttackState(tag.getInt("AttackState"));
        this.setDirectionState(tag.getInt("DirectionState"));
        this.setAmmoCount(tag.getInt("AmmoCount"));
        this.setShootingRange(tag.getInt("Range"));
        this.setTurretMode(tag.getBoolean("TurretMode"));
    }

    public void setTurretMode(boolean mode) {
        this.entityData.set(TURRET_MODE, mode);
    }

    public boolean getTurretMode() {
        return this.entityData.get(TURRET_MODE);
    }

    public void setShootingRange(float range) {
        this.entityData.set(RANGE, range);
    }

    public float getShootingRange() {
        return this.entityData.get(RANGE);
    }

    public void setAmmoCount(int state) {
        this.entityData.set(AMMO_COUNT, state);
    }

    public int getAmmoCount() {
        return this.entityData.get(AMMO_COUNT);
    }

    public void setDirectionState(int state) {
        this.entityData.set(DIRECTION_STATE, state);
    }

    public int getDirectionState() {
        return this.entityData.get(DIRECTION_STATE);
    }

    public void setAttackState(int state) {
        this.entityData.set(ATTACK_STATE, state);
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }


    @Override
    public void tick() { //tracks activated state
        super.tick();
    }

    @Override
    protected void tickDeath() { //delays death long enough to play custom animation
        ++this.deathTime;
        if (this.deathTime == 60 && !this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TurretTankAttackGoal(this, 1.0D, 60));
        this.goalSelector.addGoal(2, new ToyTankAttackGoal(this, 1.0D, 60, 10.0F));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 2.0D, 8, 3, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<ToyTankEntity> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        AnimationController<ToyTankEntity> attackController = new AnimationController<>(this, "attackController", 0, this::attackPredicate);
        data.addAnimationController(idleController);
        data.addAnimationController(attackController);
    }

    private <E extends IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
        if (deathTime == 0 && this.getAttackState() == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyTankConstants.ANIMATION_IDLE, ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.getAttackState() == 1 && deathTime == 0 && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyTankConstants.ANIMATION_ATTACK, ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }


    //sounds without a special use case

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ANVIL_STEP, ToyTankConstants.STEP_SOUND_VOLUME, ToyTankConstants.STEP_SOUND_PITCH);
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return TTS_Sounds.TOY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return TTS_Sounds.TOY_DEATH.get();
    }

    protected float getSoundVolume() {
        return ToyTankConstants.SOUND_VOLUME;
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public int tickTimer() {
        return tickCount;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
        IronBall ironBall = new IronBall(this.level, this, new ItemStack(TTS_Items.IRON_BALL_ITEM.get()));
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333) - ironBall.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        ironBall.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.1F, 1);
        this.playSound(SoundEvents.GENERIC_EXPLODE, 0.1F, 1.2F);
        this.level.addFreshEntity(ironBall);
    }

    public void performRangedAttack(BlockPos pTarget) {
        IronBall ironBall = new IronBall(this.level, this, new ItemStack(TTS_Items.IRON_BALL_ITEM.get()));
        double d0 = (pTarget.getX()+0.5) - this.getX();
        double d1 = pTarget.getY() - ironBall.getY();
        double d2 = (pTarget.getZ()+0.5) - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        ironBall.shoot(d0, d1 + d3 * 0.2 + this.getShootingRange(), d2, 0.6F+this.getShootingRange(), 1);
        this.playSound(SoundEvents.GENERIC_EXPLODE, 0.1F, 1.2F);
        this.level.addFreshEntity(ironBall);
    }

    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
        return 0.25F;
    }


//

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance instance,
                                        @NotNull MobSpawnType type, @Nullable SpawnGroupData data,
                                        @Nullable CompoundTag tag) {
        ToyTankVariant variant = Util.getRandom(ToyTankVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(levelAccessor, instance, type, data, tag);
    }

    public ToyTankVariant getVariant() {
        return ToyTankVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    public void setVariant(ToyTankVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }
}
