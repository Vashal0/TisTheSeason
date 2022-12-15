package net.vashal.tistheseason.items.custom.curios;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import top.theillusivec4.curios.api.SlotContext;

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
            if (!player.isOnGround() && vec3.y < 0.0D && !player.isShiftKeyDown() && !player.isInFluidType()) {
                player.setDeltaMovement(vec3.multiply(1.0D, 0.5D, 1.0D));
                player.flyingSpeed += player.flyingSpeed * 1.5f;
                if (player.tickCount % 4 == 0) {
                    if (player.getLevel() instanceof ServerLevel level) {
                        level.sendParticles(ParticleTypes.SNOWFLAKE, player.getRandomX(1), player.getY() - 0.35d, player.getRandomZ(1), 12, 0, 0, 0, 0);
                    }
                }
            }
        }
    }

    private void onLivingFall(LivingFallEvent event, LivingEntity wearer) {
        event.setDamageMultiplier(0);
    }
}
