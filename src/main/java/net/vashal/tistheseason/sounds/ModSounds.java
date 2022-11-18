package net.vashal.tistheseason.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TisTheSeason.MOD_ID);

    public static final RegistryObject<SoundEvent> TOYWALK =
            SOUNDS.register("toywalk", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toywalk")));

    public static final RegistryObject<SoundEvent> TOYDEATH =
            SOUNDS.register("toydeath", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toydeath")));

    public static final RegistryObject<SoundEvent> TOYAMBIENT =
            SOUNDS.register("toyambient", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toyambient")));

    public static final RegistryObject<SoundEvent> TOYHURT =
            SOUNDS.register("toyhurt", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toyhurt")));

}

