package net.vashal.tistheseason.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class StockingBlockItem extends BlockItem {

    public StockingBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("Write a letter (written book) to Santa for X-Mas! (title must contain Santa)").withStyle(ChatFormatting.GREEN));
            components.add(Component.literal("Toss it into the air,\"POOF\", Sleep and wake up X-Mas morning and check your stocking!\n(Recommended to empty your stocking before doing this)").withStyle(ChatFormatting.GOLD));
        } else {
            components.add(Component.literal("The LAST stocking you place into the world belongs to you, no one else can access it").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.BOLD));
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
