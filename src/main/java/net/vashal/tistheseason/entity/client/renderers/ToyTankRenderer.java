package net.vashal.tistheseason.entity.client.renderers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.constants.ToyTankConstants;
import net.vashal.tistheseason.entity.client.models.ToyTankModel;
import net.vashal.tistheseason.entity.custom.ToyTankEntity;
import net.vashal.tistheseason.entity.variant.ToyTankVariant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class ToyTankRenderer extends GeoEntityRenderer<ToyTankEntity> {
    public static final Map<ToyTankVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ToyTankVariant.class), (variant) -> {
                variant.put(ToyTankVariant.DEFAULT,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toytank/toy_tank.png"));
                variant.put(ToyTankVariant.SAND,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toytank/toy_tank_sand.png"));
            });

    public ToyTankRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ToyTankModel());
        this.shadowRadius = ToyTankConstants.SHADOW_RADIUS;
    }

    public float getDeathMaxRotation(ToyTankEntity instance) {
        return 0f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ToyTankEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    @Override
    public RenderType getRenderType(ToyTankEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {


        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }

    @Override
    public void render(@NotNull ToyTankEntity tank, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        super.render(tank, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        Component displayName = Component.translatable("Out of Gunpowder");
        if (tank.getAmmoCount() == 0 && tank.getTurretMode()) {
            renderName(tank, displayName, poseStack, bufferSource, packedLight, true);
        }
        if (tank.getAmmoCount() > 0 && tank.getTurretMode()) {
            displayName = Component.translatable("Shots Remaining: " + tank.getAmmoCount());
            renderName(tank, displayName, poseStack, bufferSource, packedLight, false);
        }
    }

    protected void renderName(ToyTankEntity tank, Component pDisplayName, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, boolean color) {
        if (shouldShow(tank)) {
            float f = tank.getBbHeight() + 1.2F;
            pMatrixStack.pushPose();
            pMatrixStack.translate(0.0D, f, 0.0D);
            pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            pMatrixStack.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = pMatrixStack.last().pose();
            float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
            int j = (int) (f1 * 255.0F) << 24;
            Font font = this.getFont();
            float f2 = (float) (-font.width(pDisplayName) / 2);
            if (color) {
                font.drawInBatch(pDisplayName, f2, 0, 16753920, false, matrix4f, pBuffer, false, j, pPackedLight);
            } else {
                font.drawInBatch(pDisplayName, f2, 0, 9498256, false, matrix4f, pBuffer, false, j, pPackedLight);
            }

            pMatrixStack.popPose();
        }
    }

    protected boolean shouldShow(ToyTankEntity tank) {
        return tank == this.entityRenderDispatcher.crosshairPickEntity;
    }
}
