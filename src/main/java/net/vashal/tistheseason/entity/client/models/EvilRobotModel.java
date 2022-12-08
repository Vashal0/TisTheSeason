package net.vashal.tistheseason.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.entity.custom.EvilToyRobotEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EvilRobotModel extends AnimatedGeoModel<EvilToyRobotEntity> {

    @Override
    public ResourceLocation getModelResource(EvilToyRobotEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ToyRobotConstants.MODEL_RESOURCE);
    }

    @Override
    public ResourceLocation getTextureResource(EvilToyRobotEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_green.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EvilToyRobotEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ToyRobotConstants.ANIMATION_RESOURCE);
    }
}
