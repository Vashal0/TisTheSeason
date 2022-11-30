package net.vashal.tistheseason.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.items.custom.ToyRobotItem;
import net.vashal.tistheseason.items.custom.curios.HobbyHorseItem;
import net.vashal.tistheseason.utils.TTS_CreativeModeTab;


public class TTS_Items {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TisTheSeason.MOD_ID);

    public static final RegistryObject<Item> TOYROBOT = ITEMS.register("toy_robot_item",
            () -> new ToyRobotItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> HOBBY_HORSE = ITEMS.register("hobby_horse",
            () -> new HobbyHorseItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> IRON_BALL_ITEM = ITEMS.register("iron_ball_item",
            () -> new IronBallItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(64)));

    public static final RegistryObject<Item> TOY_ROBOT_SPAWN_EGG = ITEMS.register("toy_robot_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOYROBOT, 0x22b341, 0x19732e,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
