package net.vashal.tistheseason.items.custom.curios;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.vashal.tistheseason.block.TTS_Blocks;
import net.vashal.tistheseason.event.ModEvents;
import top.theillusivec4.curios.api.SlotContext;

import java.util.Objects;

public class PowerGloveItem extends TTSCurios {

    public PowerGloveItem(Properties properties) {
        super(properties);
        addListener(EventPriority.HIGH, PlayerInteractEvent.RightClickBlock.class, this::onRightClick);
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(ModEvents.REDSTONE_TOUCH).ifPresent(state -> {
                state.setState(1);
            });
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(ModEvents.REDSTONE_TOUCH).ifPresent(state -> {
                state.setState(0);
            });
        }
    }


    private void onRightClick(PlayerInteractEvent.RightClickBlock event, LivingEntity wearer) {
        Player player = event.getEntity();
        BlockPos pos = event.getPos().relative(Objects.requireNonNull(event.getFace()));
        BlockPos pPos = event.getPos();
        BlockState state = TTS_Blocks.INVISIBLE_REDSTONE.get().defaultBlockState();
        Level world = event.getLevel();
        Block block = world.getBlockState(pPos).getBlock();
        if (player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty() && !(block instanceof BaseEntityBlock)) {
            if (!(world.getBlockState(pos).getMaterial() == Material.AIR && world.getBlockState(pos).getBlock() != TTS_Blocks.INVISIBLE_REDSTONE.get())) {
                return;
            }
            player.getCapability(ModEvents.REDSTONE_TOUCH).ifPresent(redstoneTouch -> {
                if (redstoneTouch.getCurrentState() == 1) {
                    world.setBlockAndUpdate(pos, state);
                    world.scheduleTick(pos, state.getBlock(), 20);
                    if (!world.isOutsideBuildHeight(pos)) {
                        world.sendBlockUpdated(pos, state, state, 3);
                        world.updateNeighborsAt(pos, state.getBlock());
                    }
                }
            });
        }
    }
}
