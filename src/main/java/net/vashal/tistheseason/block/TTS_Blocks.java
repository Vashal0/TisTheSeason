package net.vashal.tistheseason.block;

import com.google.common.base.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.custom.ToyWorkbench;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.utils.TTS_CreativeModeTab;

public class TTS_Blocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TisTheSeason.MOD_ID);

    public static final RegistryObject<Block> TOY_WORKBENCH = registerBlock("toy_workbench",
            () -> new ToyWorkbench(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(6f).requiresCorrectToolForDrops().noOcclusion()), TTS_CreativeModeTab.TISTHESEASON_TAB);




    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {

        return TTS_Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventbus) {
        BLOCKS.register(eventbus);
    }
}
