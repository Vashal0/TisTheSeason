package net.vashal.tistheseason.items.custom.curios;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class HobbyHorseItem extends TTSCurios {

    public HobbyHorseItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slotContext, uuid, stack);
        attributes.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(uuid, "max_step_height", 0.5, AttributeModifier.Operation.ADDITION));
        attributes.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "speed_bonus", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return attributes;
    }
}
