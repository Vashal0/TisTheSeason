package net.vashal.tistheseason.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ToySoldierModel extends AnimatedGeoModel<ToySoldierEntity> {
    @Override
    public ResourceLocation getModelResource(ToySoldierEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "geo/toysoldier.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ToySoldierEntity object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toysoldier_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ToySoldierEntity animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "animations/toysoldier.animation.json");
    }
}
