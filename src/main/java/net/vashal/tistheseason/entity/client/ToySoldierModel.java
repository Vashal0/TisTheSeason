package net.vashal.tistheseason.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.vashal.tistheseason.constants.ToySoldierConstants.*;

public class ToySoldierModel extends AnimatedGeoModel<ToySoldierEntity> {
    @Override
    public ResourceLocation getModelResource(ToySoldierEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, MODEL_RESOURCE);
    }

    @Override
    public ResourceLocation getTextureResource(ToySoldierEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, TEXTURE_RESOURCE);
    }

    @Override
    public ResourceLocation getAnimationResource(ToySoldierEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ANIMATION_RESOURCE);
    }
}
