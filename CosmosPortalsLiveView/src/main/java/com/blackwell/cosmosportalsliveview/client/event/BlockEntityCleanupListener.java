package com.blackwell.cosmosportalsliveview.client.event;

import com.blackwell.cosmosportalsliveview.client.renderer.PortalLiveViewManager;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.chunk.ChunkAccess;

@Mod.EventBusSubscriber(modid = "cosmosportals_liveview", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class BlockEntityCleanupListener {
    
    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (!event.getLevel().isClientSide()) return;
        
        ChunkAccess chunk = event.getChunk();
        
        // Iterate through block entities in the chunk
        try {
            // Try to get block entities map
            var blockEntityMap = chunk.getBlockEntities();
            if (blockEntityMap != null) {
                blockEntityMap.forEach((pos, entity) -> {
                    if (entity != null && entity.getClass().getSimpleName().contains("BlockEntityPortal")) {
                        PortalLiveViewManager.removePortal(pos);
                    }
                });
            }
        } catch (Exception e) {
            // Fallback: try iterating through NBT
            try {
                var nbt = chunk.getBlockEntitiesTag();
                if (nbt != null && nbt.contains("BlockEntities")) {
                    var beList = nbt.getList("BlockEntities", Tag.TAG_COMPOUND);
                    for (int i = 0; i < beList.size(); i++) {
                        var beTag = beList.getCompound(i);
                        int x = beTag.getInt("x");
                        int y = beTag.getInt("y");
                        int z = beTag.getInt("z");
                        String id = beTag.getString("id");
                        if (id.contains("portal")) {
                            PortalLiveViewManager.removePortal(new BlockPos(x, y, z));
                        }
                    }
                }
            } catch (Exception e2) {
                // Silently fail
            }
        }
    }
    
    @SubscribeEvent
    public static void onWorldUnload(LevelEvent.Unload event) {
        if (event.getLevel().isClientSide()) {
            PortalLiveViewManager.cleanup();
        }
    }
}
