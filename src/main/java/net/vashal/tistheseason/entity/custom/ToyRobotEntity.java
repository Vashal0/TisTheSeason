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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.vashal.tistheseason.entity.TTSEntityTypes;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.entity.ai.ToyRobotFollow;
import net.vashal.tistheseason.sounds.TTSSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.UUID;

//TODO add a new melee attack goal so he doesn't move while deactivated
//TODO add melee attack animation
//TODO find a way to split parts of the model for 'upgrading'
//TODO think of uses for the robot for the player to play with
//TODO add 3-4 variant colors

//TODO placing this here for now but it's not robot specific, add toy workbench block which allows ability to 'customize' toys
//TODO generify the toys so we can more easily add new toys
//TODO add new toys
//TODO clean up creative tab
//TODO refactor
//TODO look into how to add custom projectiles
//TODO Add other holiday items from the google doc which are not toys, especially the hobby horse
//TODO better understand curio integration

public class ToyRobotEntity extends TamableAnimal implements NeutralMob, IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public ToyRobotEntity(EntityType<? extends ToyRobotEntity> entityType, Level level) {
        super(entityType, level);

    }


    public static AttributeSupplier setAttributes() {

        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, ToyRobotConstants.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, ToyRobotConstants.ATTACK_DAMAGE)
                .add(Attributes.ATTACK_SPEED, ToyRobotConstants.ATTACK_SPEED)
                .add(Attributes.MOVEMENT_SPEED, ToyRobotConstants.MOVEMENT_SPEED).build();
    }

    @Nullable
    public static ToyRobotEntity create(Level world, double x, double y, double z) {
        ToyRobotEntity toyRobot = TTSEntityTypes.TOYROBOT.get().create(world);
        if (toyRobot == null) {
            return null;
        }
        toyRobot.setPos(x, y, z);
        toyRobot.xo = x;
        toyRobot.yo = y;
        toyRobot.zo = z;
        return toyRobot;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
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

    public void tick() {
        super.tick();
        if (getActivatedTicks() > 0) {
            setTickCount(getActivatedTicks() - 1);
            playModSounds();
        }
        if (getActivatedTicks() == 0) {
            setActivationStatus(false);
        }
    }

    public void playModSounds() {
        if (this.level.isClientSide() && deathTime == 0) {
            if (tickCount % 3 == 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTSSounds.TOY_WALK.get(), this.getSoundSource(), 0.3f, 0.6f, true);
            }
            if (tickCount % 512 == 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTSSounds.TOY_GEARS.get(), this.getSoundSource(), 0.6f, 1.2f, true);
            }
        }
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new ToyRobotFollow(this, 1.0f, 2.0f, 4.0f));
        this.goalSelector.addGoal(3, new FloatGoal(this));


        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

    }

    private <E extends IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {

        if (deathTime == 0) {
            if (getActivatedStatus()) {
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

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "windController",
                0, this::windPredicate));

        AnimationController idleController = new AnimationController(this, "idleController", 0, this::idlePredicate);
        idleController.registerSoundListener(this::soundListenerIdle);
        data.addAnimationController(idleController);

        AnimationController deactivatedController = new AnimationController(this, "deactivatedController", 0, this::deactivatedPredicate);
        data.addAnimationController(deactivatedController);

        AnimationController deathController = new AnimationController(this, "deathController", 0, this::deathPredicate);
        data.addAnimationController(deathController);

        AnimationController feetController = new AnimationController(this, "feetController", 0, this::feetPredicate);
        data.addAnimationController(feetController);
    }


    private void soundListenerIdle(SoundKeyframeEvent<ToyRobotEntity> event) {
        ToyRobotEntity robot = event.getEntity();
        if (this.level.isClientSide()) {
            if (event.sound.equals("toyambient")) {
                this.level.playLocalSound(robot.getX(), robot.getY(), robot.getZ(), TTSSounds.TOY_AMBIENT.get(), robot.getSoundSource(), 1f, 1f, true);
            }
        }
    }

    private PlayState deathPredicate(AnimationEvent event) {
        if (deathTime > 0) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.death", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }

    public PlayState deactivatedPredicate(AnimationEvent event) {
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

    private PlayState feetPredicate(AnimationEvent event) {
        if (deathTime == 0) {
            if (getActivatedStatus()) {
                this.playSound(TTSSounds.TOY_WALK.get());
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_FEET_MOVEMENT, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }

    private PlayState windPredicate(AnimationEvent event) {

        if (deathTime == 0) {
            if (getActivatedStatus()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToyRobotConstants.ANIMATION_WIND, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
        }
        return PlayState.STOP;
    }


    private static final EntityDataAccessor<Integer> WIND_POSITION = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ACTIVATED_TICKS = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_ACTIVATED = SynchedEntityData.defineId(ToyRobotEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WIND_POSITION, 0);
        this.entityData.define(ACTIVATED_TICKS, 0);
        this.entityData.define(IS_ACTIVATED, false);
    }

    private void setWind(int position) {
        this.entityData.set(WIND_POSITION, position);
    }

    private int getWindCount() {
        return this.entityData.get(WIND_POSITION);
    }

    private void setActivationStatus(boolean bool) {
        this.entityData.set(IS_ACTIVATED, bool);
    }

    public boolean getActivatedStatus() {
        return this.entityData.get(IS_ACTIVATED);
    }

    private int getActivatedTicks() {
        return this.entityData.get(ACTIVATED_TICKS);
    }

    private void setTickCount(int count) {
        this.entityData.set(ACTIVATED_TICKS, count);
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

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime == 60 && !this.level.isClientSide()) {
            this.level.broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ANVIL_STEP, ToyRobotConstants.STEP_SOUND_VOLUME, ToyRobotConstants.STEP_SOUND_PITCH);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return TTSSounds.TOY_HURT.get(); }

    protected SoundEvent getDeathSound() { return TTSSounds.TOY_DEATH.get(); }

    protected float getSoundVolume() { return ToyRobotConstants.SOUND_VOLUME; }

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

