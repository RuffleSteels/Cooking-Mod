package com.oscimate.oscimate_cooking;

import com.oscimate.oscimate_cooking.block.BlockRegistry;
import com.oscimate.oscimate_cooking.block.entity.mixing_bowl.MixingBowlEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.KITCHEN_BENCH, RenderLayer.getCutout());
        Main.SCREEN_REGISTRY.registerAllClient();
        BlockEntityRendererRegistry.INSTANCE.register(BlockRegistry.MIXING_BOWL_ENTITY, MixingBowlEntityRenderer::new);
    }
}
