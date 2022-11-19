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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.vashal.tistheseason.entity.ModEntityTypes;
import net.vashal.tistheseason.sounds.ModSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import java.util.UUID;


public class ToyRobotEntity extends TamableAnimal implements NeutralMob, IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public ToyRobotEntity(EntityType<? extends ToyRobotEntity> entityType, Level level) {
        super(ModEntityTypes.TOYROBOT.get(), level);
    }


    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.00)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.45f).build();
    }

    public static ToyRobotEntity of(Level level, Player player) {
        ToyRobotEntity toReturn = new ToyRobotEntity(ModEntityTypes.TOYROBOT.get(), level);
        toReturn.tame(player);
        return toReturn;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.level.isClientSide && hand == InteractionHand.OFF_HAND) return InteractionResult.PASS;
        if (this.isTame() && this.isOwnedBy(player)) {
            if (getWindCount() < 9 && !getActivatedStatus()) {
                this.saveWind(getWindCount() + 1);
                return InteractionResult.SUCCESS;
            } else if (getWindCount() == 9 && !getActivatedStatus()) {
                saveActivationStatus(true);
                saveWind(getWindCount() - 9);
                saveTickCount(600);
                this.setNoAi(false);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }


    @Override
    public void tick() {
        super.tick();
        if (getActivatedTicks() > 0) {
            saveTickCount(getActivatedTicks() - 1);
        }
        if (getActivatedTicks() == 0) {
            saveActivationStatus(false);
            this.setNoAi(true);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0d, 2.0f, 10.0f, false));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0d));
        this.goalSelector.addGoal(6, new FloatGoal(this));


        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (getActivatedStatus()) {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.walk", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "windController",
                0, this::windPredicate));
        data.addAnimationController(new AnimationController(this, "feetController",
                0, this::feetPredicate));
        data.addAnimationController(new AnimationController(this, "deactivatedController",
                0, this::deactivatedPredicate));
    }



    public PlayState deactivatedPredicate(AnimationEvent event) {
        String animation = "animation.toyrobot.deactivated";
        if (!getActivatedStatus() && getWindCount() >= 0) {
            animation = animation + getWindCount();
            event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }



    private PlayState feetPredicate(AnimationEvent event) {
        if (getActivatedStatus()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.feetmovement", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private PlayState windPredicate(AnimationEvent event) {
        if (getActivatedStatus()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.wind", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
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

    private void saveWind(int position) {
        this.entityData.set(WIND_POSITION, position);
    }

    private int getWindCount() {
        int windCount = this.entityData.get(WIND_POSITION);
        return windCount;
    }

    private void saveActivationStatus(boolean bool) {
        this.entityData.set(IS_ACTIVATED, bool);
    }

    private boolean getActivatedStatus() {
        return this.entityData.get(IS_ACTIVATED);
    }

    private int getActivatedTicks() {
        int tickCount = this.entityData.get(ACTIVATED_TICKS);
        return tickCount;
    }

    private void saveTickCount(int count) {
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
        this.saveWind(tag.getInt("WindPosition"));
        this.saveTickCount(tag.getInt("ActivatedTicks"));
        this.saveActivationStatus(tag.getBoolean("isActivated"));
    }



    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.IRON_GOLEM_STEP, 0.15f, 1.0f);
    }

    protected SoundEvent getAmbientSound() { return ModSounds.TOYAMBIENT.get(); }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return ModSounds.TOYHURT.get(); }

    protected SoundEvent getDeathSound() { return ModSounds.TOYDEATH.get(); }

    protected float getSoundVolume() { return 0.8f; }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
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
