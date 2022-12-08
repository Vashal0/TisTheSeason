package net.vashal.tistheseason.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.client.renderers.ToyRobotRenderer;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.vashal.tistheseason.constants.ToyRobotConstants.ANIMATION_RESOURCE;
import static net.vashal.tistheseason.constants.ToyRobotConstants.MODEL_RESOURCE;

public class ToyRobotModel extends AnimatedGeoModel<ToyRobotEntity> {

    @Override
    public ResourceLocation getModelResource(ToyRobotEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, MODEL_RESOURCE);
    }

    @Override
    public ResourceLocation getTextureResource(ToyRobotEntity object) {
        return ToyRobotRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(ToyRobotEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ANIMATION_RESOURCE);
    }
}
