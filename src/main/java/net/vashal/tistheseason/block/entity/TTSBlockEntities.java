package net.vashal.tistheseason.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.TTS_Blocks;

public class TTSBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TisTheSeason.MOD_ID);

    public static final RegistryObject<BlockEntityType<RockingHorseBlockEntity>> ROCKING_HORSE =
            BLOCK_ENTITIES.register("rocking_horse", () ->
                    BlockEntityType.Builder.of(RockingHorseBlockEntity::new,
                            TTS_Blocks.ROCKING_HORSE.get()).build(null));

    public static final RegistryObject<BlockEntityType<PetRockBlockEntity>> PET_ROCK =
            BLOCK_ENTITIES.register("pet_rock", () ->
                    BlockEntityType.Builder.of(PetRockBlockEntity::new,
                            TTS_Blocks.PET_ROCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
