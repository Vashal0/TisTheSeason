package net.vashal.tistheseason.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.constants.KrampusConstants;
import net.vashal.tistheseason.constants.ToyRobotConstants;
import net.vashal.tistheseason.constants.ToyTankConstants;
import net.vashal.tistheseason.entity.custom.*;
import net.vashal.tistheseason.entity.projectile.IronBall;

public class TTS_EntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TisTheSeason.MOD_ID);

    public static final RegistryObject<EntityType<ToyRobotEntity>> TOYROBOT =
            ENTITY_TYPES.register(ToyRobotConstants.NAME,
                    () -> EntityType.Builder.of(ToyRobotEntity::new, MobCategory.MISC)
                            .sized(ToyRobotConstants.WIDTH, ToyRobotConstants.HEIGHT)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, ToyRobotConstants.NAME).toString()));

    public static final RegistryObject<EntityType<EvilToyRobotEntity>> EVIL_ROBOT =
            ENTITY_TYPES.register("evil_robot",
                    () -> EntityType.Builder.of(EvilToyRobotEntity::new, MobCategory.MONSTER)
                            .sized(ToyRobotConstants.WIDTH, ToyRobotConstants.HEIGHT)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, ToyRobotConstants.NAME).toString()));

    public static final RegistryObject<EntityType<ToySoldierEntity>> TOYSOLDIER =
            ENTITY_TYPES.register("toysoldier",
                    () -> EntityType.Builder.of(ToySoldierEntity::new, MobCategory.MONSTER)
                            .sized(0.5f, 1f)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, "toysoldier").toString()));

    public static final RegistryObject<EntityType<EvilToyTankEntity>> TOY_TANK =
            ENTITY_TYPES.register(ToyTankConstants.NAME,
                    () -> EntityType.Builder.of(EvilToyTankEntity::new, MobCategory.MONSTER)
                            .sized(ToyTankConstants.WIDTH, ToyTankConstants.HEIGHT)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, ToyTankConstants.NAME).toString()));

    public static final RegistryObject<EntityType<KrampusEntity>> KRAMPUS =
            ENTITY_TYPES.register(KrampusConstants.NAME,
                    () -> EntityType.Builder.of(KrampusEntity::new, MobCategory.MONSTER)
                            .sized(KrampusConstants.WIDTH, KrampusConstants.HEIGHT)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, KrampusConstants.NAME).toString()));

    public static final RegistryObject<EntityType<IronBall>> IRON_BALL =
            ENTITY_TYPES.register("ironball",
                    () -> EntityType.Builder.<IronBall>of(IronBall::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .noSummon()
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, "ironball").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
