package net.vashal.tistheseason.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
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
        Minecraft mc = Minecraft.getInstance();
        HitResult pos = mc.hitResult;
        if (Minecraft.renderNames()
                && !name.isEmpty() && pos != null && pos.getType() == HitResult.Type.BLOCK
                && rock.getBlockPos().equals(((BlockHitResult) pos).getBlockPos())) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
            float f1 = 0.016666668F * 1.6F;
            poseStack.scale(-f1, -f1, f1);
            int halfWidth = mc.font.width(rock.name.getString()) / 2;

            float opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
            int opacityRGB = (int) (opacity * 255.0F) << 24;
            mc.font.drawInBatch(rock.name, -halfWidth, 0, 0x20FFFFFF, false, poseStack.last().pose(), bufferSource, true, opacityRGB, packedLight);
            mc.font.drawInBatch(rock.name, -halfWidth, 0, 0xFFFFFFFF, false, poseStack.last().pose(), bufferSource, false, 0, packedLight);
            poseStack.popPose();
        }
    }

}

