package net.vashal.tistheseason.items.custom.curios;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
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
import net.vashal.tistheseason.block.TTSBlocks;
import net.vashal.tistheseason.capabilities.TTSCapabilities;
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
            player.getCapability(TTSCapabilities.REDSTONE_TOUCH).ifPresent(state -> state.setState(1));
        }
    }
    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof Player player) {
            player.getCapability(TTSCapabilities.REDSTONE_TOUCH).ifPresent(state -> state.setState(0));
        }
    }

    private void onRightClick(PlayerInteractEvent.RightClickBlock event, LivingEntity wearer) {
        Player player = event.getEntity();
        BlockPos pPos = event.getPos();
        BlockPos pos = pPos.relative(Objects.requireNonNull(event.getFace()));
        BlockState state = TTSBlocks.INVISIBLE_REDSTONE.get().defaultBlockState();
        Level world = event.getLevel();
        BlockState state1 = world.getBlockState(pPos);
        BlockState state2 = world.getBlockState(pos);
        Block block = state1.getBlock();
        if (player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty() && !(block instanceof BaseEntityBlock)) {
            if (!(state2.getMaterial() == Material.AIR)) {
                return;
            }
            player.getCapability(TTSCapabilities.REDSTONE_TOUCH).ifPresent(redstoneTouch -> {
                if (redstoneTouch.getCurrentState() == 1) {
                    world.setBlockAndUpdate(pos, state);
                    world.scheduleTick(pos, state.getBlock(), 20);
                    if (player.getLevel() instanceof ServerLevel level) {
                        level.sendParticles(DustParticleOptions.REDSTONE, pos.getX(), pos.getY(), pos.getZ(), 12, 0.5, 0.5, 0.5, 0.1);
                    }
                }
            });
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("While equipped: Right click with an empty hand to pulse a temporary redstone signal").withStyle(ChatFormatting.DARK_RED));
            components.add(Component.literal("\"I love the power glove...It's so bad\"").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));

        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
