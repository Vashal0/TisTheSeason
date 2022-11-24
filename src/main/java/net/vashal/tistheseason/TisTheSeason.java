package net.vashal.tistheseason;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.Item;
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
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import net.vashal.tistheseason.block.TTS_Blocks;
import net.vashal.tistheseason.block.entity.TTS_BlockEntities;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.client.*;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.items.custom.HobbyHorseItem;
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

        MinecraftForge.EVENT_BUS.register(this);

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());

        GeckoLib.initialize();
        TTS_EntityTypes.register(modEventBus);
        TTS_Sounds.SOUNDS.register(modEventBus);
        TTS_BlockEntities.register(modEventBus);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

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
