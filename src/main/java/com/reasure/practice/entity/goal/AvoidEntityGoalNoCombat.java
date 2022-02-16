package com.reasure.practice.entity.goal;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class AvoidEntityGoalNoCombat<T extends LivingEntity> extends Goal {
    protected final PathfinderMob mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    @Nullable
    protected T toAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;
    /**
     * 이 행동이 피하려고 하는 엔티티 클래스
     */
    protected final Class<T> avoidClass;
    private final TargetingConditions avoidEntityTargeting;

    /**
     * 몹이 특정 클래스의 몹을 피하는 것을 돕는 목표
     */
    public AvoidEntityGoalNoCombat(PathfinderMob pMob, Class<T> entityClassToAvoid, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier) {
        this.mob = pMob;
        this.avoidClass = entityClassToAvoid;
        this.maxDist = maxDistance;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.pathNav = pMob.getNavigation();
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.avoidEntityTargeting = TargetingConditions.forNonCombat().range(maxDistance).selector(EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    /**
     * 행동을 시작해야 하는지 여부를 반환. 이 메서드에서도 실행에 필요한 상태를 읽고 캐시할 수 있음.
     */
    public boolean canUse() {
        List<T> entitiesOfClass = this.mob.level.getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate(this.maxDist, 3.0D, this.maxDist), (ent) -> true);
        this.toAvoid = this.mob.level.getNearestEntity(entitiesOfClass, this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
            if (vec3 == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                return this.path != null;
            }
        }
    }

    /**
     * 진행 중인 EntityAIBase가 계속 실행되어야 하는지 여부를 반환
     */
    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    /**
     * 한번에 하는 과제 또는 연속적인 과제 실행
     */
    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);
    }

    /**
     * 과제의 내부 상태 재설정. 이 과제가 다른 과제에 의해 중단될 때 호출
     */
    public void stop() {
        this.toAvoid = null;
    }

    /**
     * 이미 시작된 연속적인 과제를 틱마다 계속 실행
     */
    public void tick() {
        if (this.toAvoid != null) {
            if (this.mob.distanceToSqr(this.toAvoid) < 49.0D) {
                this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
            } else {
                this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
            }
        }
    }
}