package net.vashal.tistheseason.recipe;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.vashal.tistheseason.TisTheSeason;
import org.jetbrains.annotations.NotNull;


public class ToyWorkbenchRecipe implements Recipe<Container> {
    private final Ingredient toy;
    private final Ingredient upgrade;
    private final ItemStack result;
    private final ResourceLocation id;


    public ToyWorkbenchRecipe(ResourceLocation pId, Ingredient pBase, Ingredient pAddition, ItemStack pResult) {
        this.id = pId;
        this.toy = pBase;
        this.upgrade = pAddition;
        this.result = pResult;
    }

    @Override
    public boolean matches(Container pInv, @NotNull Level pLevel) {
        return this.toy.test(pInv.getItem(0)) && this.upgrade.test(pInv.getItem(1));
    }

    @Override
    public @NotNull ItemStack assemble (Container container) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = container.getItem(0).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return result;
    }

    public @NotNull Ingredient getToy() {
        return toy;
    }

    public @NotNull Ingredient getUpgrade() {
        return upgrade;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ToyWorkbenchRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "toy_making";
    }

    public static class Serializer implements RecipeSerializer<ToyWorkbenchRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(TisTheSeason.MOD_ID, "toy_making");

        public @NotNull ToyWorkbenchRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pJson) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "toy"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(pJson, "upgrade"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pJson, "result"));
            return new ToyWorkbenchRecipe(pRecipeId, ingredient, ingredient1, itemstack);
        }

        @Override
        public ToyWorkbenchRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(pBuffer);
            ItemStack itemstack = pBuffer.readItem();
            return new ToyWorkbenchRecipe(pRecipeId, ingredient, ingredient1, itemstack);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, ToyWorkbenchRecipe pRecipe) {
            pRecipe.toy.toNetwork(pBuffer);
            pRecipe.upgrade.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
