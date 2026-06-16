package com.blackwell.cosmosportalsliveview;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.blackwell.cosmosportalsliveview.config.PortalLiveViewConfig;
import com.blackwell.cosmosportalsliveview.client.PortalLiveViewClientSetup;

@Mod(CosmosPortalsLiveView.MOD_ID)
public class CosmosPortalsLiveView {
    
    public static final String MOD_ID = "cosmosportals_liveview";
    
    public CosmosPortalsLiveView() {
        IEventBus modEventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, PortalLiveViewConfig.SPEC, "cosmosportals-liveview-client.toml");
        
        modEventBus.addListener(this::onFMLCommonSetup);
        modEventBus.addListener(this::onFMLClientSetup);
    }
    
    private void onFMLCommonSetup(final FMLCommonSetupEvent event) {
    }
    
    @OnlyIn(Dist.CLIENT)
    private void onFMLClientSetup(final FMLClientSetupEvent event) {
        PortalLiveViewClientSetup.setupClient();
    }
}
