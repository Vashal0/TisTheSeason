package net.vashal.tistheseason.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TisTheSeason.MOD_ID);

    public static final RegistryObject<EntityType<ToyRobotEntity>> TOYROBOT =
            ENTITY_TYPES.register("toyrobot",
                    () -> EntityType.Builder.of(ToyRobotEntity::new, MobCategory.MONSTER)
                            .sized(0.5f, 1f)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, "toyrobot").toString()));

    public static final RegistryObject<EntityType<ToySoldierEntity>> TOYSOLDIER =
            ENTITY_TYPES.register("toysoldier",
                    () -> EntityType.Builder.of(ToySoldierEntity::new, MobCategory.MONSTER)
                            .sized(0.5f, 1f)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, "toysoldier").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
