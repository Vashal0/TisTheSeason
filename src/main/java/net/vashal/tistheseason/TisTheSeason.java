package net.vashal.tistheseason;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
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
import net.vashal.tistheseason.block.entity.TTSBlockEntities;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.items.TTS_Items;
import net.vashal.tistheseason.items.custom.curios.client.models.*;
import net.vashal.tistheseason.items.custom.curios.client.renderer.CuriosLayerDefinitions;
import net.vashal.tistheseason.screen.TTS_MenuTypes;
import net.vashal.tistheseason.sounds.TTS_Sounds;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

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
                () -> SlotTypePreset.BELT.getMessageBuilder().cosmetic().build());

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HANDS.getMessageBuilder().cosmetic().build());

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BODY.getMessageBuilder().cosmetic().build());

        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder().cosmetic().build());

        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("feet").priority(220).icon(InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS).cosmetic().build());

        GeckoLib.initialize();
        TTS_EntityTypes.register(modEventBus);
        TTS_Sounds.SOUNDS.register(modEventBus);
        TTS_MenuTypes.register(modEventBus);
        TTSBlockEntities.register(modEventBus);
    }

    public static ResourceLocation location(String path)
    {
        return new ResourceLocation(MOD_ID, path);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void clientSetup(final FMLClientSetupEvent evt) {
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(CuriosLayerDefinitions.GLOVES, GloveModel::createLayer);
            event.registerLayerDefinition(CuriosLayerDefinitions.MITTENS, MittenModel::createLayer);
            event.registerLayerDefinition(CuriosLayerDefinitions.SWEATER, SweaterModel::createLayer);
            event.registerLayerDefinition(CuriosLayerDefinitions.HAT, HatModel::createLayer);
            event.registerLayerDefinition(CuriosLayerDefinitions.SOLDIER_HAT, SoldierHatModel::createLayer);
            event.registerLayerDefinition(CuriosLayerDefinitions.REINDEER_SLIPPERS, ReindeerSlipperModel::createLayer);
            event.registerLayerDefinition(CuriosLayerDefinitions.SNOWMAN_SLIPPERS, SnowmanSlipperModel::createLayer);
        }
    }
}
