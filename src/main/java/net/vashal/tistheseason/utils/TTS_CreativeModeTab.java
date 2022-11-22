package net.vashal.tistheseason.utils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.items.TTS_Items;
import org.jetbrains.annotations.NotNull;

public class TTS_CreativeModeTab {
    public static final CreativeModeTab TISTHESEASON_TAB = new CreativeModeTab("tistheseasontab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(TTS_Items.TOYROBOT.get());
        }
    };
}
