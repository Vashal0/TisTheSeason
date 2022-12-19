package net.vashal.tistheseason.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.vashal.tistheseason.constants.ToyTankConstants;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.ai.EvilToyTankAttackGoal;
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

public class EvilToyTankEntity extends Monster implements IAnimatable, IAnimationTickable, RangedAttackMob {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public EvilToyTankEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

    }

    public static AttributeSupplier setAttributes() {

        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();
    }

    @Nullable
    public static EvilToyTankEntity create(Level world) {
        return TTS_EntityTypes.EVIL_TOY_TANK.get().create(world);
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(EvilToyTankEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(ToySoldierEntity.class, EntityDataSerializers.INT);


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
        this.entityData.define(ATTACK_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
        tag.putInt("AttackState", this.getAttackState());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
        this.setAttackState(tag.getInt("AttackState"));
    }

    public void setAttackState(int position) {
        this.entityData.set(ATTACK_STATE, position);
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
            this.remove(Entity.RemovalReason.KILLED);
        }
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EvilToyTankAttackGoal(this, 1.0D, 40, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EvilToyTankEntity> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        AnimationController<EvilToyTankEntity> attackController = new AnimationController<>(this, "attackController", 0, this::attackPredicate);
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
        ironBall.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, 1);
        this.playSound(SoundEvents.GENERIC_EXPLODE, 0.1F, 1.2F);
        this.level.addFreshEntity(ironBall);
    }

    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
        return 0.25F;
    }

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

//

