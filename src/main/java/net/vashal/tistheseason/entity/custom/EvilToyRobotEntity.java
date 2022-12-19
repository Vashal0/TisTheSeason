package net.vashal.tistheseason.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.entity.TTSEntityTypes;
import net.vashal.tistheseason.entity.ai.EvilToyRobotAttackGoal;
import net.vashal.tistheseason.sounds.TTSSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

import javax.annotation.Nonnull;

public class EvilToyRobotEntity extends Monster implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public EvilToyRobotEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ATTACK_DAMAGE, ToyRobotConstants.ATTACK_DAMAGE)
                .add(Attributes.ATTACK_SPEED, ToyRobotConstants.ATTACK_SPEED)
                .add(Attributes.ARMOR, ToyRobotConstants.ARMOR)
                .add(Attributes.MOVEMENT_SPEED, 0.35).build();
    }

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(EvilToyRobotEntity.class, EntityDataSerializers.INT);

    @Nullable
    public static EvilToyRobotEntity create(Level world) {
        return TTSEntityTypes.EVIL_ROBOT.get().create(world);
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

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
        playModSounds();
    }

    public void playModSounds() { //plays the walking sound much faster than normal
        if (this.level.isClientSide() && deathTime == 0) {
            if (tickCount % 3 == 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTSSounds.TOY_WALK.get(), this.getSoundSource(), 0.3f, 0.6f, true);
            }
            if (tickCount % 512 == 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTSSounds.TOY_GEARS.get(), this.getSoundSource(), 0.4f, 1.2f, true);
            }
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


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EvilToyRobotAttackGoal(this, 1));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new FloatGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EvilToyRobotEntity> windController = new AnimationController<>(this, "windController", 0, this::windPredicate); //spins the wind when active
        AnimationController<EvilToyRobotEntity> feetController = new AnimationController<>(this, "feetController", 0, this::feetPredicate); //moves the feet when active
        AnimationController<EvilToyRobotEntity> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        AnimationController<EvilToyRobotEntity> deathController = new AnimationController<>(this, "deathController", 0, this::deathPredicate);
        AnimationController<EvilToyRobotEntity> meleeController = new AnimationController<>(this, "meleeController", 0, this::meleePredicate);
        data.addAnimationController(windController);
        data.addAnimationController(feetController);
        idleController.registerSoundListener(this::soundListenerIdle);
        data.addAnimationController(idleController);
        data.addAnimationController(deathController);
        data.addAnimationController(meleeController);
    }

    private <E extends IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
        if (deathTime == 0 && this.entityData.get(ATTACK_STATE) == 0) {
                if (event.isMoving()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_WALK, ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_IDLE, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }


    private void soundListenerIdle(SoundKeyframeEvent<EvilToyRobotEntity> event) {
        EvilToyRobotEntity robot = event.getEntity();
        if (this.level.isClientSide()) {
            if (event.sound.equals("toyambient")) {
                this.level.playLocalSound(robot.getX(), robot.getY(), robot.getZ(), TTSSounds.TOY_AMBIENT.get(), robot.getSoundSource(), 1f, 1f, true);
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
        if (this.entityData.get(ATTACK_STATE) == 1 && deathTime == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.melee", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState windPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_WIND, ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState feetPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_FEET_MOVEMENT, ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }


    //sounds without a special use case

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.ANVIL_STEP, ToyRobotConstants.STEP_SOUND_VOLUME, ToyRobotConstants.STEP_SOUND_PITCH);
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return TTSSounds.TOY_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return TTSSounds.TOY_DEATH.get();
    }

    protected float getSoundVolume() {
        return ToyRobotConstants.SOUND_VOLUME;
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
        return 0.75F;
    }

    //
}