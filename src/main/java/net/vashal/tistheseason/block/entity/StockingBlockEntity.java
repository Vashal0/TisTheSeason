package net.vashal.tistheseason.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.vashal.tistheseason.screen.StockingContainerMenu;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class StockingBlockEntity extends RandomizableContainerBlockEntity {

    private UUID owner = null;

    protected NonNullList<ItemStack> items;

    public StockingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(TTSBlockEntities.STOCKING.get(), pPos, pBlockState);
        this.items = NonNullList.withSize(14, ItemStack.EMPTY);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("block.tistheseason.stocking");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new StockingContainerMenu(pContainerId, pInventory, this);
    }

    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items, false);
        }
        if (this.owner != null)
            tag.putUUID("owner", this.owner);
    }

    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag) && tag.contains("Items", 9)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
        if (tag.contains("owner")) {
            this.owner = tag.getUUID("owner");
        } else this.owner = null;
    }

    @Override
    public boolean canOpen(@NotNull Player pPlayer) {
        if (pPlayer.isCreative()) return true;
        return this.isOwnedBy(pPlayer);
    }

    @Nullable
    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        if (this.level != null) {
            if (owner != null) {
                this.owner = owner;
            }
            this.setChanged();
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }


    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    public void clearOwner() {
        this.owner = null;
    }

    public boolean isOwnedBy(Player player) {
        UUID id = this.getOwner();
        return (id != null && id.equals(player.getUUID()));
    }

    private final LazyOptional<?> itemHandler = LazyOptional.of(this::createUnSidedHandler);

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER)
            return itemHandler.cast();
        return super.getCapability(cap, side);
    }
}


