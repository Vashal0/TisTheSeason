package net.vashal.tistheseason.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.UUID;

public class RockingHorseBlockEntity extends BlockEntity implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private UUID getPlayer;


    //Desyncs all the time, I don't have a clue how to fix it. Hopefully a kind hearted soul will fix it after the modjam
    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().transitionLengthTicks = 0;
        Level level = this.getLevel();
        assert level != null;
        if (this.getPlayer != null) {
            Player player = level.getPlayerByUUID(this.getPlayer);
            if (player == null) {
                return PlayState.STOP;
            }
                if (player.isPassenger()) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.rock", ILoopType.EDefaultLoopTypes.LOOP));
                    return PlayState.CONTINUE;
                }
            }
        return PlayState.STOP;
    }


    public RockingHorseBlockEntity(BlockPos pos, BlockState state) {
        super(TTSBlockEntities.ROCKING_HORSE.get(), pos, state);
    }

    public void onRiding(Player player) {
        getPlayer = UUIDUtil.getOrCreatePlayerUUID(player.getGameProfile());
    }



    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }

}