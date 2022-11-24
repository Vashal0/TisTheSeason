package net.vashal.tistheseason;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.client.ToyRobotRenderer;
import net.vashal.tistheseason.entity.client.ToySoldierRenderer;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.items.custom.curios.HobbyHorseItem;
import net.vashal.tistheseason.items.custom.curios.renderer.HobbyHorseRenderer;
import net.vashal.tistheseason.sounds.TTS_Sounds;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TisTheSeason.MOD_ID)
public class TisTheSeason {
    public static final String MOD_ID = "tistheseason";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TisTheSeason() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        TTS_Items.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());

        GeckoLib.initialize();
        TTS_EntityTypes.register(modEventBus);
        TTS_Sounds.SOUNDS.register(modEventBus);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent evt) {
        CuriosRendererRegistry.register(TTS_Items.HOBBY_HORSE.get(), HobbyHorseRenderer::new);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            EntityRenderers.register(TTS_EntityTypes.TOYROBOT.get(), ToyRobotRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.TOYSOLDIER.get(), ToySoldierRenderer::new);
        }
    }
}
