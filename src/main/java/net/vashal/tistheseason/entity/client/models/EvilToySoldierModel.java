package net.vashal.tistheseason.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.client.renderers.ToySoldierRenderer;
import net.vashal.tistheseason.entity.custom.EvilToySoldierEntity;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.vashal.tistheseason.constants.ToySoldierConstants.ANIMATION_RESOURCE;
import static net.vashal.tistheseason.constants.ToySoldierConstants.MODEL_RESOURCE;

public class EvilToySoldierModel extends AnimatedGeoModel<EvilToySoldierEntity> {
    @Override
    public ResourceLocation getModelResource(EvilToySoldierEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, MODEL_RESOURCE);
    }

    @Override
    public ResourceLocation getTextureResource(EvilToySoldierEntity object) {
        return ToySoldierRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(EvilToySoldierEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ANIMATION_RESOURCE);
    }
}
