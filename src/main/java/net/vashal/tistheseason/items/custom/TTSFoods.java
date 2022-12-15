package net.vashal.tistheseason.items.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class TTSFoods {

    public static final FoodProperties CANDY_CANE = (new FoodProperties.Builder()).nutrition(2).saturationMod(1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1), 1.0F).alwaysEat().fast().build();
}
