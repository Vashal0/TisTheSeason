package net.vashal.tistheseason.block.entity;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PetRockModel extends AnimatedGeoModel<PetRockBlockEntity> {
    @Override
    public ResourceLocation getAnimationResource(PetRockBlockEntity entity) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "animations/pet_rock.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(PetRockBlockEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "geo/pet_rock.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PetRockBlockEntity entity) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/pet_rock_texture.png");
    }
}
