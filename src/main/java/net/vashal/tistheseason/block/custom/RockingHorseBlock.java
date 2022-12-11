package net.vashal.tistheseason.block.custom;

import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vashal.tistheseason.block.entity.RockingHorseBlockEntity;
import net.vashal.tistheseason.block.entity.TTSBlockEntities;
import net.vashal.tistheseason.entity.TTS_EntityTypes;
import net.vashal.tistheseason.entity.custom.DummyEntityForSitting;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class RockingHorseBlock extends BaseEntityBlock implements EntityBlock {

    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final EnumProperty<HorseSide> TYPE = EnumProperty.create("model", HorseSide.class);

    public RockingHorseBlock() {
        super(Properties.of(Material.WOOD).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(OCCUPIED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }


    @Override
    public @NotNull VoxelShape getBlockSupportShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    @Deprecated
    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos otherpos = pos.relative(state.getValue(FACING).getOpposite());
        return worldIn.getBlockState(otherpos).getMaterial().isReplaceable();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OCCUPIED, TYPE);
    }

    @Deprecated
    @Override
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void playerWillDestroy(Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!worldIn.isClientSide) {
            HorseSide horseSide = state.getValue(TYPE);
            if (horseSide == HorseSide.MAIN) {
                BlockPos otherpos = pos.relative(state.getValue(FACING).getOpposite());
                BlockState otherstate = worldIn.getBlockState(otherpos);
                if (otherstate.getBlock() == this) {
                    worldIn.setBlock(otherpos, Blocks.AIR.defaultBlockState(), 35);
                    worldIn.levelEvent(player, 2001, otherpos, Block.getId(otherstate));
                }
            }
            if (horseSide == HorseSide.BACK) {
                BlockPos otherpos = pos.relative(state.getValue(FACING));
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
            BlockPos blockpos = pos.relative(state.getValue(FACING).getOpposite());
            worldIn.setBlock(blockpos, state.setValue(TYPE, HorseSide.BACK), 3);
            worldIn.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(worldIn, pos, 3);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facingDir = context.getClickedFace();
        Direction placementDir;
        if (facingDir == Direction.DOWN || facingDir == Direction.UP) {
            placementDir = context.getHorizontalDirection().getOpposite();
        } else {
            placementDir = facingDir.getClockWise();
        }

        BlockState blockstate = this.defaultBlockState().setValue(FACING, placementDir).setValue(OCCUPIED, false);
        return blockstate;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(TYPE) == HorseSide.BACK) {
            return TTSBlockEntities.ROCKING_HORSE.get().create(blockPos, blockState);
        }
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 15, 16);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(handIn);
        BlockState upperBlock = worldIn.getBlockState(pos.above());
        if (worldIn.getBlockEntity(pos) instanceof RockingHorseBlockEntity horse) {
            horse.onRiding(player);
        }
            boolean canSit = hit.getDirection() == Direction.UP && !state.getValue(OCCUPIED) && heldItem.isEmpty() && upperBlock.isAir() && isPlayerInRange(player, pos) && state.getValue(TYPE) == HorseSide.BACK && !player.isPassenger();
            if (!worldIn.isClientSide()) {
                if (canSit) {
                    DummyEntityForSitting seat = TTS_EntityTypes.DUMMY_ENTITY_TYPE.get().create(worldIn);
                    seat.setSeatPos(pos);
                    worldIn.addFreshEntity(seat);
                    player.startRiding(seat);

                    return InteractionResult.SUCCESS;
                }
            }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    private static boolean isPlayerInRange(Player player, BlockPos pos) {
        BlockPos playerPos = player.blockPosition();
        int blockReachDistance = 2;

        pos = pos.offset(0.5D, 0.5D, 0.5D);
        AABB range = new AABB(pos.getX() + blockReachDistance, pos.getY() + blockReachDistance, pos.getZ() + blockReachDistance, pos.getX() - blockReachDistance, pos.getY() - blockReachDistance, pos.getZ() - blockReachDistance);
        playerPos = playerPos.offset(0.5D, 0.5D, 0.5D);
        return range.minX <= playerPos.getX() && range.minY <= playerPos.getY() && range.minZ <= playerPos.getZ() && range.maxX >= playerPos.getX() && range.maxY >= playerPos.getY() && range.maxZ >= playerPos.getZ();
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        List<DummyEntityForSitting> entities = worldIn.getEntitiesOfClass(DummyEntityForSitting.class, new AABB(x, y, z, x + 1, y + 1, z + 1));
        for (DummyEntityForSitting entity : entities) {
            entity.remove(Entity.RemovalReason.DISCARDED);
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_) {
        return false;
    }


    public enum HorseSide implements StringRepresentable {
        MAIN, BACK;

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