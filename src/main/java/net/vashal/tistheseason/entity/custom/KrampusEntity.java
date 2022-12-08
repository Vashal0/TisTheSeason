package net.vashal.tistheseason.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.vashal.tistheseason.constants.KrampusConstants;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.ai.KrampusAttackGoal;
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

import javax.annotation.Nonnull;

public class KrampusEntity extends Monster implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public KrampusEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

    }

    @Override
    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.lerpX = pX;
        this.lerpY = pY;
        this.lerpZ = pZ;
        this.lerpYRot = pYaw;
        this.lerpXRot = pPitch;
        this.lerpSteps = pPosRotationIncrements * 2;
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, KrampusConstants.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, KrampusConstants.ATTACK_DAMAGE)
                .add(Attributes.ATTACK_SPEED, KrampusConstants.ATTACK_SPEED)
                .add(Attributes.MOVEMENT_SPEED, KrampusConstants.MOVEMENT_SPEED).build();
    }

    @Nullable
    public static KrampusEntity create(Level world) {
        return TTS_EntityTypes.KRAMPUS.get().create(world);
    }


    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<KrampusEntity> walkController = new AnimationController<>(this, "walkController", 0, this::walkPredicate);
        AnimationController<KrampusEntity> deathController = new AnimationController<>(this, "deathController", 0, this::deathPredicate);
        AnimationController<KrampusEntity> meleeController = new AnimationController<>(this, "meleeController", 0, this::meleePredicate);

        data.addAnimationController(walkController);
        data.addAnimationController(deathController);
        data.addAnimationController(meleeController);
    }

    private <E extends IAnimatable> PlayState walkPredicate(AnimationEvent<E> event) {
        if (event.isMoving() && this.entityData.get(ATTACK_STATE) != 1) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState deathPredicate(AnimationEvent<E> event) {
        if (deathTime > 0 && this.isDeadOrDying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.death", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState meleePredicate(AnimationEvent<E> event) {
        if (this.entityData.get(ATTACK_STATE) == 1 && !(this.dead || this.getHealth() < 0.01 || this.isDeadOrDying())) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.melee", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.65F;
    }



    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();
        if (randomsource.nextInt(1) == 0) {
            Villager villager = EntityType.VILLAGER.create(this.level);
            assert villager != null;
            villager.setBaby(true);
            villager.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            villager.finalizeSpawn(pLevel, pDifficulty, pReason, null, null);
            villager.startRiding(this);
        }
        return pSpawnData;
    }

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(KrampusEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("AttackingState", this.getAttackingState());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setAttackingState(tag.getInt("AttackingState"));
    }

    public void setAttackingState(int state) {
        this.entityData.set(ATTACK_STATE, state);
    }

    public int getAttackingState() {
        return this.entityData.get(ATTACK_STATE);
    }


    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    protected void tickDeath() { //delays death long enough to play custom animation
        ++this.deathTime;
        if (this.deathTime == 32 && !this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new KrampusAttackGoal(this, 1.0, 2));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        super.registerGoals();
    }


    //sounds without a special use case

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ANVIL_STEP, KrampusConstants.STEP_SOUND_VOLUME, KrampusConstants.STEP_SOUND_PITCH);
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return TTS_Sounds.TOY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return TTS_Sounds.TOY_DEATH.get();
    }

    protected float getSoundVolume() {
        return KrampusConstants.SOUND_VOLUME;
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

    protected float getStandingEyeHeight(@NotNull Pose pPose, @NotNull EntityDimensions pSize) {
        return 2.75F;
    }
}