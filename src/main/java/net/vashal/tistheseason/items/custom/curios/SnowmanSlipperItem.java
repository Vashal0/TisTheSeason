package net.vashal.tistheseason.items.custom.curios;

import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

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
}
