package net.vashal.tistheseason.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vashal.tistheseason.TisTheSeason;

public class TTSSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TisTheSeason.MOD_ID);

    public static final RegistryObject<SoundEvent> TOY_WALK =
            SOUNDS.register("toywalk", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toywalk")));

    public static final RegistryObject<SoundEvent> TOY_DEATH =
            SOUNDS.register("toydeath", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toydeath")));

    public static final RegistryObject<SoundEvent> TOY_AMBIENT =
            SOUNDS.register("toyambient", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toyambient")));

    public static final RegistryObject<SoundEvent> TOY_HURT =
            SOUNDS.register("toyhurt", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toyhurt")));

    public static final RegistryObject<SoundEvent> WIND_TURN =
            SOUNDS.register("windturn", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "windturn")));

    public static final RegistryObject<SoundEvent> CORK =
            SOUNDS.register("cork", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "cork")));

    public static final RegistryObject<SoundEvent> DRUM =
            SOUNDS.register("drum", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "drum")));

    public static final RegistryObject<SoundEvent> TOY_GEARS =
            SOUNDS.register("toygears", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "toygears")));

    public static final RegistryObject<SoundEvent> KRAMPUS =
            SOUNDS.register("krampus", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "krampus")));

    public static final RegistryObject<SoundEvent> KRAMPUS_HIT =
            SOUNDS.register("krampus_hit", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "krampus_hit")));

    public static final RegistryObject<SoundEvent> KRAMPUS_HURT =
            SOUNDS.register("krampus_hurt", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "krampus_hurt")));

    public static final RegistryObject<SoundEvent> GROWL =
            SOUNDS.register("growl", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "growl")));

    public static final RegistryObject<SoundEvent> SNARL =
            SOUNDS.register("snarl", () -> new SoundEvent(
                    new ResourceLocation(TisTheSeason.MOD_ID, "snarl")));

}

