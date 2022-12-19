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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
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
import javax.annotation.Nullable;

public class WaterStream extends AbstractArrow implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int life;
    private final ItemStack waterStream = new ItemStack(TTS_Items.WATER_STREAM.get());

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
        if (this.life >= 2) {
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
        this.applyWater(pos1);
        this.applyWater(pos1.relative(direction.getOpposite()));
        for(Direction direction1 : Direction.Plane.HORIZONTAL) {
            this.applyWater(pos1.relative(direction1));
            this.applyWater(pos.relative(direction1));
        }

        this.remove(RemovalReason.KILLED);

    }

    @Override
    protected boolean tryPickup(@NotNull Player pPlayer) {
        return false;
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.hitSound = soundIn;
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.WATER_AMBIENT;
    }


    public void setOwner(@Nullable Entity pEntity) {
        super.setOwner(pEntity);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        Entity entity1 = this.getOwner();
        if (entity instanceof LivingEntity livingEntity) {
            if (entity1 != null  && !livingEntity.isSensitiveToWater() && !(entity instanceof Player)) {
                double d0 = Math.max(0.0D, 1.0D - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 1.0D, 1.0D).normalize().scale(2 * 0.6D * d0);
                if (vec3.lengthSqr() > 0.0D) {
                    livingEntity.push(vec3.x, 0.1D + vec3.y, vec3.z);
                    remove(RemovalReason.KILLED);
                }
            }
            if (this.tickCount > 3 && entity instanceof Player player) {
                double d0 = Math.max(0.0D, 1.0D - player.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 1.0D, 1.0D).normalize().scale(2 * 0.6D * d0);
                if (vec3.lengthSqr() > 0.0D) {
                    player.push(vec3.x, vec3.y, vec3.z);
                }
               }

            if (livingEntity.isSensitiveToWater()) {
                livingEntity.hurt(DamageSource.indirectMagic(this, this.getOwner()), 6.0F);
            }
            if (livingEntity.isOnFire()) {
                livingEntity.clearFire();
            }
        }

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



    private void applyWater(BlockPos pPos) {
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
