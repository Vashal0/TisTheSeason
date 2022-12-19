package net.vashal.tistheseason.items.custom.client;

import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.items.custom.PopGunItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PopGunModel extends AnimatedGeoModel<PopGunItem> {
    @Override
    public ResourceLocation getModelResource(PopGunItem object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "geo/pop_gun.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PopGunItem object) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/item/pop_gun_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PopGunItem animatable) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "animations/pop_gun.animation.json");
    }
}