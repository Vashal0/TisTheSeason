package net.vashal.tistheseason.constants;

public final class ToyTankConstants {

    private ToyTankConstants(){}

    public static final String NAME = "toy_tank";

    //Entity constants
    public static final String MODEL_RESOURCE = "geo/toy_tank.geo.json";
    public static final String ANIMATION_RESOURCE = "animations/toy_tank.animation.json";
    public static final String TEXTURE_RESOURCE = "textures/entity/toy_tank_texture.png";
    public static final float SHADOW_RADIUS = 0.3f;

    //Attributes constants
    public static final double MAX_HEALTH = 20.00;
    public static final float MOVEMENT_SPEED = .25f;
    public static final float WIDTH = 0.7f;
    public static final float HEIGHT = 0.3f;

    //Animation constants
    public static final String ANIMATION_IDLE = "animation.toy_tank.idle";
    public static final String ANIMATION_ATTACK = "animation.toy_tank.attack";

    //Sound constants
    public static final float SOUND_VOLUME = 0.8f;
    public static final float STEP_SOUND_VOLUME = 0.1f;
    public static final float STEP_SOUND_PITCH = 1.5f;
}

