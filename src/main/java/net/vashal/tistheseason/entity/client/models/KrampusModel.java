package net.vashal.tistheseason.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.custom.KrampusEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.vashal.tistheseason.constants.KrampusConstants.*;

public class KrampusModel extends AnimatedGeoModel<KrampusEntity> {
    @Override
    public ResourceLocation getModelResource(KrampusEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, MODEL_RESOURCE);
    }

    @Override
    public ResourceLocation getTextureResource(KrampusEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, TEXTURE_RESOURCE);
    }

    @Override
    public ResourceLocation getAnimationResource(KrampusEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ANIMATION_RESOURCE);
    }
}

