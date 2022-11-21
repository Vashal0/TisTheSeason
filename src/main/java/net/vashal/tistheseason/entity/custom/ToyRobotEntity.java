package net.vashal.tistheseason.entity.custom;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.level.Level;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.entity.TTSEntityTypes;
import net.vashal.tistheseason.entity.ai.ToyRobotFollow;
import net.vashal.tistheseason.sounds.TTSSounds;
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
//TODO look into how to ad
// d custom projectiles
//TODO Add other holiday items from the google doc which are not toys, especially the hobby horse
//TODO better understand curio integration

public class ToyRobotEntity extends WindUpToys implements NeutralMob, IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public ToyRobotEntity(EntityType<? extends ToyRobotEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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

    public void registerControllers(AnimationData data) {
        AnimationController<ToyRobotEntity> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        idleController.registerSoundListener(this::soundListenerIdle);
        data.addAnimationController(idleController); //plays both the 'walking' and idle animations currently
        AnimationController<ToyRobotEntity> deathController = new AnimationController<>(this, "deathController", 0, this::deathPredicate);
        data.addAnimationController(deathController); //plays a death timer
        AnimationController<ToyRobotEntity> windController = new AnimationController<>(this, "windController", 0, this::windPredicate);
        data.addAnimationController(windController); //spins the wind when active
        AnimationController<ToyRobotEntity> feetController = new AnimationController<>(this, "feetController", 0, this::feetPredicate);
        data.addAnimationController(feetController); //moves the feet when active
        AnimationController<ToyRobotEntity> deactivatedController = new AnimationController<>(this, "deactivatedController", 0, this::deactivatedPredicate);
        data.addAnimationController(deactivatedController); //10 frames of different wind positions while 'deactivated'
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

    private void soundListenerIdle(SoundKeyframeEvent<ToyRobotEntity> event) {
        ToyRobotEntity robot = event.getEntity();
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
                this.playSound(TTSSounds.TOY_WALK.get());
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

}

