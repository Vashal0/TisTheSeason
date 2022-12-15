package net.vashal.tistheseason.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.entity.PetRockRenderer;
import net.vashal.tistheseason.block.entity.TTSBlockEntities;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.client.renderers.*;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.items.custom.curios.renderer.*;
import net.vashal.tistheseason.screen.StockingGUI;
import net.vashal.tistheseason.screen.TTS_MenuTypes;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
    }

    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            EntityRenderers.register(TTS_EntityTypes.TOY_ROBOT.get(), ToyRobotRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.EVIL_ROBOT.get(), EvilRobotRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.TOY_SOLDIER.get(), ToySoldierRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.EVIL_TOY_SOLDIER.get(), EvilToySoldierRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.TOY_TANK.get(), ToyTankRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.EVIL_TOY_TANK.get(), EvilToyTankRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.KRAMPUS.get(), KrampusRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.IRON_BALL.get(), IronBallRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.WATER_STREAM.get(), WaterStreamRenderer::new);
            event.registerBlockEntityRenderer(TTSBlockEntities.PET_ROCK.get(), PetRockRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.POWER_GLOVE.get(), GloveRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.MITTENS.get(), MittenRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.HAT.get(), HatRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.SOLDIER_HAT.get(), SoldierHatRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.SOLDIER_HAT_ALT.get(), AltSoldierHatRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.REINDEER_SLIPPERS.get(), ReindeerSlipperRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.SNOWMAN_SLIPPERS.get(), SnowmanSlipperRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.SWEATER.get(), SweaterRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.HOBBY_HORSE.get(), HobbyHorseRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.MARI_HOBBY_HORSE.get(), MariHobbyHorseRenderer::new);
            MenuScreens.register(TTS_MenuTypes.STOCKING_CONTAINER_MENU.get(), StockingGUI::new);

        }
    }
}
