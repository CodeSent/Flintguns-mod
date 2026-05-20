package net.codesent.flintguns.features;

import net.codesent.flintguns.FlintGuns;
import net.codesent.flintguns.features.Entities.Projectilies.Bullet.BulletEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@EventBusSubscriber(modid = FlintGuns.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(items.BULLET.get(), BulletEntityRenderer::new);
    }
}
