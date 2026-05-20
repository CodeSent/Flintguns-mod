package net.codesent.flintguns.features.modItems;

import ca.weblite.objc.Message;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;

import java.util.Optional;

enum  gunState {
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

    public flintlock(Properties properties) {
        super(properties);
    }
    //private gunState currentState = gunState.UNLOADED;
    private final Item proplent = Items.GUNPOWDER;
    private final Item bullet = Items.IRON_NUGGET;

    private void writeItemData(ItemStack target, String data, gunState value) {
        target.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, CustomData ->
                CustomData.update(compoundTag -> compoundTag.putInt(data,value.getId()))
        );
       //cPlayer.displayClientMessage(Component.literal("set Data:"+ data+ ", value:"+value.name()),false);
    }
    private gunState readItemData(ItemStack target, String dataName) {
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
        return  currentUsage;
    }
    //Player cPlayer;

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack mainHandStack = player.getItemInHand(hand);
        //cPlayer = player;
        // FIX: Only run your logic if the current execution is evaluating the main hand,
        // but explicitly look at and shrink the OFFHAND stack.
        if (hand == InteractionHand.MAIN_HAND) {
            //ItemStack offhandStack = player.getOffhandItem(); // Explicitly gets offhand
            ItemStack itemstack = player.getOffhandItem();
            String Message = "";
            if (!level.isClientSide()) {
                switch (readItemData(mainHandStack,"gun_State")) {
                    case UNLOADED:
                        Message = "load the Gun with Gunpowder (put the item in offhand.)";
                        if (!itemstack.isEmpty()&&itemstack.getItem() == proplent) {
                            Message = "Gunpowder loaded";
                            writeItemData(mainHandStack,"gun_State",gunState.GUNPOWDER_LOADED);
                            itemstack.shrink(1);
                            player.containerMenu.broadcastChanges();
                            player.displayClientMessage(Component.literal(Message),true);
                            //player.sendSystemMessage();
                            return InteractionResult.SUCCESS;
                        }
                        break;
                    case GUNPOWDER_LOADED:
                        Message = "load the Gun with a Iron Nugget (put the item in offhand)";
                        if (!itemstack.isEmpty()&&itemstack.getItem() == bullet) {
                            Message = "Bullet loaded";
                            writeItemData(mainHandStack,"gun_State",gunState.BULLET_LOADED);
                            itemstack.shrink(1);
                            player.containerMenu.broadcastChanges();
                            player.displayClientMessage(Component.literal(Message),true);
                            return InteractionResult.SUCCESS;
                        };
                        break;
                    case BULLET_LOADED:
                        Message = "use a stick to set the contents (put the item in offhand)";
                        if (!itemstack.isEmpty()&&itemstack.getItem() == Items.STICK) {
                            Message = "Ready to shoot";
                            writeItemData(mainHandStack,"gun_State",gunState.READY);
                            //itemstack.shrink(1);
                            //player.containerMenu.broadcastChanges();
                            player.displayClientMessage(Component.literal(Message),true);
                            return InteractionResult.SUCCESS;
                        };
                        break;

            }
                player.displayClientMessage(Component.literal(Message),true);

                //player.sendSystemMessage();
            }


        }



        // 2. ONLY send the chat message on the logical server side


        // 3. Return the 1.21.4 success result with the item stack attached
        return InteractionResult.PASS;
    }
}