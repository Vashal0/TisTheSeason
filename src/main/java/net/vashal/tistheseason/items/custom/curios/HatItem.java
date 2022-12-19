package net.vashal.tistheseason.items.custom.curios;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vashal.tistheseason.capabilities.TTSCapabilities;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;

public class HatItem extends TTSCurios {

    public HatItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> niceScore.addFestiveMultiplier(1));
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> niceScore.removeFestiveMultiplier(1));
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("While equipped: Filled with X-Mas cheer, your actions both good and bad are noticed more by santa").withStyle(ChatFormatting.WHITE));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
