package net.vashal.tistheseason.entity.ai;

import net.minecraft.world.entity.player.Player;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;


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
        if (toyRobot.getOwner() instanceof Player) {
            this.owner = (Player) toyRobot.getOwner();
            if (this.owner == null || this.owner.isSpectator()) {
                return false;
            } else if (toyRobot.level.dimension() != this.owner.level.dimension()) {
                return false;
            } else if (toyRobot.distanceToSqr(this.owner) < (minDistance * minDistance)) {
                return false;
            } else return toyRobot.getActivatedStatus();
        } else {
            return false;
        }
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

