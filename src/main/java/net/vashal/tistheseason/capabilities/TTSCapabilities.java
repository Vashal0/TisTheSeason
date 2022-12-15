package net.vashal.tistheseason.capabilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.vashal.tistheseason.TisTheSeason;

public class TTSCapabilities {

    public static final Capability<INaughtyOrNice> NAUGHTY_OR_NICE = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Capability<IRedstoneTouch> REDSTONE_TOUCH = CapabilityManager.get(new CapabilityToken<>() {
    });


    public static LazyOptional<INaughtyOrNice> getScore(final LivingEntity entity) {
        if (entity == null)
            return LazyOptional.empty();
        return entity.getCapability(NAUGHTY_OR_NICE);
    }

    public static LazyOptional<IRedstoneTouch> getRedstoneTouch(final LivingEntity entity) {
        if (entity == null)
            return LazyOptional.empty();
        return entity.getCapability(REDSTONE_TOUCH);
    }


    @Mod.EventBusSubscriber(modid = TisTheSeason.MOD_ID)
    public static class ForgeEvents {


        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                NaughtyOrNiceAttacher.attach(event);
                RedstoneTouchAttacher.attach(event);
            }
        }


        @SubscribeEvent
        public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
            event.register(INaughtyOrNice.class);
            event.register(IRedstoneTouch.class);
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            Player oldPlayer = event.getOriginal();
            oldPlayer.revive();
            getScore(oldPlayer).ifPresent(oldNiceScore -> getScore(event.getEntity()).ifPresent(newNiceScore -> {
                newNiceScore.setMaxScore(oldNiceScore.getMaxScore());
                newNiceScore.setMinScore(oldNiceScore.getMinScore());
                newNiceScore.setScore(oldNiceScore.getCurrentScore());
                newNiceScore.setStocking(oldNiceScore.getStocking());
            }));

            getRedstoneTouch(oldPlayer).ifPresent(oldRedstoneTouch -> getRedstoneTouch(event.getEntity()).ifPresent(newRedstoneTouch -> {
                newRedstoneTouch.setState(oldRedstoneTouch.getCurrentState());
            }));
            event.getOriginal().invalidateCaps();
        }
    }




}
