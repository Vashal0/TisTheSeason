package net.vashal.tistheseason.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.TTS_Blocks;
import net.vashal.tistheseason.block.custom.StockingBlock;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.items.custom.*;
import net.vashal.tistheseason.items.custom.curios.*;
import net.vashal.tistheseason.utils.TTS_CreativeModeTab;


public class TTS_Items {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TisTheSeason.MOD_ID);

    public static final RegistryObject<Item> TOY_ROBOT_ITEM = ITEMS.register("toy_robot_item",
            () -> new ToyRobotItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> TOY_SOLDIER_ITEM = ITEMS.register("toy_soldier_item",
            () -> new ToySoldierItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> TOY_TANK_ITEM = ITEMS.register("toy_tank_item",
            () -> new ToyTankItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> HOBBY_HORSE = ITEMS.register("hobby_horse_item",
            () -> new HobbyHorseItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MARI_HOBBY_HORSE = ITEMS.register("mari_hobby_horse_item",
            () -> new MariHobbyHorseItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> POWER_GLOVE = ITEMS.register("power_glove_item",
            () -> new PowerGloveItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> MITTENS = ITEMS.register("mittens",
            () -> new MittenItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SWEATER = ITEMS.register("sweater_item",
            () -> new SweaterItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> HAT = ITEMS.register("santa_hat_item",
            () -> new HatItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SOLDIER_HAT = ITEMS.register("soldier_hat",
            () -> new SoldierHatItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SOLDIER_HAT_ALT = ITEMS.register("soldier_hat_alt",
            () -> new SoldierHatItemAlt(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> REINDEER_SLIPPERS = ITEMS.register("reindeer_slippers",
            () -> new ReindeerSlipperItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> SNOWMAN_SLIPPERS = ITEMS.register("snowman_slippers",
            () -> new SnowmanSlipperItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> IRON_BALL_ITEM = ITEMS.register("iron_ball_item",
            () -> new IronBallItem(new Item.Properties()));

    public static final RegistryObject<Item> WATER_STREAM = ITEMS.register("water_stream",
            () -> new WaterStreamItem(new Item.Properties()));

    public static final RegistryObject<BlockItem> STOCKING_ITEM = ITEMS.register("stocking_item",
            () -> new StockingBlockItem(TTS_Blocks.STOCKING.get(), new Item.Properties()));

    public static final RegistryObject<Item> TOY_PARTS = ITEMS.register("toy_parts",
            () -> new ToyPartsItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(64)));

    public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane",
            () -> new Item(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).food(TTSFoods.CANDY_CANE).stacksTo(64)));

    public static final RegistryObject<Item> SUPER_SOAKER = ITEMS.register("super_soaker",
            () -> new SuperSoakerItem(new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB).stacksTo(1)));

    public static final RegistryObject<Item> TOY_ROBOT_SPAWN_EGG = ITEMS.register("eggs/toy_robot_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOY_ROBOT, 0x0096ff, 0xc70039,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> TOY_SOLDIER_SPAWN_EGG = ITEMS.register("eggs/toy_soldier_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOY_SOLDIER, 0xe8b923, 0xc70039,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> TOY_TANK_SPAWN_EGG = ITEMS.register("eggs/toy_tank_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOY_TANK, 0x454b1b, 0x023020,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> EVIL_TOY_ROBOT_SPAWN_EGG = ITEMS.register("eggs/evil_toy_robot_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.EVIL_ROBOT, 0xc70039, 0x0096ff,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    public static final RegistryObject<Item> KRAMPUS_SPAWN_EGG = ITEMS.register("eggs/krampus_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.KRAMPUS, 0xc70039, 0x5A5A5A,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

    /*
    public static final RegistryObject<Item> EVIL_TOY_SOLDIER_SPAWN_EGG = ITEMS.register("eggs/evil_toy_soldier_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.TOYSOLDIER, 0xc70039, 0xe8b923,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));

     */

    public static final RegistryObject<Item> EVIL_TOY_TANK_SPAWN_EGG = ITEMS.register("eggs/evil_toy_tank_spawn_egg",
            () -> new ForgeSpawnEggItem(TTS_EntityTypes.EVIL_TOY_TANK, 0x023020, 0x454b1b,
                    new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
