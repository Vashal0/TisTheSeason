package net.vashal.tistheseason.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.entity.StockingBlockEntity;
import net.vashal.tistheseason.capabilities.INaughtyOrNice;
import net.vashal.tistheseason.capabilities.IRedstoneTouch;
import net.vashal.tistheseason.capabilities.NaughtyOrNiceAttacher;
import net.vashal.tistheseason.capabilities.RedstoneTouchAttacher;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.custom.*;
import net.vashal.tistheseason.items.TTS_Items;


public class ModEvents {


    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID)
    public static class ForgeEvents {



        @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
        public static class ModEventBusEvents {
            @SubscribeEvent
            public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
                event.put(TTS_EntityTypes.TOY_ROBOT.get(), ToyRobotEntity.setAttributes());
                event.put(TTS_EntityTypes.EVIL_ROBOT.get(), EvilToyRobotEntity.setAttributes());
                event.put(TTS_EntityTypes.TOY_SOLDIER.get(), ToySoldierEntity.setAttributes());
                event.put(TTS_EntityTypes.EVIL_TOY_SOLDIER.get(), EvilToySoldierEntity.setAttributes());
                event.put(TTS_EntityTypes.TOY_TANK.get(), ToyTankEntity.setAttributes());
                event.put(TTS_EntityTypes.EVIL_TOY_TANK.get(), EvilToyTankEntity.setAttributes());
                event.put(TTS_EntityTypes.KRAMPUS.get(), KrampusEntity.setAttributes());
            }
        }
    }
}

