package net.vashal.tistheseason.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.items.custom.*;
import net.vashal.tistheseason.items.custom.curios.*;
import net.vashal.tistheseason.utils.TTS_CreativeModeTab;


public class TTS_Items {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TisTheSeason.MOD_ID);

    public static final RegistryObject<Item> TOYROBOT = ITEMS.register("toy_robot_item",
            () -> new ToyRobotItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> HOBBY_HORSE = ITEMS.register("hobby_horse",
            () -> new HobbyHorseItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MARI_HOBBY_HORSE = ITEMS.register("mari_hobby_horse",
            () -> new MariHobbyHorseItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> POWER_GLOVE = ITEMS.register("power_glove_item",
            () -> new PowerGloveItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SWEATER = ITEMS.register("sweater_item",
            () -> new SweaterItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> HAT = ITEMS.register("santa_hat_item",
            () -> new HatItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> REINDEER_SLIPPERS = ITEMS.register("reindeer_slippers",
            () -> new ReindeerSlipperItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SNOWMAN_SLIPPERS = ITEMS.register("snowman_slippers",
            () -> new SnowmanSlipperItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> IRON_BALL_ITEM = ITEMS.register("iron_ball_item",
            () -> new IronBallItem(new Item.Properties()));

    public static final RegistryObject<Item> WATER_STREAM = ITEMS.register("water_stream",
            () -> new WaterStreamItem(new Item.Properties()));

    public static final RegistryObject<Item> WOODEN_MALLET = ITEMS.register("wooden_mallet",
            () -> new WoodenMalletItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SUPER_SOAKER = ITEMS.register("super_soaker",
            () -> new SuperSoakerItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> TOY_ROBOT_SPAWN_EGG = ITEMS.register("toy_robot_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOYROBOT, 0x0096ff, 0xc70039,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> TOY_SOLDIER_SPAWN_EGG = ITEMS.register("toy_soldier_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOYSOLDIER, 0xff5733, 0xffd700,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> TOY_TANK_SPAWN_EGG = ITEMS.register("toy_tank_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOY_TANK, 0x454b1b, 0x023020,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> EVIL_TOY_ROBOT_SPAWN_EGG = ITEMS.register("evil_toy_robot_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.EVIL_ROBOT, 0x0096ff, 0xc70039,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> EVIL_TOY_SOLDIER_SPAWN_EGG = ITEMS.register("evil_toy_soldier_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOYSOLDIER, 0xff5733, 0xffd700,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> EVIL_TOY_TANK_SPAWN_EGG = ITEMS.register("evil_toy_tank_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOY_TANK, 0x454b1b, 0x023020,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
