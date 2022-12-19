package net.vashal.tistheseason.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.vashal.tistheseason.items.TTS_Items;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class LollipopItem extends Item {
    public LollipopItem(Properties pProperties) {
        super(pProperties);
    }


    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, pEntityLiving);
        int random = new Random().nextInt((16-1)+1);
        if (random != 0) {
            return pEntityLiving instanceof Player && ((Player) pEntityLiving).getAbilities().instabuild ? itemstack : new ItemStack(TTS_Items.LOLLIPOP.get());
        }
        return pStack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        components.add(Component.literal("How many licks?").withStyle(ChatFormatting.DARK_RED));

        super.appendHoverText(itemStack, level, components, isAdvanced);
    }
}