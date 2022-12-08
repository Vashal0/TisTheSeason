package net.vashal.tistheseason.items.custom.curios;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.event.ModEvents;
import top.theillusivec4.curios.api.SlotContext;

public class PowerGloveItem extends TTSCurios {

    public PowerGloveItem(Properties properties) {
        super(properties);
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if (slotContext.entity() instanceof Player player) {
            changeStateWeak(player);
        }

    }

    public void changeStateWeak(Player player) {
        player.getCapability(ModEvents.REDSTONE_TOUCH).ifPresent(state -> {
            state.setState(1);
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof Player player) {
            changeStateOff(player);
        }
    }

    public void changeStateStrong(Player player) {
        player.getCapability(ModEvents.REDSTONE_TOUCH).ifPresent(state -> {
            state.setState(2);
        });
    }

    public void changeStateOff(Player player) {
        player.getCapability(ModEvents.REDSTONE_TOUCH).ifPresent(state -> {
            state.setState(0);
        });
    }
}
