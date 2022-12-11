package net.vashal.tistheseason.block.entity;

import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.ChunkPos;
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
    public static final String TAG_NAME = "name";
    public Component name = Component.literal("");

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
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            sync();
        }
    }

    public void sync() {
        Packet<ClientGamePacketListener> packet = this.getUpdatePacket();

        if(packet != null && this.getLevel() instanceof ServerLevel serverLevel) {
            serverLevel.getChunkSource().chunkMap
                    .getPlayers(new ChunkPos(this.getBlockPos()), false)
                    .forEach(e -> e.connection.send(packet));
        }
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);
        name = Component.translatable(compound.getString(TAG_NAME));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putString(TAG_NAME, name.getString());
    }


    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return name.getString().isEmpty() ? null : name;
    }

    @Override
    public @NotNull Component getName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        if (hasCustomName()) {
            Component customName = getCustomName();
            if (customName != null)
                return customName;
        }
        return getName();
    }

}