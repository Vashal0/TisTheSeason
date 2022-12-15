package net.vashal.tistheseason.constants;

public final class ToySoldierConstants {

    private ToySoldierConstants(){}

    public static final String MODEL_RESOURCE = "geo/toysoldier.geo.json";
    public static final String ANIMATION_RESOURCE = "animations/toysoldier.animation.json";
    public static final float SHADOW_RADIUS = 0.3f;

    public static final String NAME = "toysoldier";

    //Attributes constants
    public static final double MAX_HEALTH = 20.00;
    public static final float MOVEMENT_SPEED = .45f;
    public static final float WIDTH = 0.5f;
    public static final float HEIGHT = 1.0f;

    //Animation constants
    public static final String ANIMATION_IDLE = "animation.toydrummer.idle";
    public static final String ANIMATION_FEET_MOVEMENT = "animation.toydrummer.feet";
    public static final String ANIMATION_WIND = "animation.toydrummer.wind";
    public static final String ANIMATION_DEATH = "animation.toydrummer.death";

    //Sound constants
    public static final float SOUND_VOLUME = 0.8f;
    public static final float STEP_SOUND_VOLUME = 0.1f;
    public static final float STEP_SOUND_PITCH = 1.5f;
}
