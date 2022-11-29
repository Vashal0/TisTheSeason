package net.vashal.tistheseason.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InvisibleRedstone extends Block {


    public InvisibleRedstone(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        worldIn.removeBlock(pos, false);
        worldIn.updateNeighborsAt(pos, this);
        for (Direction d : Direction.values()) {
            worldIn.updateNeighborsAt(pos.relative(d), this);
        }
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, worldIn, pos, oldState, isMoving);
    }

    public boolean isSignalSource(BlockState state) {
        return true;
    }

    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return 15;
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
}

