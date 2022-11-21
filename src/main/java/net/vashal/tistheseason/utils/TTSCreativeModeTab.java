package net.vashal.tistheseason.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.items.TTSItems;

public class TTSCreativeModeTab {
    public static final CreativeModeTab TISTHESEASON_TAB = new CreativeModeTab("tistheseasontab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(TTSItems.TOYROBOT.get());
        }
    };
}
