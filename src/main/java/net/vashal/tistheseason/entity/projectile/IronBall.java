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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.items.TTS_Items;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
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
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 40) {
            this.discard();
        }

    }

    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        this.setSoundEvent(SoundEvents.METAL_HIT);
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.METAL_HIT;
    }


    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.arrow(this, this);
        } else {
            damagesource = DamageSource.arrow(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity) entity1).setLastHurtMob(entity);
            }
        }
        boolean flag = entity.getType() == EntityType.ENDERMAN;
        if (entity.hurt(damagesource, 4.0f)) {
            if (flag) {
                return;
            }
            this.playSound(SoundEvents.GENERIC_EXPLODE, 0.1F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            this.remove(RemovalReason.KILLED);
        }
    }


    public void tick() {
        super.tick();
        if (this.level.isClientSide()) {
            double x = this.getX() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
            double z = this.getZ() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
            this.level.addParticle(ParticleTypes.SMALL_FLAME, true, x, this.getY(), z, 0, 0, 0);
        }
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return this.ironBallItem.copy();
    }


    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<IronBall> spinController = new AnimationController<>(this, "idleController", 0, this::spinPredicate);
        data.addAnimationController(spinController);
    }


    private <E extends IAnimatable> PlayState spinPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animations/animation.iron_ball.spin", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
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
}
