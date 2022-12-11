package net.vashal.tistheseason.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
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
import java.util.List;
import java.util.function.Predicate;

public class WaterStream extends AbstractArrow implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int life;
    private ItemStack waterStream = new ItemStack(TTS_Items.WATER_STREAM.get());
    public static final Predicate<LivingEntity> WATER_SENSITIVE = LivingEntity::isSensitiveToWater;

    public WaterStream(EntityType<? extends WaterStream> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public WaterStream(Level pLevel, LivingEntity pShooter) {
        super(TTS_EntityTypes.WATER_STREAM.get(), pShooter, pLevel);
        this.pickup = Pickup.DISALLOWED;
    }


    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 10) {
            this.discard();
        }

    }

    public SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();

    @Override
    protected void onHitBlock(@NotNull BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        Direction direction = hitResult.getDirection();
        BlockPos pos = hitResult.getBlockPos();
        BlockPos pos1 = pos.relative(direction);
        this.setSoundEvent(SoundEvents.POINTED_DRIPSTONE_DRIP_WATER);
        this.dowseFire(pos1);
        this.dowseFire(pos1.relative(direction.getOpposite()));
        for(Direction direction1 : Direction.Plane.HORIZONTAL) {
            this.dowseFire(pos1.relative(direction1));
            this.dowseFire(pos.relative(direction1));
        }

    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.WATER_AMBIENT;
    }


    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        applyWater();
    }


    public void tick() {
        super.tick();
        if (this.level.isClientSide()) {
            this.level.addParticle(ParticleTypes.SPLASH, true, this.getX(), this.getY() + 0.1D, this.getZ(), 0, 0, 0);
        }
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return this.waterStream.copy();
    }


    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<WaterStream> spinController = new AnimationController<>(this, "spinController", 0, this::spinPredicate);
        data.addAnimationController(spinController);
    }


    private <E extends IAnimatable> PlayState spinPredicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.spin", ILoopType.EDefaultLoopTypes.LOOP));
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

    private void applyWater() {
        AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, aabb, WATER_SENSITIVE);
        if (!list.isEmpty()) {
            for(LivingEntity livingentity : list) {
                double d0 = this.distanceToSqr(livingentity);
                if (d0 < 16.0D && livingentity.isSensitiveToWater()) {
                    livingentity.hurt(DamageSource.indirectMagic(this, this.getOwner()), 4.0F);
                }
            }
        }

        for(Axolotl axolotl : this.level.getEntitiesOfClass(Axolotl.class, aabb)) {
            axolotl.rehydrate();
        }

    }

    private void dowseFire(BlockPos pPos) {
        BlockState blockstate = this.level.getBlockState(pPos);
        if (blockstate.is(BlockTags.FIRE)) {
            this.level.removeBlock(pPos, false);
        } else if (AbstractCandleBlock.isLit(blockstate)) {
            AbstractCandleBlock.extinguish(null, blockstate, this.level, pPos);
        } else if (CampfireBlock.isLitCampfire(blockstate)) {
            this.level.levelEvent(null, 1009, pPos, 0);
            CampfireBlock.dowse(this.getOwner(), this.level, pPos, blockstate);
            this.level.setBlockAndUpdate(pPos, blockstate.setValue(CampfireBlock.LIT, Boolean.FALSE));
        } else if (blockstate.getBlock() == Blocks.FARMLAND
                && blockstate.getValue(FarmBlock.MOISTURE) < 7) {
            BlockState newBlockState = blockstate.getBlock().defaultBlockState()
                    .setValue(FarmBlock.MOISTURE, 7);
            this.level.setBlock(pPos, newBlockState, 3);
        }

    }
}
