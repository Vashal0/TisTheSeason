package net.vashal.tistheseason.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.custom.EvilToyTankEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static net.vashal.tistheseason.constants.ToyTankConstants.*;

public class ToyTankModel extends AnimatedGeoModel<EvilToyTankEntity> {

    @Override
    public ResourceLocation getModelResource(EvilToyTankEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, MODEL_RESOURCE);
    }

    @Override
    public ResourceLocation getTextureResource(EvilToyTankEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, TEXTURE_RESOURCE);
    }

    @Override
    public ResourceLocation getAnimationResource(EvilToyTankEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ANIMATION_RESOURCE);
    }
}
