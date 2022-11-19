package net.vashal.tistheseason.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.ToyRobotConstants;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import static net.vashal.tistheseason.entity.ToyRobotConstants.SHADOW_RADIUS;
import static net.vashal.tistheseason.entity.ToyRobotConstants.TEXTURE_RESOURCE;

public class ToyRobotRenderer extends GeoEntityRenderer<ToyRobotEntity> {
    public ToyRobotRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ToyRobotModel());
        this.shadowRadius = SHADOW_RADIUS;
    }

    @Override
    public ResourceLocation getTextureLocation(ToyRobotEntity instance) {
        return new ResourceLocation(TisTheSeason.MOD_ID, TEXTURE_RESOURCE);
    }

    public float getDeathMaxRotation(ToyRobotEntity instance) {
        return 0f;
    }

    @Override
    public RenderType getRenderType(ToyRobotEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {

        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}

