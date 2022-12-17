package net.vashal.tistheseason.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.vashal.tistheseason.entity.custom.ToyTankEntity;
import net.vashal.tistheseason.entity.variant.ToyTankVariant;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ToyTankItem extends Item {

    public ToyTankItem(Properties pProperties) {
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
            ToyTankEntity toyTank = new ToyTankEntity(worldIn);
            if (stack.hasTag()) {
                assert stack.getTag() != null;
                toyTank.load(stack.getTag());

            }
            BlockPos blockPos = pos.relative(facing);
            toyTank.absMoveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
            stack.setTag(new CompoundTag());
            worldIn.addFreshEntity(toyTank);
            toyTank.setVariant(ToyTankVariant.byId(new Random().nextInt((2-1)+1)));
            if (toyTank.getOwner() == null) {
                toyTank.tame(player);
            }
            return false;
        }
        ToyTankEntity tank = getEntityFromStack(stack, worldIn, true);
        BlockPos blockPos = pos.relative(facing);
        assert tank != null;
        tank.absMoveTo(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
        stack.setTag(new CompoundTag());
        worldIn.addFreshEntity(tank);
        stack.shrink(1);

        return true;
    }

    public boolean containsEntity(ItemStack stack) {
        if (stack.isEmpty() || !stack.hasTag()) return false;
        assert stack.getTag() != null;
        return stack.getTag().contains("toy");
    }

    @Nullable
    public ToyTankEntity getEntityFromStack(ItemStack stack, Level world, boolean withInfo) {
        if (stack.hasTag()) {
            assert stack.getTag() != null;
            EntityType<?> type = EntityType.byString(stack.getTag().getString("toy")).orElse(null);
            if (type != null ) {
                ToyTankEntity toys = ToyTankEntity.create(world);
                if (withInfo) {
                    assert toys != null;
                    toys.load(stack.getTag());
                } else if (!type.canSummon()) {
                    return null;
                }
                return toys;
            }
        }
        return null;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("While in turret mode").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.UNDERLINE));
            components.add(Component.literal("More TNT = More range\nRight-Click with an empty hand to change direction\nUse gunpowder to add ammo\nUse shears to exit turret mode").withStyle(ChatFormatting.DARK_GREEN));
        } else {
            components.add(Component.literal("Give your tank TNT to swap it into stationary turret mode!\n\nShift-Right-Click with an empty hand to pick up").withStyle(ChatFormatting.DARK_RED));
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        }
        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}
