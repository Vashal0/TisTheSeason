package net.vashal.tistheseason.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vashal.tistheseason.block.entity.StockingBlockEntity;
import net.vashal.tistheseason.capabilities.TTSCapabilities;
import net.vashal.tistheseason.items.TTSItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class StockingBlock extends BaseEntityBlock implements EntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape STOCKING_NORTH_SHAPE = Block.box(4.0D, 0.0D, 13.0D, 12.0D, 14.0D, 16.0D);
    protected static final VoxelShape STOCKING_EAST_SHAPE = Block.box(0.0D, 0.0D, 3.0D, 3.0D, 14.0D, 13.0D);
    protected static final VoxelShape STOCKING_WEST_SHAPE = Block.box(13.0D, 0.0D, 3.0D, 16.0D, 14.0D, 13.0D);
    protected static final VoxelShape STOCKING_SOUTH_SHAPE = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 14.0D, 3.0D);


    public StockingBlock() {
        super(Properties.of(Material.WOOL).noOcclusion().noCollission().strength(2));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = this.defaultBlockState();
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for(Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(FACING, direction1);
                if (blockstate.canSurvive(levelreader, blockpos)) {
                    return blockstate;
                }
            }
        }
        return null;
    }

    public boolean canSurvive(@NotNull BlockState pState, LevelReader pLevel, @NotNull BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof Container stocking) {
            if (!stocking.isEmpty()) {
                return true;
            }
        }
        return pLevel.getBlockState(pPos.relative(pState.getValue(FACING).getOpposite())).getMaterial().isSolid();
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (player.isCreative()) {
            return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
        }
        if ((level.getBlockEntity(pos) instanceof StockingBlockEntity stocking)) {
            if (stocking.getOwner() != null) {
                if (!stocking.isOwnedBy(player)) {
                    player.displayClientMessage(Component.translatable("I can't break that, that's someone else's stocking"), true);
                    return false;
                }
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case EAST -> STOCKING_EAST_SHAPE;
            case SOUTH -> STOCKING_SOUTH_SHAPE;
            case WEST -> STOCKING_WEST_SHAPE;
            default -> STOCKING_NORTH_SHAPE;
        };
    }

    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        return pFacing == pState.getValue(FACING).getOpposite() && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }


    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Deprecated
    @Override
    public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
        return 1;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            if (worldIn.getBlockEntity(pos) instanceof StockingBlockEntity stocking) {
                UUID owner = stocking.getOwner();
                if (owner == null) {
                    player.openMenu(stocking);
                    PiglinAi.angerNearbyPiglins(player, true);
                } else if (!owner.equals(player.getUUID())) {
                    player.displayClientMessage(Component.translatable("That stocking doesn't belong to me"), true);
                    if (!player.isCreative()) return InteractionResult.CONSUME;
                } else if (!stocking.canOpen(player)) {
                    return InteractionResult.CONSUME;
                }
                player.openMenu(stocking);
                PiglinAi.angerNearbyPiglins(player, true);
            }
            return InteractionResult.CONSUME;
        }
    }
    @Override
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }
    @Override
    public void onRemove(BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            worldIn.updateNeighbourForOutputSignal(pos, state.getBlock());
            BlockEntity blockentity = worldIn.getBlockEntity(pos);
            if (blockentity instanceof Container) {
                Containers.dropContents(worldIn, pos, (Container)blockentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos) {
        return worldIn.getBlockEntity(pos) instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StockingBlockEntity(blockPos, blockState);
    }

    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    @Override
    public @NotNull VoxelShape getBlockSupportShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public void setPlacedBy(Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        if (worldIn.getBlockEntity(pos) instanceof StockingBlockEntity entity) {
            if (stack.hasCustomHoverName()) {
                entity.setCustomName(stack.getHoverName());
            }
            if (placer instanceof Player) {
                if (entity.getOwner() == null) {
                    entity.setOwner(placer.getUUID());
                    if (entity.getOwner() != null) {
                        placer.getCapability(TTSCapabilities.NAUGHTY_OR_NICE).ifPresent(niceScore -> {
                            if (niceScore.hasStocking()) {
                                if (worldIn.getBlockEntity(niceScore.getStocking()) instanceof StockingBlockEntity stocking) {
                                    stocking.clearOwner();
                                    entity.setOwner(placer.getUUID());
                                }
                            }
                            niceScore.removeStocking();
                            niceScore.setStocking(entity.getBlockPos());
                        });
                    }
                }
            }
        }
    }
    @Override
    public void playerWillDestroy(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return false;
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        return SoundType.WOOL;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof StockingBlockEntity stocking) {
            if (stocking.hasCustomName()) {
                ItemStack stack = TTSItems.STOCKING_ITEM.get().getDefaultInstance();
                stack.setHoverName(stocking.getName());
                return stack;
            }
        }
        return TTSItems.STOCKING_ITEM.get().getDefaultInstance();
    }
}