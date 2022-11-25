package net.vashal.tistheseason.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.recipe.ToyWorkbenchRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class TisJeiPlugin implements IModPlugin {
    public static RecipeType<ToyWorkbenchRecipe> TOY_MAKING_TYPE =
            new RecipeType<>(ToyWorkbenchRecipeCategory.UID, ToyWorkbenchRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(TisTheSeason.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                ToyWorkbenchRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<ToyWorkbenchRecipe> recipeToyMaking = rm.getAllRecipesFor(ToyWorkbenchRecipe.Type.INSTANCE);
        registration.addRecipes(TOY_MAKING_TYPE, recipeToyMaking);
    }
}
