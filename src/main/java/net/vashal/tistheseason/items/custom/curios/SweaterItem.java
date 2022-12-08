package net.vashal.tistheseason.items.custom.curios;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class SweaterItem extends TTSCurios {

    public SweaterItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slotContext, uuid, stack);
        attributes.put(Attributes.LUCK, new AttributeModifier(uuid, "luck", 1, AttributeModifier.Operation.ADDITION));
        attributes.put(Attributes.ARMOR, new AttributeModifier(uuid, "armor", 1, AttributeModifier.Operation.ADDITION));
        return attributes;
    }
}
