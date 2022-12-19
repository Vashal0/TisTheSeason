package net.vashal.tistheseason.items.custom.curios;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;

public class SnowmanSlipperItem extends TTSCurios {

    public SnowmanSlipperItem(Properties properties) {
        super(properties);
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if (slotContext.entity() instanceof Player player) {
            if (!player.level.isClientSide) {
                int i;
                int j;
                int k;
                BlockState blockstate = Blocks.SNOW.defaultBlockState();
                for (int l = 0; l < 4; ++l) {
                    i = Mth.floor(player.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
                    j = Mth.floor(player.getY());
                    k = Mth.floor(player.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
                    BlockPos blockpos1 = new BlockPos(i, j, k);
                    if (player.level.isEmptyBlock(blockpos1) && blockstate.canSurvive(player.level, blockpos1)) {
                        player.level.setBlockAndUpdate(blockpos1, blockstate);
                        player.level.gameEvent(GameEvent.BLOCK_PLACE, blockpos1, GameEvent.Context.of(player, blockstate));
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("While equipped: Embrace your inner Snow Golem").withStyle(ChatFormatting.WHITE));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
