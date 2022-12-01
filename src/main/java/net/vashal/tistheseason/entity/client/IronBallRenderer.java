package net.vashal.tistheseason.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.entity.projectile.IronBall;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;


public class IronBallRenderer extends GeoProjectilesRenderer<IronBall> {

    public IronBallRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new IronBallModel());
    }

    protected int getBlockLightLevel(IronBall entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public RenderType getRenderType(IronBall animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderEarly(IronBall animatable, PoseStack stackIn, float ticks,
                            MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn,
                            float red, float green, float blue, float partialTicks) {
        stackIn.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F,
                animatable.tickCount > 2 ? 0.5F : 0.0F);
    }
}

