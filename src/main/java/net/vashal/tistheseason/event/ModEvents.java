package net.vashal.tistheseason.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;
import net.vashal.tistheseason.entity.custom.EvilToyTankEntity;


public class ModEvents {
    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID)
    public static class ForgeEvents {


        @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ModEventBusEvents {
            @SubscribeEvent
            public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
                event.put(TTS_EntityTypes.TOYROBOT.get(), ToyRobotEntity.setAttributes());
                event.put(TTS_EntityTypes.TOYSOLDIER.get(), ToySoldierEntity.setAttributes());
                event.put(TTS_EntityTypes.TOY_TANK.get(), EvilToyTankEntity.setAttributes());
            }
        }
    }
}
