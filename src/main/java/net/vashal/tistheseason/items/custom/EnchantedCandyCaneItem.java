package net.vashal.tistheseason.items.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantedCandyCaneItem extends Item {
    public EnchantedCandyCaneItem(Item.Properties pProperties) {
        super(pProperties);
    }

    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }
}