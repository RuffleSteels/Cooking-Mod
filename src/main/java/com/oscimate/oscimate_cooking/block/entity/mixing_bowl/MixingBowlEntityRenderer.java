package com.oscimate.oscimate_cooking.block.entity.mixing_bowl;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3f;

public class MixingBowlEntityRenderer implements BlockEntityRenderer<MixingBowlEntity> {

    public MixingBowlEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(MixingBowlEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();


        matrices.translate(0.5, 0.3, 0.4);
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());

        for (int i = 0; i < blockEntity.size(); i++) {
            if (blockEntity.getStack(i).equals(ItemStack.EMPTY) || blockEntity.getStack(i) == Items.AIR.getDefaultStack()) {
                MinecraftClient.getInstance().getItemRenderer().renderItem(Items.AIR.getDefaultStack(), ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
                matrices.translate(0, 0, 0.1);
            }
            if (!blockEntity.getStack(i).isEmpty()) {
                MinecraftClient.getInstance().getItemRenderer().renderItem(blockEntity.getStack(i), ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
                matrices.translate(0, 0, 0.1);
            }
        }


        matrices.pop();
    }

}
