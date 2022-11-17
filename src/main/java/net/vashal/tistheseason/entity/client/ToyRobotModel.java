package net.vashal.tistheseason.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ToyRobotModel extends AnimatedGeoModel<ToyRobotEntity> {
    @Override
    public ResourceLocation getModelResource(ToyRobotEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "geo/toyrobot.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ToyRobotEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ToyRobotEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "animations/toyrobot.animation.json");
    }
}
