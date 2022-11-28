package net.vashal.tistheseason.constants;

/*
 * Constants for Toy Robot classes
 */
public final class ToyRobotConstants {

    private ToyRobotConstants(){}

    public static final String NAME = "toyrobot";

    //Entity constants
    public static final String MODEL_RESOURCE = "geo/toy_robot.geo.json";
    public static final String ANIMATION_RESOURCE = "animations/toyrobot.animation.json";
    public static final float SHADOW_RADIUS = 0.3f;

    //Attributes constants
    public static final double MAX_HEALTH = 10.00;
    public static final float ATTACK_DAMAGE = 1.0f;
    public static final float ATTACK_SPEED = 1.0f;
    public static final float MOVEMENT_SPEED = .45f;
    public static final float WIDTH = 0.5f;
    public static final float HEIGHT = 1.0f;

    //Animation constants
    public static final String ANIMATION_WALK = "animation.toyrobot.walk";
    public static final String ANIMATION_IDLE = "animation.toyrobot.idle";
    public static final String ANIMATION_FEET_MOVEMENT = "animation.toyrobot.feetmovement";
    public static final String ANIMATION_WIND = "animation.toyrobot.wind";

    //Sound constants
    public static final float SOUND_VOLUME = 0.8f;
    public static final float STEP_SOUND_VOLUME = 0.1f;
    public static final float STEP_SOUND_PITCH = 1.5f;
}
