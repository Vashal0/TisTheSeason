package net.vashal.tistheseason.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.TTS_Blocks;

public class TTS_BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TisTheSeason.MOD_ID);


    public static final RegistryObject<BlockEntityType<ToyWorkbenchBlockEntity>> TOY_WORKBENCH =
            BLOCK_ENTITIES.register("toy_workbench", () ->
                    BlockEntityType.Builder.of(ToyWorkbenchBlockEntity::new,
                            TTS_Blocks.TOY_WORKBENCH.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
