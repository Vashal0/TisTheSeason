package net.vashal.tistheseason.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import static net.vashal.tistheseason.constants.ToyRobotConstants.*;

public class ToyRobotModel extends AnimatedGeoModel<ToyRobotEntity> {
    @Override
    public ResourceLocation getModelResource(ToyRobotEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, MODEL_RESOURCE);
    }

    @Override
    public ResourceLocation getTextureResource(ToyRobotEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, TEXTURE_RESOURCE);
    }

    @Override
    public ResourceLocation getAnimationResource(ToyRobotEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ANIMATION_RESOURCE);
    }
}
