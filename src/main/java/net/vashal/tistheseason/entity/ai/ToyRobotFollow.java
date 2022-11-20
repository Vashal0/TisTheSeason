package net.vashal.tistheseason.entity.ai;

import net.minecraft.world.entity.player.Player;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;

/*
public class ToyRobotFollow extends ToyRobotGoal {

    private Player owner;
    private final float maxDistance;
    private final float minDistance;

    public ToyRobotFollow(ToyRobotEntity robotToy, float speed, float min, float max) {
        super(robotToy, speed);
        minDistance = min;
        maxDistance = max;
    }

    @Override
    public boolean canUse() {
        Player player = toyRobot.getOwner();
        if (player == null || player.isSpectator()) {
            return false;
        } else if (toyRobot.level.dimension() != player.level.dimension()) {
            return false;
        } else if (toyRobot.distanceToSqr(owner) < (minDistance * minDistance)) {
            return false;
        } else if (!toyRobot.getActivatedStatus()) {
            return false;
        }
        owner = player;
        return true;
    }

    public boolean canContinueToUse() {
        return !getNavigator().isDone() && toyRobot.distanceToSqr(owner) > (maxDistance * maxDistance)
                && toyRobot.getActivatedStatus() && owner.level.dimension() == toyRobot.level.dimension();
    }

    @Override
    public void stop() {
        owner = null;
        super.stop();
    }

    @Override
    public void tick() {
        updateTask(owner);
    }
}

 */
