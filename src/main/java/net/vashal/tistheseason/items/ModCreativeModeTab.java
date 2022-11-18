package net.vashal.tistheseason.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab TISTHESEASON_TAB = new CreativeModeTab("tistheseasontab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TOYROBOT.get());
        }
    };
}
