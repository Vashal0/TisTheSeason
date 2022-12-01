package net.vashal.tistheseason.event;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.block.entity.TTS_BlockEntities;
import net.vashal.tistheseason.block.entity.ToyWorkbenchBlockEntity;
import net.vashal.tistheseason.block.entity.renderer.ToyWorkbenchBlockEntityRenderer;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
    }

    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(TTS_BlockEntities.TOY_WORKBENCH.get(),
                    ToyWorkbenchBlockEntityRenderer::new);
        }
    }
}
