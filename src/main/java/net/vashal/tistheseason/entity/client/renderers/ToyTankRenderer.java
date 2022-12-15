package net.vashal.tistheseason.entity.client.renderers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.constants.ToyTankConstants;
import net.vashal.tistheseason.entity.client.models.ToyTankModel;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;
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
}
