package net.vashal.tistheseason.integration;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sun.jna.platform.unix.X11;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.TTS_Blocks;
import net.vashal.tistheseason.recipe.ToyWorkbenchRecipe;

public class ToyWorkbenchRecipeCategory implements IRecipeCategory<ToyWorkbenchRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(TisTheSeason.MOD_ID, "toy_making");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(TisTheSeason.MOD_ID, "textures/gui/toy_workbench_gui_jei.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;


    public ToyWorkbenchRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(TTS_Blocks.TOY_WORKBENCH.get()));
        this.arrow = helper.drawableBuilder(TEXTURE, 176, 1, 26, 26).buildAnimated(80, IDrawableAnimated.StartDirection.LEFT, false);

    }

    @Override
    public void draw(ToyWorkbenchRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.arrow.draw(stack, 79, 31);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public RecipeType<ToyWorkbenchRecipe> getRecipeType() {
        return TisJeiPlugin.TOY_MAKING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Toy Workbench");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ToyWorkbenchRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 21, 35).addIngredients(recipe.getToy());
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 35).addIngredients(recipe.getUpgrade());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 36).addItemStack(recipe.getResultItem());
    }
}
