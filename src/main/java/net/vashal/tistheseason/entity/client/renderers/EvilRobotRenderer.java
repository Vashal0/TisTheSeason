package net.vashal.tistheseason.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.constants.ToyTankConstants;
import net.vashal.tistheseason.entity.client.models.EvilRobotModel;
import net.vashal.tistheseason.entity.custom.EvilToyRobotEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EvilRobotRenderer extends GeoEntityRenderer<EvilToyRobotEntity> {

    public EvilRobotRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EvilRobotModel());
        this.shadowRadius = ToyTankConstants.SHADOW_RADIUS;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EvilToyRobotEntity instance) {
        return new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_green.png");
    }

    @Override
    public RenderType getRenderType(EvilToyRobotEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {


        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}