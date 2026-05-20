package net.codesent.flintguns.features.Entities.Projectilies.Bullet;

import net.minecraft.server.level.ServerLevel;
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

    float damageAmount = 5.0F;

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
