package net.codesent.flintguns.features.modItems;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import org.jspecify.annotations.NonNull;

public class musket  extends flintlock{
    public static final int  durability = 75;
    public static final String itemID = "musket_gun";
    public static final float  shootVelocity = 30.0f;
    public static final float  dmgMultiplier = 10.0f;
    int                        totalPropellant = 3;
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
    public @NonNull ItemUseAnimation getUseAnimation(@NonNull ItemStack stack) {
        return ItemUseAnimation.CROSSBOW; // Or USE_REMAINS_UNUSEFUL, or CROSSBOW
    }

}
