package net.vashal.tistheseason.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.projectile.IronBall;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IronBallModel extends AnimatedGeoModel<IronBall> {

    @Override
    public ResourceLocation getModelResource(IronBall object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "geo/iron_ball.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IronBall object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/projectiles/iron_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IronBall animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "animations/iron_ball.animation.json");
    }
}