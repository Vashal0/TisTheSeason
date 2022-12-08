package net.vashal.tistheseason.items.custom.curios;

import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class ReindeerSlipperItem extends TTSCurios {

    public ReindeerSlipperItem(Properties properties) {
        super(properties);
        addListener(EventPriority.HIGH, LivingFallEvent.class, this::onLivingFall);
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        if (slotContext.entity() instanceof Player player) {
            Vec3 vec3 = player.getDeltaMovement();
            if (!player.isOnGround() && vec3.y < 0.0D && !player.isShiftKeyDown()) {
                player.setDeltaMovement(vec3.multiply(1.0D, 0.5D, 1.0D));
                player.flyingSpeed += player.flyingSpeed * 1.5f;
                if (player.tickCount % 2 == 0) {
                    player.level.addParticle(ParticleTypes.SNOWFLAKE, true, player.getRandomX(1D), player.getY() - 0.35d, player.getRandomZ(1D), -0, -0.2, -0);
                }
            }
        }
    }

    private void onLivingFall(LivingFallEvent event, LivingEntity wearer) {
        event.setDamageMultiplier(0);
    }
}
