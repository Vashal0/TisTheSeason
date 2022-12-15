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
import net.vashal.tistheseason.constants.ToySoldierConstants;
import net.vashal.tistheseason.constants.ToyTankConstants;
import net.vashal.tistheseason.entity.custom.*;
import net.vashal.tistheseason.entity.projectile.IronBall;
import net.vashal.tistheseason.entity.projectile.WaterStream;

public class TTS_EntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TisTheSeason.MOD_ID);

    public static final RegistryObject<EntityType<ToyRobotEntity>> TOY_ROBOT =
            ENTITY_TYPES.register(ToyRobotConstants.NAME,
                    () -> EntityType.Builder.<ToyRobotEntity>of(ToyRobotEntity::new, MobCategory.MISC)
                            .sized(ToyRobotConstants.WIDTH, ToyRobotConstants.HEIGHT)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, ToyRobotConstants.NAME).toString()));

    public static final RegistryObject<EntityType<EvilToyRobotEntity>> EVIL_ROBOT =
            ENTITY_TYPES.register("evil_robot",
                    () -> EntityType.Builder.of(EvilToyRobotEntity::new, MobCategory.MONSTER)
                            .sized(ToyRobotConstants.WIDTH, ToyRobotConstants.HEIGHT)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, ToyRobotConstants.NAME).toString()));

    public static final RegistryObject<EntityType<ToySoldierEntity>> TOY_SOLDIER =
            ENTITY_TYPES.register(ToySoldierConstants.NAME,
                    () -> EntityType.Builder.<ToySoldierEntity>of(ToySoldierEntity::new, MobCategory.MISC)
                            .sized(ToySoldierConstants.WIDTH, ToySoldierConstants.HEIGHT)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, ToySoldierConstants.NAME).toString()));

    public static final RegistryObject<EntityType<EvilToySoldierEntity>> EVIL_TOY_SOLDIER =
            ENTITY_TYPES.register("evil_toy_soldier",
                    () -> EntityType.Builder.of(EvilToySoldierEntity::new, MobCategory.MONSTER)
                            .sized(0.5f, 1f)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, "evil_toy_soldier").toString()));


    public static final RegistryObject<EntityType<EvilToyTankEntity>> EVIL_TOY_TANK =
            ENTITY_TYPES.register("evil_toy_tank",
                    () -> EntityType.Builder.of(EvilToyTankEntity::new, MobCategory.MONSTER)
                            .sized(ToyTankConstants.WIDTH, ToyTankConstants.HEIGHT)
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, "evil_toy_tank").toString()));

    public static final RegistryObject<EntityType<ToyTankEntity>> TOY_TANK =
            ENTITY_TYPES.register(ToyTankConstants.NAME,
                    () -> EntityType.Builder.<ToyTankEntity>of(ToyTankEntity::new, MobCategory.MISC)
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

    public static final RegistryObject<EntityType<WaterStream>> WATER_STREAM =
            ENTITY_TYPES.register("waterstream",
                    () -> EntityType.Builder.<WaterStream>of(WaterStream::new, MobCategory.MISC)
                            .sized(1F, 1F)
                            .noSummon()
                            .build(new ResourceLocation(TisTheSeason.MOD_ID, "waterstream").toString()));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
