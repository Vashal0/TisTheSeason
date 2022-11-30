package net.vashal.tistheseason;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vashal.tistheseason.block.TTS_Blocks;
import net.vashal.tistheseason.block.entity.TTS_BlockEntities;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.client.*;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.items.custom.curios.renderer.HobbyHorseRenderer;
import net.vashal.tistheseason.networking.ModMessages;
import net.vashal.tistheseason.recipe.TTS_Recipes;
import net.vashal.tistheseason.screen.TTS_MenuTypes;
import net.vashal.tistheseason.screen.ToyWorkbenchScreen;
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
        TTS_Blocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HANDS.getMessageBuilder().build());

        GeckoLib.initialize();
        TTS_EntityTypes.register(modEventBus);
        TTS_Sounds.SOUNDS.register(modEventBus);
        TTS_BlockEntities.register(modEventBus);
        TTS_MenuTypes.register(modEventBus);
        TTS_Recipes.register(modEventBus);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessages::register);
    }

    private void clientSetup(final FMLClientSetupEvent evt) {

    }



    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            EntityRenderers.register(TTS_EntityTypes.TOYROBOT.get(), ToyRobotRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.TOYSOLDIER.get(), ToySoldierRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.TOY_TANK.get(), ToyTankRenderer::new);
            EntityRenderers.register(TTS_EntityTypes.IRON_BALL.get(), IronBallRenderer::new);

            CuriosRendererRegistry.register(TTS_Items.HOBBY_HORSE.get(), HobbyHorseRenderer::new);
            MenuScreens.register(TTS_MenuTypes.TOY_WORKBENCH_MENU.get(), ToyWorkbenchScreen::new);
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(IronBallModel.LAYER_LOCATION, IronBallModel::createBodyLayer);
        }
    }
}
