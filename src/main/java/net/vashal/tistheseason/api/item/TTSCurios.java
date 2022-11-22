package net.vashal.tistheseason.api.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.utils.TTS_CreativeModeTab;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public abstract class TTSCurios extends Item implements ICurioItem {

    public TTSCurios() {
        this(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1));
    }

    public TTSCurios(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}