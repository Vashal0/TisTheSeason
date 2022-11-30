package net.vashal.tistheseason.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.vashal.tistheseason.block.entity.TTS_BlockEntities;
import net.vashal.tistheseason.block.entity.ToyWorkbenchBlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class ToyWorkbench extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape WORKBENCH_NORTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    protected static final VoxelShape WORKBENCH_EAST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    protected static final VoxelShape WORKBENCH_WEST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    protected static final VoxelShape WORKBENCH_SOUTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);


    public static final EnumProperty<WorkbenchType> TYPE = EnumProperty.create("model", WorkbenchType.class);

    public ToyWorkbench(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, WorkbenchType.MAIN));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }





    @Override
    public void playerWillDestroy(Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!worldIn.isClientSide) {
            WorkbenchType workbenchModel = state.getValue(TYPE);
            if (workbenchModel == WorkbenchType.MAIN) {
                BlockPos otherpos = pos.relative(state.getValue(FACING).getCounterClockWise());
                BlockState otherstate = worldIn.getBlockState(otherpos);
                if (otherstate.getBlock() == this) {
                    worldIn.setBlock(otherpos, Blocks.AIR.defaultBlockState(), 35);
                    worldIn.levelEvent(player, 2001, otherpos, Block.getId(otherstate));
                }
            }
            if (workbenchModel == WorkbenchType.SIDE) {
                BlockPos otherpos = pos.relative(state.getValue(FACING).getClockWise());
                BlockState otherstate = worldIn.getBlockState(otherpos);
                if (otherstate.getBlock() == this) {
                    worldIn.setBlock(otherpos, Blocks.AIR.defaultBlockState(), 35);
                    worldIn.levelEvent(player, 2001, otherpos, Block.getId(otherstate));
                }
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void setPlacedBy(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isClientSide) {
            BlockPos blockpos = pos.relative(state.getValue(FACING).getCounterClockWise());
            worldIn.setBlock(blockpos, state.setValue(TYPE, WorkbenchType.SIDE), 3);
            worldIn.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(worldIn, pos, 3);
        }
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> WORKBENCH_NORTH_SHAPE;
            case SOUTH -> WORKBENCH_SOUTH_SHAPE;
            case WEST -> WORKBENCH_WEST_SHAPE;
            default -> WORKBENCH_EAST_SHAPE;
        };
    }

    @Deprecated
    @Override
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public @NotNull VoxelShape getBlockSupportShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }


    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Deprecated
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos otherpos = pos.relative(state.getValue(FACING).getCounterClockWise());
        return worldIn.getBlockState(otherpos).getMaterial().isReplaceable();
    }

    @Deprecated
    @Override
    public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
        return 1;
    }

    //BE

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockstate) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ToyWorkbenchBlockEntity) {
                ((ToyWorkbenchBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, @NotNull BlockPos pPos,
                                          @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        WorkbenchType workbenchModel = pState.getValue(TYPE);
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof ToyWorkbenchBlockEntity) {
                if (workbenchModel == WorkbenchType.MAIN) {
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (ToyWorkbenchBlockEntity) entity, pPos);
                    return InteractionResult.CONSUME;
                } else {
                    BlockPos otherpos = pPos.relative(pState.getValue(FACING).getClockWise());
                    BlockEntity pEntity = pLevel.getBlockEntity(otherpos);
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (ToyWorkbenchBlockEntity) pEntity, otherpos);
                    }
            } else {
                throw new IllegalStateException("Uh Oh");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ToyWorkbenchBlockEntity(pPos, pState);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, TTS_BlockEntities.TOY_WORKBENCH.get(),
                ToyWorkbenchBlockEntity::tick);
    }

    public enum WorkbenchType implements StringRepresentable {
        MAIN, SIDE;

        @Override
        public @NotNull String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }

        @Override
        public String toString() {
            return getSerializedName();
        }
    }
}
