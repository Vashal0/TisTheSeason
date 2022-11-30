package net.vashal.tistheseason.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.items.TTS_Items;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nonnull;

public class IronBall extends AbstractArrow implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int life;
    private ItemStack ironBallItem = new ItemStack(TTS_Items.IRON_BALL_ITEM.get());

    public IronBall(EntityType<? extends IronBall> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public IronBall(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(TTS_EntityTypes.IRON_BALL.get(), pShooter, pLevel);
        this.ironBallItem = pStack.copy();
    }


    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.LADDER_HIT;
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 40) {
            this.discard();
        }

    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        entity.hurt(DamageSource.thrown(this, this.getOwner()), 4.0f);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return this.ironBallItem.copy();
    }


    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    //

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide()) {
            double x = this.getX() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
            double z = this.getZ() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
            this.level.addParticle(ParticleTypes.SMALL_FLAME, true, x, this.getY(), z, 0, 0, 0);
        }
    }
}
