package net.vashal.tistheseason.entity.ai;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.vashal.tistheseason.entity.custom.EvilToyRobotEntity;

public class ToyRobotAttackGoal extends MeleeAttackGoal {
    private final EvilToyRobotEntity evilRobot;
    private int raiseArmTicks;

    public ToyRobotAttackGoal(EvilToyRobotEntity pEvilRobot, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pEvilRobot, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.evilRobot = pEvilRobot;
    }

    public void start() {
        super.start();
        this.raiseArmTicks = 0;
    }


    public void stop() {
        super.stop();
        this.evilRobot.setAggressive(false);
    }


    public void tick() {
        super.tick();
        ++this.raiseArmTicks;
        if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
            this.evilRobot.setAggressive(true);
        } else {
            this.evilRobot.setAggressive(false);
        }

    }
}
