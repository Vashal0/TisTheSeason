package net.vashal.tistheseason.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.network.PacketDistributor;
import net.vashal.tistheseason.entity.projectile.IronBall;
import net.vashal.tistheseason.items.custom.client.PopGunRenderer;
import net.vashal.tistheseason.sounds.TTS_Sounds;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class PopGunItem extends Item implements IAnimatable, ISyncable {

    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public String controllerName = "controller";
    public static final int ANIM_OPEN = 0;

    public PopGunItem(Properties properties) {
        super(properties);
        GeckoLibNetwork.registerSyncable(this);
    }


    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new PopGunRenderer();
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
                player.getCooldowns().addCooldown(this, 40);
                if (!worldIn.isClientSide) {
                    IronBall ironBall = createBall(worldIn, player);
                    ironBall.shootFromRotation(player, player.getXRot(), player.getYRot(),
                            0.0F, 4.0F, 0F);
                    worldIn.addFreshEntity(ironBall);
                    int j = stack.getEnchantmentLevel(Enchantments.POWER_ARROWS);
                    if (j > 0) {
                        ironBall.setBaseDamage(ironBall.getBaseDamage() + (double) j + 1D);
                    }

                    int k = stack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
                    if (k > 0) {
                        ironBall.setKnockback(k);
                    }

                    if (stack.getEnchantmentLevel(Enchantments.FLAMING_ARROWS) > 0) {
                        ironBall.setSecondsOnFire(100);
                    }
                }

                if (!worldIn.isClientSide) {
                    final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerLevel) worldIn);
                    final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF
                            .with(() -> player);
                    GeckoLibNetwork.syncAnimation(target, this, id, ANIM_OPEN);
                }
            }
        }
    }


    public IronBall createBall(Level worldIn, LivingEntity shooter) {
        return new IronBall(worldIn, shooter);
    }


    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController controller = new AnimationController(this, controllerName, 1, this::predicate);
        data.addAnimationController(controller);
        controller.registerSoundListener(this::soundListener);
    }

    private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.playSound(TTS_Sounds.CORK.get(), 1, 1);
        }
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_OPEN) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, controllerName);
            if (controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.model.shoot", EDefaultLoopTypes.PLAY_ONCE));
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }


    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> components, @NotNull TooltipFlag flagIn) {
        components.add(Component.literal("Packs a punch even if it's a toy").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
        super.appendHoverText(stack, worldIn, components, flagIn);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if(enchantment == Enchantments.POWER_ARROWS || enchantment == Enchantments.PUNCH_ARROWS || enchantment == Enchantments.FLAMING_ARROWS) {
            return true;
        }
        return enchantment.category.canEnchant(stack.getItem());
    }

}