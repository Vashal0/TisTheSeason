package net.vashal.tistheseason.items.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.vashal.tistheseason.entity.projectile.WaterStream;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SuperSoakerItem extends Item {

    public static final int WATER_USED = 100;

    public SuperSoakerItem(Properties pProperties) {
        super(pProperties.defaultDurability(10000));
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        Level worldIn = player.level;
        if (player.tickCount % 2 == 0) {
            if (player instanceof Player playerentity) {
                if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
                    playerentity.getCooldowns().addCooldown(this, 2);
                    if (!worldIn.isClientSide) {
                        WaterStream waterStream = createWater(worldIn, playerentity);
                        waterStream = water(waterStream);
                        waterStream.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(),
                                0.0F, 3.0F, 0F);
                        stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
                        worldIn.addFreshEntity(waterStream);
                        this.useDurability(stack, worldIn, playerentity);
                    }
                } else {
                    this.useDurability(stack, worldIn, playerentity);
                }
            }
        }
    }


    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }


    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    public void useDurability(ItemStack itemStack, Level world, @Nullable Player player) {
        if (this.getMaxDamage(itemStack) > 0) {
            InteractionResult waterCheckResult = this.checkRefill(itemStack, world, player).getResult();
            if (waterCheckResult == InteractionResult.SUCCESS) {
                return;
            } else if (waterCheckResult == InteractionResult.FAIL) {
                return;
            }
            if (this.getMaxDamage(itemStack) - itemStack.getDamageValue() < WATER_USED) {
                return;
            }
            int durability = itemStack.getDamageValue() + WATER_USED;
            durability = Math.min(durability, this.getMaxDamage(itemStack));
            itemStack.setDamageValue(durability);
        }

    }

    public WaterStream createWater(Level worldIn, LivingEntity shooter) {
        return new WaterStream(worldIn, shooter);
    }

    public WaterStream water(WaterStream waterStream) {
        return waterStream;
    }

    @Nonnull
    private InteractionResultHolder<ItemStack> checkRefill(@Nonnull ItemStack itemStack, Level world, Player player) {
        BlockHitResult rayTraceResult = Item.getPlayerPOVHitResult(world, player, ClipContext.Fluid.SOURCE_ONLY);
        if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = rayTraceResult.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getMaterial() == Material.WATER) {
                if (!world.isClientSide()) {
                    int damage = itemStack.getDamageValue() - 1000;
                    damage = Math.max(0, damage);
                    itemStack.setDamageValue(damage);
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 1, 1);
                }
                return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, itemStack);
    }
}
