package net.vashal.tistheseason.items.custom.curios;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.capabilities.TTSCapabilities;
import net.vashal.tistheseason.event.ModEvents;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class SweaterItem extends TTSCurios {

    public SweaterItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slotContext, uuid, stack);
        attributes.put(Attributes.ARMOR, new AttributeModifier(uuid, "armor", 1, AttributeModifier.Operation.ADDITION));
        return attributes;
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
}
