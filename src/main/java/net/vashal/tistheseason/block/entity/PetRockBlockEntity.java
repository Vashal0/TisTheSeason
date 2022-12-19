package net.vashal.tistheseason.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PetRockBlockEntity extends BlockEntity implements IAnimatable, Nameable {
    public Component name = null;

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public PetRockBlockEntity(BlockPos pos, BlockState state) {
        super(TTSBlockEntities.PET_ROCK.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimationData data) {
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);
        name = Component.translatable(compound.getString("Name"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);
        if (name != null) {
            compound.putString("Name", name.getString());
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }

    @Override
    public @NotNull Component getName() {
        return Component.translatable("block.tistheseason.pet_rock");
    } //requires further testing

    @Nonnull
    @Override
    public Component getDisplayName() {
        if (hasCustomName()) {
            return this.name;
        }
        return getName();
    }
}