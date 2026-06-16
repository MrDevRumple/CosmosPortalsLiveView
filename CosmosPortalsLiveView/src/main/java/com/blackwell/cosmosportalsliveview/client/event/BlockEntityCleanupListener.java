package com.blackwell.cosmosportalsliveview.client.event;

import com.tcn.cosmosportals.core.blockentity.BlockEntityPortal;
import com.blackwell.cosmosportalsliveview.client.renderer.PortalLiveViewManager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;

@Mod.EventBusSubscriber(modid = "cosmosportals_liveview", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class BlockEntityCleanupListener {
    
    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (event.getLevel().isClientSide) {
            var chunk = event.getChunk();
            chunk.getBlockEntities().forEach((pos, entity) -> {
                if (entity instanceof BlockEntityPortal) {
                    PortalLiveViewManager.removePortal(pos);
                }
            });
        }
    }
    
    @SubscribeEvent
    public static void onWorldUnload(LevelEvent.Unload event) {
        if (event.getLevel().isClientSide) {
            PortalLiveViewManager.cleanup();
        }
    }
}
