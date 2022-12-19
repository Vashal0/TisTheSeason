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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.constants.ToySoldierConstants;
import net.vashal.tistheseason.entity.TTSEntityTypes;
import net.vashal.tistheseason.entity.variant.ToySoldierVariant;
import net.vashal.tistheseason.sounds.TTSSounds;
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
import java.util.List;

public class EvilToySoldierEntity extends Monster implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(EvilToySoldierEntity.class, EntityDataSerializers.INT);

    public EvilToySoldierEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 4)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.MOVEMENT_SPEED, 0.3).build();
    }

    @Nullable
    public static EvilToySoldierEntity create(Level world) {
        return TTSEntityTypes.EVIL_TOY_SOLDIER.get().create(world);
    }


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }


    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
    }


    @Override
    public void tick() {
        super.tick();
        BlockPos pos = this.getOnPos();
        if (tickCount % 20 == 0) {
            if (getTypeVariant() == 1) {
                applyEffects(level, pos, MobEffects.MOVEMENT_SPEED);
                applyEffects(level, pos, MobEffects.MOVEMENT_SLOWDOWN);
            } else if (getTypeVariant() == 0) {
                applyEffects(level, pos, MobEffects.REGENERATION);
                applyEffects(level, pos, MobEffects.DAMAGE_RESISTANCE);
            }
        }
        playModSounds();
    }

    public void playModSounds() { //plays the walking sound much faster than normal
        if (this.level.isClientSide() && deathTime == 0) {
            if (tickCount % 3 == 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTSSounds.TOY_WALK.get(), this.getSoundSource(), 0.025f, 0.6f, true);
            }
            if (tickCount % 6 == 0) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTSSounds.DRUM.get(), this.getSoundSource(), 0.5f, 2f, true);
            }
        }
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
        this.goalSelector.addGoal(1, new FollowMobGoal(this, 1, 1, 5));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, KrampusEntity.class, true));
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<EvilToySoldierEntity> windController = new AnimationController<>(this, "windController", 0, this::windPredicate); //spins the wind when active
        AnimationController<EvilToySoldierEntity> feetController = new AnimationController<>(this, "feetController", 0, this::feetPredicate); //moves the feet when active
        AnimationController<EvilToySoldierEntity> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        AnimationController<EvilToySoldierEntity> deathController = new AnimationController<>(this, "deathController", 0, this::deathPredicate);
        data.addAnimationController(windController);
        data.addAnimationController(feetController);
        data.addAnimationController(idleController); //plays both the 'walking' and idle animations currently
        data.addAnimationController(deathController); //plays a death animation
    }

    private <E extends IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToySoldierConstants.ANIMATION_IDLE, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
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
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToySoldierConstants.ANIMATION_WIND, ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState feetPredicate(AnimationEvent<E> event) {
        if (deathTime == 0) {
                this.playSound(TTSSounds.TOY_WALK.get());
                event.getController().setAnimation(new AnimationBuilder().addAnimation(ToySoldierConstants.ANIMATION_FEET_MOVEMENT, ILoopType.EDefaultLoopTypes.LOOP));
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

    private static void applyEffects(Level pLevel, BlockPos pPos, @Nullable MobEffect effect) {
        if (!pLevel.isClientSide && effect != null) {
            AABB aabb = (new AABB(pPos)).inflate(8);
            if (effect == MobEffects.MOVEMENT_SLOWDOWN) {
                List<Player> list = pLevel.getEntitiesOfClass(Player.class, aabb);
                for (Player player : list) {
                    player.addEffect(new MobEffectInstance(effect, 100, 1, true, true));
                }
            } else {
                List<Monster> list = pLevel.getEntitiesOfClass(Monster.class, aabb);
                for (Monster monster : list) {
                    monster.addEffect(new MobEffectInstance(effect, 60, 1, true, true));
                }
            }
        }
    }
    //

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
}