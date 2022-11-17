package net.vashal.tistheseason;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vashal.tistheseason.entity.ModEntityTypes;
import net.vashal.tistheseason.entity.client.ToyRobotRenderer;
import net.vashal.tistheseason.entity.client.ToySoldierRenderer;
import net.vashal.tistheseason.items.ModItems;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TisTheSeason.MOD_ID)
public class TisTheSeason {
    public static final String MOD_ID = "tistheseason";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TisTheSeason() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();
        ModEntityTypes.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            EntityRenderers.register(ModEntityTypes.TOYROBOT.get(), ToyRobotRenderer::new);
            EntityRenderers.register(ModEntityTypes.TOYSOLDIER.get(), ToySoldierRenderer::new);

        }
    }
}
