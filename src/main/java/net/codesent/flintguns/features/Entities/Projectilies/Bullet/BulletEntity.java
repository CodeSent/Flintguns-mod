package net.codesent.flintguns.features.Entities.Projectilies.Bullet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BulletEntity extends AbstractArrow {
    public static String ENTITYID = "flintguns_bullet";


    public BulletEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
        this.pickup = Pickup.DISALLOWED; // Bullet cannot be picked up
    }

    float damageAmount = 10.0F;

    public void tick() {
        super.tick();

        // 1. Only spawn particles on the server side & ensure the entity is alive
        if (!this.level().isClientSide() && this.isAlive()) {

            // 2. Spawn a trail of smoke (Large smoke or normal smoke)
            this.level().addFreshEntity(new net.minecraft.world.entity.item.ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.AIR))); // Workaround for simple particle API

            // The preferred NeoForge way to send particles to all players tracking this entity:
            ((net.minecraft.server.level.ServerLevel) this.level()).sendParticles(
                    ParticleTypes.SMOKE,
                    this.getX(), this.getY() + 0.2, this.getZ(), // X, Y, Z position
                    30, // Particle count
                    0.1, 0.1, 0.1, // Random offset spread
                    0.0 // Particle speed
            );
            ((net.minecraft.server.level.ServerLevel) this.level()).sendParticles(
                    ParticleTypes.FLAME,
                    this.getX(), this.getY() + 0.2, this.getZ(), // X, Y, Z position
                    10, // Particle count
                    0.1, 0.1, 0.1, // Random offset spread
                    0.0 // Particle speed
            );
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level() instanceof ServerLevel serverLevel) {
            Entity target = result.getEntity();
            Entity owner = this.getOwner();

            DamageSource damageSource = this.damageSources().arrow(this, owner != null ? owner : this);


            if (target.hurtServer(serverLevel,damageSource, damageAmount)) {
                // Optional: Apply vanilla arrow hit mechanics like enchantment effects or fire aspect
                if (target instanceof LivingEntity livingTarget) {
                    this.doPostHurtEffects(livingTarget);
                }
            }


            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }
}
