package net.vashal.tistheseason.block;

import com.google.common.base.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.custom.InvisibleRedstone;
import net.vashal.tistheseason.block.custom.PetRockBlock;
import net.vashal.tistheseason.block.custom.StockingBlock;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.utils.TTS_CreativeModeTab;

public class TTS_Blocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TisTheSeason.MOD_ID);

    public static final RegistryObject<Block> INVISIBLE_REDSTONE = registerBlock("invisible_redstone",
            InvisibleRedstone::new);

    public static final RegistryObject<Block> PET_ROCK = registerBlock("pet_rock",
            PetRockBlock::new);

    public static final RegistryObject<Block> STOCKING = registerBlock("stocking",
            StockingBlock::new);


    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {

        if (name.equals("invisible_redstone")) {
            return TTS_Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        } else {
            return TTS_Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(TTS_CreativeModeTab.TISTHESEASON_TAB)));
        }
    }

    public static void register(IEventBus eventbus) {
        BLOCKS.register(eventbus);
    }
}
