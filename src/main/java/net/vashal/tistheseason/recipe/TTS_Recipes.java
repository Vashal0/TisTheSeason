package net.vashal.tistheseason.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;

public class TTS_Recipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TisTheSeason.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ToyWorkbenchRecipe>> TOY_MAKING_SERIALIZER =
            SERIALIZERS.register("toy_making", () -> ToyWorkbenchRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
