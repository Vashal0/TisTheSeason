package net.vashal.tistheseason.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.event.ModEvents;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class NaughtyOrNiceAttacher {

    private static class NaughtyOrNiceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {


        public static final ResourceLocation IDENTIFIER = new ResourceLocation(TisTheSeason.MOD_ID, "score");

        private final INaughtyOrNice backend = new NaughtyOrNice(null);
        private final LazyOptional<INaughtyOrNice> optionalData = LazyOptional.of(() -> backend);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return TTSCapabilities.NAUGHTY_OR_NICE.orEmpty(cap, this.optionalData);
        }

        void invalidate() {
            this.optionalData.invalidate();
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.backend.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.backend.deserializeNBT(nbt);
        }
    }

    public static void attach(final AttachCapabilitiesEvent<Entity> event) {
        final NaughtyOrNiceProvider provider = new NaughtyOrNiceProvider();
        event.addCapability(NaughtyOrNiceProvider.IDENTIFIER, provider);
    }
}
