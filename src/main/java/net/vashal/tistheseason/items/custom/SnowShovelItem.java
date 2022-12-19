package net.vashal.tistheseason.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class SnowShovelItem extends Item {

    public SnowShovelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {

        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();

        if (!level.isClientSide()) {
            if (level.getBlockState(pos).getBlock() instanceof SnowLayerBlock) {
                for (int i = pos.getX() - 2; i < pos.getX() + 3; i++) {
                    for (int k = pos.getZ() - 2; k < pos.getZ() + 3; k++) {
                        if (pos.getX() == i && pos.getZ() == k)
                            continue;
                        BlockPos breakPos = new BlockPos(i, pos.getY(), k);
                        BlockState extraBlock = level.getBlockState(breakPos);
                        if (extraBlock.getBlock() instanceof SnowLayerBlock) {
                            level.destroyBlock(breakPos, false);
                            level.destroyBlock(pos, false);
                        }
                        if (level.getBlockState(pos).getBlock() instanceof SnowLayerBlock) {
                            level.destroyBlock(pos, false);
                        }
                    }
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("Shovels up a large area of snow layers with Right-Click").withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("Does NOT drop items!").withStyle(ChatFormatting.DARK_RED));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
