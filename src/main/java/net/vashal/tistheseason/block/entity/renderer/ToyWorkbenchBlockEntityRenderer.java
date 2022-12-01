package net.vashal.tistheseason.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.vashal.tistheseason.block.custom.ToyWorkbench;
import net.vashal.tistheseason.block.entity.ToyWorkbenchBlockEntity;
import net.vashal.tistheseason.items.TTS_Items;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ToyWorkbenchBlockEntityRenderer implements BlockEntityRenderer<ToyWorkbenchBlockEntity> {

    public ToyWorkbenchBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }


    @Override
    public void render(ToyWorkbenchBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack itemStack = pBlockEntity.getRenderStack();
        pPoseStack.pushPose();
        pPoseStack.translate(0.68f, 1f, 0.22f);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(-90));
        pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(180));

        switch (pBlockEntity.getBlockState().getValue(ToyWorkbench.FACING)) {
            case NORTH -> {
                pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
                pPoseStack.translate(0.15f,-0.82f,0f);
            }
            case EAST -> {
                pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
                pPoseStack.translate(-0.05f,0.1f,0f);
            }
            case SOUTH -> {
                pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
                pPoseStack.translate(-0.95f,-0.1f,0f);
            }
            case WEST -> {
                pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
                pPoseStack.translate(-0.75f,-1.02f,0f);
            }
        }

        if (itemStack.getItem() == TTS_Items.HOBBY_HORSE.get()) {
            pPoseStack.mulPose(Vector3f.XP.rotationDegrees(-15));
            pPoseStack.translate(0f,0f,-0.1f);
        }

        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()),
                        pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
