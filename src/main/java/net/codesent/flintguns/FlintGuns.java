package net.codesent.flintguns;

import net.codesent.flintguns.features.ClientEvents;
import net.codesent.flintguns.features.Entities.Projectilies.Bullet.BulletEntityRenderer;
import net.codesent.flintguns.features.Entities.Projectilies.Bullet.BulletModel;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.codesent.flintguns.features.creativemodeTab;
import net.codesent.flintguns.features.items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(FlintGuns.MODID)
public class FlintGuns {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "flintguns";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public FlintGuns(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        
        creativemodeTab.register(modEventBus);
        items.register(modEventBus);
        
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        //NeoForge.EVENT_BUS.register(ClientEvents.class);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(this::registerEntityRenderers);
        modEventBus.addListener(this::registerLayerDefinitions);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
       
    }
    public  void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(items.BULLET.get(), BulletEntityRenderer::new);
    }
    public  void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Registers the blueprint (LayerDefinition) for your bullet model
        event.registerLayerDefinition(BulletModel.MODEL_LAYER, BulletModel::createBodyLayer);
    }
    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        
    }
}
