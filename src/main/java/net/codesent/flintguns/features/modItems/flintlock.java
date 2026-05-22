package net.codesent.flintguns.features.modItems;

import net.codesent.flintguns.features.Entities.Projectilies.Bullet.BulletEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;
import net.codesent.flintguns.features.items;

import java.util.Optional;

enum gunState {
    UNLOADED(0),
    GUNPOWDER_LOADED(1),
    BULLET_LOADED(2),
    READY(3);

    private final int id;

    gunState(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static gunState fromID(int id) {
        for (gunState state : values()) {
            if (state.getId() == id) {
                return state;
            }
        }
        return UNLOADED; // Fallback default
    }
}



public class flintlock extends Item {

    private final Item         propellant = Items.GUNPOWDER;
    private final Item         bullet = Items.IRON_NUGGET;
    public static final int    durability = 30;
    public static final String itemID = "flintlock_gun";
    public static final float  shootVelocity = 8.0f;
    public static final float  dmgMultiplier = 1.0f;
   //int                        totalPropellant= 1;
    //static  int propellantNeeded =1;
    public flintlock(Item.Properties properties) {

        super(properties);
        //properties = properties.durability(durability);

    }


    //private gunState currentState = gunState.UNLOADED;


    private void writeItemData_gunState(ItemStack target, String data, gunState value) {
        target.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, CustomData ->
                CustomData.update(compoundTag -> compoundTag.putInt(data, value.getId()))
        );
        //cPlayer.displayClientMessage(Component.literal("set Data:"+ data+ ", value:"+value.name()),false);
    }

    private gunState readItemData_gunState(ItemStack target, String dataName) {
        CustomData data = target.get(DataComponents.CUSTOM_DATA);
        //data.copyTag()
        gunState currentUsage = gunState.UNLOADED;
        if (data != null) {
            CompoundTag nbtCopy = data.copyTag();

            if (nbtCopy.contains(dataName)) {
                Optional<Integer> optionalVal = nbtCopy.getInt(dataName);
                int stateName = optionalVal.orElse(0);
                currentUsage = gunState.fromID(stateName);
            }
        }
        //cPlayer.displayClientMessage(Component.literal("read Data:"+ dataName + ", value:"+currentUsage.name()),false);
        return currentUsage;
    }
    //Player cPlayer;
    private void writeItemData_int(ItemStack target, String data, int value) {
        target.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, CustomData ->
                CustomData.update(compoundTag -> compoundTag.putInt(data, value))
        );
        //cPlayer.displayClientMessage(Component.literal("set Data:"+ data+ ", value:"+value.name()),false);
    }

   int getTotalPropellant() {
        return 1;
    }
   float getDamageMultiplier() {
        return dmgMultiplier;
    }
    int getParticleMultiplier() {
        return 1;
    }
    float getVelocity() {
        return shootVelocity;
    }

    private int readItemData_int(ItemStack target, String dataName, int defValue) {
        CustomData data = target.get(DataComponents.CUSTOM_DATA);
        //data.copyTag()
        int currentUsage = defValue;
        if (data != null) {
            CompoundTag nbtCopy = data.copyTag();

            if (nbtCopy.contains(dataName)) {
                Optional<Integer> optionalVal = nbtCopy.getInt(dataName);
                currentUsage = optionalVal.orElse(defValue);
            }
        }
        //cPlayer.displayClientMessage(Component.literal("read Data:"+ dataName + ", value:"+currentUsage.name()),false);
        return currentUsage;
    }


    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack mainHandStack = player.getItemInHand(hand);
        int totalPropellant = getTotalPropellant();
        //cPlayer = player;
        // FIX: Only run your logic if the current execution is evaluating the main hand,
        // but explicitly look at and shrink the OFFHAND stack.
        if (hand == InteractionHand.MAIN_HAND) {
            //ItemStack offhandStack = player.getOffhandItem(); // Explicitly gets offhand
            ItemStack itemstack = player.getOffhandItem();
            String Message = "";
          //  if (!level.isClientSide()) {
                switch (readItemData_gunState(mainHandStack, "gun_State")) {
                    case gunState.UNLOADED:
                        int cProp = readItemData_int(mainHandStack,"propellantLoaded",0);
                        //player.displayClientMessage(Component.literal("PropalentNeeded:"+ totalPropellant), false);
                        if (totalPropellant <= 1) {
                            Message = "load the Gun with Gunpowder (put the item in offhand.)";

                        } else {
                            Message = "load the Gun with Gunpowder ("+cProp+"/"+totalPropellant+") (put the item in offhand.)";
                        }

                        if (!itemstack.isEmpty() && itemstack.getItem() == propellant) {
                            cProp++;
                            if (cProp >= totalPropellant) writeItemData_gunState(mainHandStack, "gun_State", gunState.GUNPOWDER_LOADED);
                            if (!level.isClientSide()) {
                                Message = "Gunpowder loaded";

                                itemstack.shrink(1);
                                player.containerMenu.broadcastChanges();
                                player.displayClientMessage(Component.literal(Message), true);
                                //player.sendSystemMessage();
                            }

                            writeItemData_int(mainHandStack,"propellantLoaded",cProp);
                            if (level.isClientSide()) return InteractionResult.CONSUME;
                            return InteractionResult.SUCCESS;
                        }
                        break;
                    case GUNPOWDER_LOADED:

                        Message = "load the Gun with a Iron Nugget (put the item in offhand)";
                        if (!itemstack.isEmpty() && itemstack.getItem() == bullet) {
                            writeItemData_gunState(mainHandStack, "gun_State", gunState.BULLET_LOADED);
                            if (!level.isClientSide()) {
                                Message = "Bullet loaded";

                                itemstack.shrink(1);
                                player.containerMenu.broadcastChanges();
                                player.displayClientMessage(Component.literal(Message), true);

                            }
                            if (level.isClientSide()) return InteractionResult.CONSUME;
                            return InteractionResult.SUCCESS;
                        }
                        ;
                        break;
                    case BULLET_LOADED:

                        Message = "use a stick to set the contents (put the item in offhand)";

                        if (!itemstack.isEmpty() && itemstack.getItem() == Items.STICK) {
                            writeItemData_gunState(mainHandStack, "gun_State", gunState.READY);
                            if (!level.isClientSide()) {
                                Message = "Ready to shoot";

                                //itemstack.shrink(1);
                                //player.containerMenu.broadcastChanges();
                                player.displayClientMessage(Component.literal(Message), true);

                            }
                            if (level.isClientSide()) return InteractionResult.CONSUME;
                            return InteractionResult.SUCCESS;
                        }
                        break;
                    case READY:
                        writeItemData_gunState(mainHandStack, "gun_State", gunState.UNLOADED);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.0F, 2.0F);
                        writeItemData_int(mainHandStack,"propellantLoaded",0);
                        // 2. Projectile spawning logic must run safely on the logical server level
                        if (level instanceof ServerLevel serverLevel) {
                            // Instantiate the custom bullet using our DeferredHolder registry entry
                            BulletEntity bullet = new BulletEntity(items.BULLET.get(), serverLevel);
                            bullet.getPersistentData().putFloat("dmgMultiplier",getDamageMultiplier());

                            serverLevel.sendParticles(
                                    ParticleTypes.LARGE_SMOKE,
                                    player.getX(),
                                    player.getY(0.5),
                                    player.getZ(),
                                    30*getParticleMultiplier(),
                                    0.5,
                                    0.5,
                                    0.5,
                                    0.01
                            );
                            // Position the bullet right at the player's eye level
                            bullet.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
                            bullet.setOwner(player); // Assign shooter so damage tracking knows who killed what

                            // 3. Shoot behavior:
                            // Arguments: (shooter, pitch, yaw, roll, velocity, inaccuracy)
                            // Arrows usually have a velocity of 3.0F. Bullets should be blazing fast (e.g., 6.0F).
                            bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, getVelocity(), 0.5F);

                            // Add the bullet to the world tick system
                            serverLevel.addFreshEntity(bullet);
                             mainHandStack.hurtAndBreak(1,player, InteractionHand.MAIN_HAND);
                            //mainHandStack.hurtAndBreak(1,player,player.getEquipmentSlotForItem(mainHandStack));



                        }

                        // 4. Client side bookkeeping (cooldowns & statistics)
                        player.awardStat(Stats.ITEM_USED.get(this));
                        if (level.isClientSide()) return InteractionResult.CONSUME;
                        return InteractionResult.SUCCESS;

                }
                player.displayClientMessage(Component.literal(Message), true);

                //player.sendSystemMessage();
            }


       // }


        // 2. ONLY send the chat message on the logical server side


        // 3. Return the 1.21.4 success result with the item stack attached
        return InteractionResult.PASS;
    }




}