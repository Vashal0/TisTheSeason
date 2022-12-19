package net.vashal.tistheseason.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import net.vashal.tistheseason.entity.variant.ToyRobotVariant;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ToyRobotItem extends Item {

    public ToyRobotItem(Properties pProperties) {
        super(pProperties);
    }



    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getClickedFace();
        Level worldIn = context.getLevel();
        ItemStack stack = context.getItemInHand();
        assert player != null;
        if (!release(player, pos, facing, worldIn, stack)) return InteractionResult.FAIL;
        return InteractionResult.SUCCESS;
    }



    public boolean release(Player player, BlockPos pos, Direction facing, Level worldIn, ItemStack stack) {
        if (player.getCommandSenderWorld().isClientSide) return false;
        if (!containsEntity(stack)) {
            ToyRobotEntity toyRobot = new ToyRobotEntity(worldIn);
            toyRobot.setVariant(ToyRobotVariant.byId(11));
            if (stack.hasTag()) {
                assert stack.getTag() != null;
                toyRobot.load(stack.getTag());
            }
            BlockPos blockPos = pos.relative(facing);
            toyRobot.absMoveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
            stack.setTag(new CompoundTag());
            worldIn.addFreshEntity(toyRobot);
            stack.shrink(1);
            if (toyRobot.getOwner() == null) {
                toyRobot.tame(player);
            }
            return false;
        }
        ToyRobotEntity toyRobot = getEntityFromStack(stack, worldIn, true);
        BlockPos blockPos = pos.relative(facing);
        assert toyRobot != null;
        toyRobot.absMoveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
        stack.setTag(new CompoundTag());
        worldIn.addFreshEntity(toyRobot);
        stack.shrink(1);
        return true;
    }

    public boolean containsEntity(ItemStack stack) {
        if (stack.isEmpty() || !stack.hasTag()) return false;
        assert stack.getTag() != null;
        return stack.getTag().contains("toy");
    }

    @Nullable
    public ToyRobotEntity getEntityFromStack(ItemStack stack, Level world, boolean withInfo) {
        if (stack.hasTag()) {
            assert stack.getTag() != null;
            EntityType<?> type = EntityType.byString(stack.getTag().getString("toy")).orElse(null);
            if (type != null) {
                ToyRobotEntity toys = ToyRobotEntity.create(world);
                if (toys != null) {
                    if (withInfo) {
                        toys.load(stack.getTag());
                    } else if (!type.canSummon()) {
                        return null;
                    }
                    return toys;
                }
            }
        }
        return null;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("Wind up your toy with right clicks after placing! \nUse dye to swap to 16 different variants \nUse White Wool to muffle the clicking, shears to remove").withStyle(ChatFormatting.DARK_RED));
        } else {
            components.add(Component.literal("Shift-Right-Click with an empty hand to pick up").withStyle(ChatFormatting.DARK_AQUA));
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
