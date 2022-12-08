package net.vashal.tistheseason.items.custom.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class TTSCurios extends Item implements ICurioItem {

    public TTSCurios(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return entity != null && CuriosApi.getCuriosHelper().findFirstCurio(entity, this).isPresent();
    }

    protected <T extends Event, S extends LivingEntity> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        MinecraftForge.EVENT_BUS.addListener(priority, true, eventClass, event -> {
            S wearer = wearerSupplier.apply(event);
            if (isEquippedBy(wearer)) {
                listener.accept(event, wearer);
            }
        });
    }
    protected <T extends LivingEvent> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(priority, eventClass, listener, LivingEvent::getEntity);
    }


}