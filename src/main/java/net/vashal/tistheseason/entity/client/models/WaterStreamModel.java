package net.vashal.tistheseason.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.projectile.WaterStream;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WaterStreamModel extends AnimatedGeoModel<WaterStream> {

    @Override
    public ResourceLocation getModelResource(WaterStream object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "geo/water_stream.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WaterStream object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/projectiles/water_stream_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WaterStream animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "animations/water.animation.json");
    }
}