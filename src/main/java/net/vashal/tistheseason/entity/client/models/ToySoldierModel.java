package net.vashal.tistheseason.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.client.renderers.ToySoldierRenderer;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.vashal.tistheseason.constants.ToySoldierConstants.ANIMATION_RESOURCE;
import static net.vashal.tistheseason.constants.ToySoldierConstants.MODEL_RESOURCE;

public class ToySoldierModel extends AnimatedGeoModel<ToySoldierEntity> {
    @Override
    public ResourceLocation getModelResource(ToySoldierEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, MODEL_RESOURCE);
    }

    @Override
    public ResourceLocation getTextureResource(ToySoldierEntity object) {
        return ToySoldierRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(ToySoldierEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ANIMATION_RESOURCE);
    }
}
