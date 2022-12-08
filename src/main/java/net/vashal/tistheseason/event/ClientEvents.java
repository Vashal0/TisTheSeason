package net.vashal.tistheseason.event;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.client.renderers.*;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.items.custom.curios.renderer.*;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
    }

    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            EntityRenderers.register(TTS_EntityTypes.TOYROBOT.get(), ToyRobotRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.EVIL_ROBOT.get(), EvilRobotRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.TOYSOLDIER.get(), ToySoldierRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.TOY_TANK.get(), ToyTankRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.KRAMPUS.get(), KrampusRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.IRON_BALL.get(), IronBallRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.POWER_GLOVE.get(), GloveRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.HAT.get(), HatRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.REINDEER_SLIPPERS.get(), ReindeerSlipperRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.SNOWMAN_SLIPPERS.get(), SnowmanSlipperRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.SWEATER.get(), SweaterRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.HOBBY_HORSE.get(), HobbyHorseRenderer::new);
            CuriosRendererRegistry.register(TTS_Items.MARI_HOBBY_HORSE.get(), MariHobbyHorseRenderer::new);
        }
    }
}
