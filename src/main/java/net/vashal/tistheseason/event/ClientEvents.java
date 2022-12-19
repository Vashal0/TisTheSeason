package net.vashal.tistheseason.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.entity.PetRockRenderer;
import net.vashal.tistheseason.block.entity.StockingRenderer;
import net.vashal.tistheseason.block.entity.TTSBlockEntities;
import net.vashal.tistheseason.entity.TTSEntityTypes;
import net.vashal.tistheseason.entity.client.renderers.*;
import net.vashal.tistheseason.items.TTSItems;
import net.vashal.tistheseason.items.custom.curios.client.renderer.*;
import net.vashal.tistheseason.screen.StockingGUI;
import net.vashal.tistheseason.screen.TTSMenuTypes;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
    }

    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            EntityRenderers.register(TTSEntityTypes.TOY_ROBOT.get(), ToyRobotRenderer::new);
            EntityRenderers.register(TTSEntityTypes.EVIL_ROBOT.get(), EvilRobotRenderer::new);
            EntityRenderers.register(TTSEntityTypes.TOY_SOLDIER.get(), ToySoldierRenderer::new);
            EntityRenderers.register(TTSEntityTypes.EVIL_TOY_SOLDIER.get(), EvilToySoldierRenderer::new);
            EntityRenderers.register(TTSEntityTypes.TOY_TANK.get(), ToyTankRenderer::new);
            EntityRenderers.register(TTSEntityTypes.EVIL_TOY_TANK.get(), EvilToyTankRenderer::new);
            EntityRenderers.register(TTSEntityTypes.KRAMPUS.get(), KrampusRenderer::new);
            EntityRenderers.register(TTSEntityTypes.IRON_BALL.get(), IronBallRenderer::new);
            EntityRenderers.register(TTSEntityTypes.WATER_STREAM.get(), WaterStreamRenderer::new);
            event.registerBlockEntityRenderer(TTSBlockEntities.PET_ROCK.get(), PetRockRenderer::new);
            event.registerBlockEntityRenderer(TTSBlockEntities.STOCKING.get(), StockingRenderer::new);
            CuriosRendererRegistry.register(TTSItems.POWER_GLOVE.get(), GloveRenderer::new);
            CuriosRendererRegistry.register(TTSItems.MITTENS.get(), MittenRenderer::new);
            CuriosRendererRegistry.register(TTSItems.HAT.get(), HatRenderer::new);
            CuriosRendererRegistry.register(TTSItems.SOLDIER_HAT.get(), SoldierHatRenderer::new);
            CuriosRendererRegistry.register(TTSItems.SOLDIER_HAT_ALT.get(), AltSoldierHatRenderer::new);
            CuriosRendererRegistry.register(TTSItems.REINDEER_SLIPPERS.get(), ReindeerSlipperRenderer::new);
            CuriosRendererRegistry.register(TTSItems.SNOWMAN_SLIPPERS.get(), SnowmanSlipperRenderer::new);
            CuriosRendererRegistry.register(TTSItems.SWEATER.get(), SweaterRenderer::new);
            CuriosRendererRegistry.register(TTSItems.HOBBY_HORSE.get(), HobbyHorseRenderer::new);
            CuriosRendererRegistry.register(TTSItems.MARI_HOBBY_HORSE.get(), MariHobbyHorseRenderer::new);
            MenuScreens.register(TTSMenuTypes.STOCKING_CONTAINER_MENU.get(), StockingGUI::new);

        }
    }
}
