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
import net.vashal.tistheseason.constants.ToySoldierConstants;
import net.vashal.tistheseason.entity.client.models.ToySoldierModel;
import net.vashal.tistheseason.entity.custom.ToySoldierEntity;
import net.vashal.tistheseason.entity.variant.ToySoldierVariant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class ToySoldierRenderer extends GeoEntityRenderer<ToySoldierEntity> {
    public static final Map<ToySoldierVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ToySoldierVariant.class), (variant) -> {
                variant.put(ToySoldierVariant.DEFAULT,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toysoldier/toy_soldier.png"));
                variant.put(ToySoldierVariant.ALTERNATE,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toysoldier/toy_soldier_alternate.png"));
            });

    public ToySoldierRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ToySoldierModel());
        this.shadowRadius = ToySoldierConstants.SHADOW_RADIUS;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ToySoldierEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
    }

    public float getDeathMaxRotation(ToySoldierEntity instance) {
        return 0f;
    }

    @Override
    public RenderType getRenderType(ToySoldierEntity animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture) {


        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
