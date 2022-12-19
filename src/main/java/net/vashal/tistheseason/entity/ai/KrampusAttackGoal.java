package net.vashal.tistheseason.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.vashal.tistheseason.entity.custom.KrampusEntity;

import java.util.EnumSet;

public class KrampusAttackGoal extends Goal {
    private final KrampusEntity entity;
    private final double moveSpeedAmp;
    private int attackTime = -1;

    public KrampusAttackGoal(KrampusEntity mob, double moveSpeedAmpIn) {
        this.entity = mob;
        this.moveSpeedAmp = moveSpeedAmpIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        return this.entity.getTarget() != null;
    }

    public boolean canContinueToUse() {
        return this.canUse();
    }

    public void start() {
        super.start();
        this.entity.setAggressive(true);
    }

    public void stop() {
        super.stop();
        this.entity.setAggressive(false);
        this.entity.setAttackingState(0);
        this.attackTime = -1;
    }

    public void tick() {
        LivingEntity livingentity = this.entity.getTarget();
        if (livingentity != null) {
            boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingentity);
            this.attackTime++;
            this.entity.lookAt(livingentity, 15.0F, 15.0F);
            final AABB aabb2 = new AABB(this.entity.blockPosition()).inflate(2D);
            if (inLineOfSight) {
                this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
                if (this.attackTime == 12 && this.entity.getAttackingState() == 1) {
                    this.entity.getCommandSenderWorld().getEntities(this.entity, aabb2).forEach(e -> {
                        if ((e == livingentity)) {
                            this.entity.doHurtTarget(livingentity);
                            livingentity.invulnerableTime = 0;
                        }
                    });
                }
                if (this.attackTime == 2) {
                    this.entity.getCommandSenderWorld().getEntities(this.entity, aabb2).forEach(e -> {
                        if ((e == livingentity)) {
                            this.entity.setAttackingState(1);
                        }
                    });
                }
                if (this.attackTime >= 16) {
                    this.attackTime = -1;
                    this.entity.setAttackingState(0);
                }
            }
        }
    }

}
