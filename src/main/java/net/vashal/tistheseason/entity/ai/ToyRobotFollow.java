package net.vashal.tistheseason.entity.ai;

import net.minecraft.world.entity.player.Player;
import net.vashal.tistheseason.entity.custom.WindUpToys;


public class ToyRobotFollow extends ToyRobotGoal {

    private Player owner;
    private final float maxDistance;
    private final float minDistance;

    public ToyRobotFollow(WindUpToys toys, float speed, float min, float max) {
        super(toys, speed);
        minDistance = min;
        maxDistance = max;
    }

    @Override
    public boolean canUse() {
        if (toys.getOwner() instanceof Player) {
            this.owner = (Player) toys.getOwner();
            if (this.owner == null || this.owner.isSpectator()) {
                return false;
            } else if (toys.level.dimension() != this.owner.level.dimension()) {
                return false;
            } else if (toys.distanceToSqr(this.owner) < (minDistance * minDistance)) {
                return false;
            } else return toys.getActivatedStatus();
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        return !getNavigator().isDone() && toys.distanceToSqr(owner) > (maxDistance * maxDistance)
                && toys.getActivatedStatus() && owner.level.dimension() == toys.level.dimension();
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

