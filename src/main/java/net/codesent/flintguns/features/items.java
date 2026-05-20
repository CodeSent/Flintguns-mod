package net.codesent.flintguns.features;

import net.codesent.flintguns.FlintGuns;
import net.codesent.flintguns.features.Entities.Projectilies.Bullet.BulletEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.codesent.flintguns.features.modItems.flintlock;

import java.util.function.Supplier;


public class items {
    public static  final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FlintGuns.MODID);

    public static final DeferredItem<flintlock> FlintlockItem = ITEMS.register(
            "flintlock_gun",
            () -> new flintlock(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,Identifier.fromNamespaceAndPath(FlintGuns.MODID,"flintlock_gun"))).durability(1)));
    public static final DeferredRegister.Entities  ENTITY_TYPES = DeferredRegister.createEntities(FlintGuns.MODID);

    // Registers the entity type
    public static final Supplier<EntityType<BulletEntity>> BULLET =
            ENTITY_TYPES.register(BulletEntity.ENTITYID, () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
                    .sized(0.5f, 1.15f)
                    .noSummon()
                    // Prevents the entity from being saved to disk.
                    .noSave()
                    // Makes the entity fire immune.
                    .fireImmune().build(ResourceKey.create(
                            Registries.ENTITY_TYPE,
                            Identifier.fromNamespaceAndPath(FlintGuns.MODID, BulletEntity.ENTITYID)
                    )));


    public static void register(IEventBus eBus) {

        ITEMS.register(eBus);
        ENTITY_TYPES.register(eBus);
    }
}