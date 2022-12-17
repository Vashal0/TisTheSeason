package net.vashal.tistheseason.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;


public class PetRockRenderer extends GeoBlockRenderer<PetRockBlockEntity> {

    public PetRockRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new PetRockModel());
    }

    @Override
    public RenderType getRenderType(PetRockBlockEntity animatable, float partialTick, PoseStack poseStack,
                                    MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }



    @Override
    public void render(PetRockBlockEntity rock, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(rock, partialTick, poseStack, bufferSource, packedLight);
        rock.getDisplayName();
        renderName(rock, rock.getDisplayName().getString(), poseStack, bufferSource, packedLight);
    }

    private void renderName(PetRockBlockEntity rock, String name, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Minecraft minecraft = Minecraft.getInstance();
        HitResult pos = minecraft.hitResult;
        if (Minecraft.renderNames() && !name.isEmpty() && pos != null && pos.getType() == HitResult.Type.BLOCK && rock.getBlockPos().equals(((BlockHitResult) pos).getBlockPos())) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.75F, 0.5F);
            poseStack.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());
            poseStack.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = poseStack.last().pose();
            float f2 = (float)(minecraft.font.width(rock.getDisplayName().getString()) / 2);
            float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
            int j = (int)(f1 * 255.0F) << 24;
            minecraft.font.drawInBatch(name, -f2, 0, 553648127, false, matrix4f, bufferSource, true, j, packedLight);
            minecraft.font.drawInBatch(name, -f2, 0, 0xFFFFFFFF, false, matrix4f, bufferSource, false, 0, packedLight);

            poseStack.popPose();
        }
    }
}

