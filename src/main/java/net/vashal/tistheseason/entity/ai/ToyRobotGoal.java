package net.vashal.tistheseason.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.vashal.tistheseason.entity.custom.WindUpToys;

import java.util.EnumSet;

public abstract class ToyRobotGoal extends Goal {

    protected final WindUpToys toys;
    protected final float moveSpeed;
    private int timeToRecalcPath;
    private float oldWaterCost;


    protected ToyRobotGoal(WindUpToys toy, float speed) {
        toys = toy;
        moveSpeed = speed;
        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    protected PathNavigation getNavigator() {
        return toys.getNavigation();
    }

    protected Level getWorld() {
        return toys.getCommandSenderWorld();
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        oldWaterCost = toys.getPathfindingMalus(BlockPathTypes.WATER);
        toys.setPathfindingMalus(BlockPathTypes.WATER, 0);
    }

    @Override
    public void stop() {
        getNavigator().stop();
        toys.setPathfindingMalus(BlockPathTypes.WATER, oldWaterCost);
    }

    protected void updateTask(Entity target) {
        toys.getLookControl().setLookAt(target, 6, toys.getMaxHeadXRot() / 10F);
        if (--timeToRecalcPath <= 0) {
            timeToRecalcPath = 10;
            if (!toys.isPassenger()) {
                if (toys.distanceToSqr(target) >= 144.0) {
                    BlockPos targetPos = target.blockPosition();
                    for (int i = 0; i < 10; i++) {
                        if (tryPathTo(target, targetPos.getX() + randomIntInclusive(-3, 3), targetPos.getY() + randomIntInclusive(-1, 1), targetPos.getZ() + randomIntInclusive(-3, 3))) {
                            return;
                        }
                    }
                } else {
                    getNavigator().moveTo(target, moveSpeed);
                }
            }
        }
    }

    private int randomIntInclusive(int pMin, int pMax) {
        return toys.getRandom().nextInt(pMax - pMin + 1) + pMin;
    }


    private boolean tryPathTo(Entity target, int x, int y, int z) {
        if (Math.abs(x - target.getX()) < 2 && Math.abs(z - target.getZ()) < 2 || !canWalk(new BlockPos(x, y, z))) {
            return false;
        }
        toys.moveTo(x + 0.5, y, z + 0.5, toys.getYRot(), toys.getXRot());
        getNavigator().stop();
        return true;
    }

    private boolean canWalk(BlockPos pPos) {
        Level world = getWorld();
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(world, pPos.mutable());
        if (blockpathtypes == BlockPathTypes.WALKABLE) {
            BlockPos blockpos = pPos.subtract(toys.blockPosition());
            return world.noCollision(toys, toys.getBoundingBox().move(blockpos));
        }
        return false;
    }
}
