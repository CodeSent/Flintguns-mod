package net.codesent.flintguns.features;


import net.codesent.flintguns.features.items;
import net.codesent.flintguns.FlintGuns;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class creativemodeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FlintGuns.MODID);

    public static final Supplier<CreativeModeTab> FLINTGUNS_GUNS_TAB = CREATIVE_MODE_TAB.register("bismuth_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(items.FlintlockItem .get()))
                    .title(Component.translatable("creativetab.FlintGuns.Guns"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(items.FlintlockItem);
                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}