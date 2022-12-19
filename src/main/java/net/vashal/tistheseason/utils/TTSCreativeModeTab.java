package net.vashal.tistheseason.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.items.TTSItems;
import org.jetbrains.annotations.NotNull;

public class TTSCreativeModeTab {
    public static final CreativeModeTab TISTHESEASON_TAB = new CreativeModeTab("tistheseasontab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(TTSItems.HAT.get());
        }
    };
}
