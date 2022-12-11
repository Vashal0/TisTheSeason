package net.vashal.tistheseason.block.entity;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class RockingHorseModel extends AnimatedGeoModel<RockingHorseBlockEntity> {
    @Override
    public ResourceLocation getAnimationResource(RockingHorseBlockEntity entity) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "animations/rocking_horse.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(RockingHorseBlockEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "geo/rocking_horse.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RockingHorseBlockEntity entity) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/rocking_horse_texture.png");
    }
}
