package net.vashal.tistheseason.items.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class TTSFoods {

    public static final FoodProperties CANDY_CANE = (new FoodProperties.Builder()).nutrition(1).saturationMod(1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60, 0), 1.0F).alwaysEat().fast().build();

    public static final FoodProperties ENCHANTED_CANDY_CANE = (new FoodProperties.Builder()).nutrition(3).saturationMod(1.2F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2), 1.0F).alwaysEat().fast().build();

    public static final FoodProperties CARAMEL = (new FoodProperties.Builder()).nutrition(1).saturationMod(1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0F).alwaysEat().build();

    public static final FoodProperties LOLLIPOP = (new FoodProperties.Builder()).nutrition(2).saturationMod(1.0F)
            .alwaysEat().fast().build();
}
