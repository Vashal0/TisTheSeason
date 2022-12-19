package net.vashal.tistheseason.block.entity;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StockingModel extends AnimatedGeoModel<StockingBlockEntity> {
    @Override
    public ResourceLocation getAnimationResource(StockingBlockEntity entity) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "animations/stocking.animation.json");

    }

    @Override
    public ResourceLocation getModelResource(StockingBlockEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "geo/stocking.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(StockingBlockEntity entity) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/block/stocking_texture.png");
    }
}
