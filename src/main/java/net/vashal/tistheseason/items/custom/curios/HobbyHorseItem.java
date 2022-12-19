package net.vashal.tistheseason.items.custom.curios;

import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class HobbyHorseItem extends TTSCurios {

    public HobbyHorseItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slotContext, uuid, stack);
        attributes.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(uuid, "max_step_height", 0.5, AttributeModifier.Operation.ADDITION));
        attributes.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "speed_bonus", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return attributes;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("While equipped: Gain additional step-height and speed").withStyle(ChatFormatting.LIGHT_PURPLE));
            components.add(Component.literal("Almost as good as a real horse!").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
