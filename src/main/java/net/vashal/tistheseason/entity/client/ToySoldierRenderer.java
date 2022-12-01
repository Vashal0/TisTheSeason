package net.vashal.tistheseason.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.constants.ToySoldierConstants;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ToySoldierRenderer extends GeoEntityRenderer<ToySoldierEntity> {
    public ToySoldierRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ToySoldierModel());
        this.shadowRadius = ToySoldierConstants.SHADOW_RADIUS;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ToySoldierEntity instance) {
        return new ResourceLocation(TisTheSeason.MOD_ID, ToySoldierConstants.TEXTURE_RESOURCE);
    }

    @Override
    public RenderType getRenderType(ToySoldierEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {


        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
