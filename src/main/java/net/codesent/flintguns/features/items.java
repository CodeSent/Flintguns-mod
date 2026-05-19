package net.codesent.flintguns.features;

import net.codesent.flintguns.FlintGuns;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class items {
    public static  final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FlintGuns.MODID);

    public static final DeferredItem<Item> FlintlockItem = ITEMS.registerItem(
            "flintlock_gun",
            Item::new);

    public static void register(IEventBus eBus) {
        ITEMS.register(eBus);
    }
}