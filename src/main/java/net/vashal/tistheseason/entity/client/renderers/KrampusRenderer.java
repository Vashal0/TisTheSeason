package net.vashal.tistheseason.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.constants.KrampusConstants;
import net.vashal.tistheseason.entity.client.models.KrampusModel;
import net.vashal.tistheseason.entity.custom.KrampusEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class KrampusRenderer extends GeoEntityRenderer<KrampusEntity> {

    public KrampusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KrampusModel());
        this.shadowRadius = KrampusConstants.SHADOW_RADIUS;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull KrampusEntity instance) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/krampus_texture.png");
    }

    public float getDeathMaxRotation(KrampusEntity instance) {
        return 0f;
    }

    @Override
    public RenderType getRenderType(KrampusEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }

}
