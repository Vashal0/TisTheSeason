package net.vashal.tistheseason.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.entity.client.models.IronBallModel;
import net.vashal.tistheseason.entity.client.models.WaterStreamModel;
import net.vashal.tistheseason.entity.projectile.IronBall;
import net.vashal.tistheseason.entity.projectile.WaterStream;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;


public class WaterStreamRenderer extends GeoProjectilesRenderer<WaterStream> {

    public WaterStreamRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new WaterStreamModel());
    }

    protected int getBlockLightLevel(WaterStream entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public RenderType getRenderType(WaterStream animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(WaterStream animatable, PoseStack stackIn, float ticks,
                            MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn,
                            float red, float green, float blue, float partialTicks) {
        stackIn.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F,
                animatable.tickCount > 2 ? 0.5F : 0.0F);
    }
}

