package net.vashal.tistheseason.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.vashal.tistheseason.entity.custom.ToyTankEntity;

import java.util.EnumSet;

public class TurretTankAttackGoal extends RangedAttackGoal {
    private final Mob mob;
    private final ToyTankEntity toyTank;
    private int attackTime = -1;
    private final int attackIntervalMin;
    private final int attackIntervalMax;


    public TurretTankAttackGoal(ToyTankEntity pToyTank, double pSpeedModifier, int pAttackInterval) {
        this(pToyTank, pSpeedModifier, pAttackInterval, pAttackInterval);
    }

    public TurretTankAttackGoal(ToyTankEntity pToyTank, double pSpeedModifier, int pAttackIntervalMin, int pAttackIntervalMax) {
        super(pToyTank, pSpeedModifier, pAttackIntervalMin, pAttackIntervalMax);
        this.toyTank = pToyTank;
        this.mob = pToyTank;
        this.attackIntervalMin = pAttackIntervalMin;
        this.attackIntervalMax = pAttackIntervalMax;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.toyTank.getTurretMode();
    }

    @Override
    public void stop() {
        this.attackTime = -1;
        this.toyTank.setAttackState(0);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.toyTank.getTurretMode();
    }

    @Override
    public void tick() {

        if (toyTank.getDirectionState() == 0) {
            BlockPos north = toyTank.blockPosition().north(12);
            double d0 = this.mob.distanceToSqr((north.getX()+0.5), north.getY(), (north.getZ()+0.5));
            this.mob.getLookControl().setLookAt((north.getX()+0.5), north.getY(), (north.getZ()+0.5));
            if (--this.attackTime == 0 && this.toyTank.getAmmoCount() > 0) {
                this.toyTank.performRangedAttack(north);
                this.toyTank.setAmmoCount(this.toyTank.getAmmoCount()-1);
                this.attackTime = 60;
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0), this.attackIntervalMin, this.attackIntervalMax));
            } else if (this.attackTime > 10) {
                this.toyTank.setAttackState(0);
            } else if (this.attackTime == 10) {
                this.toyTank.setAttackState(1);
            }
        }
        if (toyTank.getDirectionState() == 1) {
            BlockPos west = toyTank.blockPosition().west(12);
            double d0 = this.mob.distanceToSqr((west.getX()+0.5), west.getY(), (west.getZ()+0.5));
            this.mob.getLookControl().setLookAt((west.getX()+0.5), west.getY(), (west.getZ()+0.5));
            if (--this.attackTime == 0 && this.toyTank.getAmmoCount() > 0) {
                this.toyTank.performRangedAttack(west);
                this.toyTank.setAmmoCount(this.toyTank.getAmmoCount()-1);
                this.attackTime = 60;
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0), this.attackIntervalMin, this.attackIntervalMax));
            } else if (this.attackTime > 10) {
                this.toyTank.setAttackState(0);
            } else if (this.attackTime == 10) {
                this.toyTank.setAttackState(1);
            }
        }
        if (toyTank.getDirectionState() == 2) {
            BlockPos south = toyTank.blockPosition().south(12);
            double d0 = this.mob.distanceToSqr((south.getX()+0.5), south.getY(), (south.getZ()+0.5));
            this.mob.getLookControl().setLookAt((south.getX()+0.5), south.getY(), (south.getZ()+0.5));
            if (--this.attackTime == 0 && this.toyTank.getAmmoCount() > 0) {
                this.toyTank.performRangedAttack(south);
                this.toyTank.setAmmoCount(this.toyTank.getAmmoCount()-1);
                this.attackTime = 60;
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0), this.attackIntervalMin, this.attackIntervalMax));
            } else if (this.attackTime > 10) {
                this.toyTank.setAttackState(0);
            } else if (this.attackTime == 10) {
                this.toyTank.setAttackState(1);
            }
        }
        if (toyTank.getDirectionState() == 3) {
            BlockPos east = toyTank.blockPosition().east(12);
            double d0 = this.mob.distanceToSqr((east.getX()+0.5), east.getY(), (east.getZ()+0.5));
            this.mob.getLookControl().setLookAt((east.getX()+0.5), east.getY(), (east.getZ()+0.5));
            if (--this.attackTime == 0 && this.toyTank.getAmmoCount() > 0) {
                this.toyTank.performRangedAttack(east);
                this.toyTank.setAmmoCount(this.toyTank.getAmmoCount()-1);
                this.attackTime = 60;
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0), this.attackIntervalMin, this.attackIntervalMax));
            } else if (this.attackTime > 10) {
                this.toyTank.setAttackState(0);
            } else if (this.attackTime == 10) {
                this.toyTank.setAttackState(1);
            }
        }
    }
}
