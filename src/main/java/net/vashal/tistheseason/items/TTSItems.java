package net.vashal.tistheseason.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.items.custom.ToyRobotItem;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.utils.TTSCreativeModeTab;


public class TTSItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TisTheSeason.MOD_ID);

    public static final RegistryObject<Item> TOYROBOT = ITEMS.register(ToyRobotConstants.NAME,
            () -> new ToyRobotItem(new Item.Properties().tab(TTSCreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}