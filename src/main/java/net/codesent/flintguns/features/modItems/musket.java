package net.codesent.flintguns.features.modItems;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import org.jspecify.annotations.NonNull;

public class musket  extends flintlock{
    public static final int  durability = 60;
    public static final String itemID = "musket_gun";
    public static final float  shootVelocity = 15.0f;
    public static final float  dmgMultiplier = 5.0f;
    int                        totalPropellant = 4;
    public musket(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    int getTotalPropellant() {
        return totalPropellant;
    }
    @Override
    float getDamageMultiplier() {
        return dmgMultiplier;
    }
    @Override
    float getVelocity() {
        return shootVelocity;
    }
    @Override
    int getParticleMultiplier() {
        return 3;
    }

    @Override
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack stack) {
        return ItemUseAnimation.CROSSBOW; // Or USE_REMAINS_UNUSEFUL, or CROSSBOW
    }

}
