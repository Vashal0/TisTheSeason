package net.vashal.tistheseason.entity.custom;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.entity.TTSEntityTypes;
import net.vashal.tistheseason.sounds.TTSSounds;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

//TODO add melee attack animation
//TODO find a way to split parts of the model for 'upgrading'
//TODO think of uses for the robot for the player to play with
//TODO add 3-4 variant colors

//TODO placing this here for now but it's not robot specific, add toy workbench block which allows ability to 'customize' toys
//TODO add new toys
//TODO clean up creative tab
//TODO look into how to add custom projectiles
//TODO Add other holiday items from the google doc which are not toys, especially the hobby horse
//TODO better understand curio integration

public class ToyRobotEntity extends WindUpToys implements IAnimatable {

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
    public void playModSounds() {
        super.playModSounds();
        if (tickCount % 512 == 0) {
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), TTSSounds.TOY_GEARS.get(), this.getSoundSource(), 0.6f, 1.2f, true);
        }
    }

    public void registerControllers(AnimationData data) {
        super.registerControllers(data);
        AnimationController<ToyRobotEntity> idleController = new AnimationController<>(this, "idleController", 0, this::idlePredicate);
        AnimationController<ToyRobotEntity> deathController = new AnimationController<>(this, "deathController", 0, this::deathPredicate);
        AnimationController<ToyRobotEntity> meleeController = new AnimationController<>(this, "meleeController", 0, this::meleePredicate);
        idleController.registerSoundListener(this::soundListenerIdle);
        data.addAnimationController(idleController); //plays both the 'walking' and idle animations currently
        data.addAnimationController(deathController); //plays a death animation
        data.addAnimationController(meleeController); //plays a melee attack animation
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
        if (this.swinging && deathTime == 0 && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.toyrobot.melee", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }
}

