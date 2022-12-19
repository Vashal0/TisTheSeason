package net.vashal.tistheseason.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.TTSEntityTypes;
import net.vashal.tistheseason.entity.custom.*;


public class ModEvents {


    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID)
    public static class ForgeEvents {



        @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ModEventBusEvents {
            @SubscribeEvent
            public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
                event.put(TTSEntityTypes.TOY_ROBOT.get(), ToyRobotEntity.setAttributes());
                event.put(TTSEntityTypes.EVIL_ROBOT.get(), EvilToyRobotEntity.setAttributes());
                event.put(TTSEntityTypes.TOY_SOLDIER.get(), ToySoldierEntity.setAttributes());
                event.put(TTSEntityTypes.EVIL_TOY_SOLDIER.get(), EvilToySoldierEntity.setAttributes());
                event.put(TTSEntityTypes.TOY_TANK.get(), ToyTankEntity.setAttributes());
                event.put(TTSEntityTypes.EVIL_TOY_TANK.get(), EvilToyTankEntity.setAttributes());
                event.put(TTSEntityTypes.KRAMPUS.get(), KrampusEntity.setAttributes());
            }
        }
    }
}

