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

    private final ItemRenderer itemRenderer;

    public ToyWorkbenchBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        itemRenderer = Minecraft.getInstance().getItemRenderer();
    }


    @Override
    public void render(ToyWorkbenchBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStack itemStack = pBlockEntity.getRenderStack();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 0.5f, 0.5f);
        pPoseStack.mulPose(Vector3f.YN.rotationDegrees(pBlockEntity.getBlockState().getValue(ToyWorkbench.FACING).toYRot()));
        pPoseStack.pushPose();
        pPoseStack.translate(0.2f, 0.525f, 0.175f);
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(270));
        pPoseStack.mulPose(Vector3f.ZN.rotationDegrees(90));

        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);

        pPoseStack.popPose();
        pPoseStack.popPose();
    }


    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
