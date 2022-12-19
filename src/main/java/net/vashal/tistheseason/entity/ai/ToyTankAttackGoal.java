package net.vashal.tistheseason.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.vashal.tistheseason.entity.custom.ToyTankEntity;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ToyTankAttackGoal extends RangedAttackGoal {
    private final Mob mob;
    private final ToyTankEntity toyTank;
    @Nullable
    private LivingEntity target;
    private int attackTime = -1;
    private final double speedModifier;
    private int seeTime;
    private final int attackIntervalMin;
    private final int attackIntervalMax;
    private final float attackRadius;
    private final float attackRadiusSqr;

    public ToyTankAttackGoal(ToyTankEntity pToyTank, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
        this(pToyTank, pSpeedModifier, pAttackInterval, pAttackInterval, pAttackRadius);
    }

    public ToyTankAttackGoal(ToyTankEntity pToyTank, double pSpeedModifier, int pAttackIntervalMin, int pAttackIntervalMax, float pAttackRadius) {
        super(pToyTank, pSpeedModifier, pAttackIntervalMin, pAttackIntervalMax, pAttackRadius);
        this.toyTank = pToyTank;
        this.mob = pToyTank;
        this.speedModifier = pSpeedModifier;
        this.attackIntervalMin = pAttackIntervalMin;
        this.attackIntervalMax = pAttackIntervalMax;
        this.attackRadius = pAttackRadius;
        this.attackRadiusSqr = pAttackRadius * pAttackRadius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null && livingentity.isAlive()) {
            this.target = livingentity;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void stop() {
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
        this.toyTank.setAttackState(0);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.canUse()) return true;
        assert this.target != null;
        return this.target.isAlive() && !this.mob.getNavigation().isDone();
    }

    @Override
    public void tick() {
        assert this.target != null;
        double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean flag = this.mob.getSensing().hasLineOfSight(this.target);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (!(d0 > (double) this.attackRadiusSqr) && this.seeTime >= 5) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.target, this.speedModifier);
        }
        this.mob.getLookControl().setLookAt(this.target, 10.0F, 10.0F);
        if (--this.attackTime == 0) {
            if (!flag) {
                return;
            }
            float f = (float) Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);

            this.toyTank.performRangedAttack(this.target, f1);
            this.attackTime = 60;
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, this.attackIntervalMin, this.attackIntervalMax));
        } else if (this.attackTime > 10) {
            this.toyTank.setAttackState(0);
        } else if (this.attackTime == 10) {
            this.toyTank.setAttackState(1);
        }
    }
}
