package net.vashal.tistheseason.items.custom.curios;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.vashal.tistheseason.block.TTS_Blocks;
import net.vashal.tistheseason.capabilities.TTSCapabilities;
import net.vashal.tistheseason.event.ModEvents;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class PowerGloveItem extends TTSCurios {

    public PowerGloveItem(Properties properties) {
        super(properties);
        addListener(EventPriority.HIGH, PlayerInteractEvent.RightClickBlock.class, this::onRightClick);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(TTSCapabilities.REDSTONE_TOUCH).ifPresent(state -> {
                state.setState(1);
            });
        }
    }
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(TTSCapabilities.REDSTONE_TOUCH).ifPresent(state -> {
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
            player.getCapability(TTSCapabilities.REDSTONE_TOUCH).ifPresent(redstoneTouch -> {
                if (redstoneTouch.getCurrentState() == 1) {
                    world.setBlockAndUpdate(pos, state);
                    world.scheduleTick(pos, state.getBlock(), 20);
                    if (player.getLevel() instanceof ServerLevel level) {
                        level.sendParticles(DustParticleOptions.REDSTONE, pos.getX(), pos.getY(), pos.getZ(), 12, 0.5, 0.5, 0.5, 0.1);
                        if (!world.isOutsideBuildHeight(pos)) {
                            world.sendBlockUpdated(pos, state, state, 3);
                            world.updateNeighborsAt(pos, state.getBlock());
                        }
                    }
                }
            });
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("While equipped: Right click with an empty hand to pulse a temporary redstone signal").withStyle(ChatFormatting.DARK_RED));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
