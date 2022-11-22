package net.vashal.tistheseason.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.entity.custom.ToyRobotEntity;
import net.vashal.tistheseason.entity.variant.ToyRobotVariant;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

import static net.vashal.tistheseason.constants.ToyRobotConstants.SHADOW_RADIUS;
import static net.vashal.tistheseason.constants.ToyRobotConstants.TEXTURE_RESOURCE;

public class ToyRobotRenderer extends GeoEntityRenderer<ToyRobotEntity> {
    public static final Map<ToyRobotVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ToyRobotVariant.class), (variant) -> {
                variant.put(ToyRobotVariant.DEFAULT,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toyrobotdefault.png"));
                variant.put(ToyRobotVariant.WOOD,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toyrobotwood.png"));
                variant.put(ToyRobotVariant.EVA,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toyroboteva.png"));
                variant.put(ToyRobotVariant.BEE,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toyrobotbee.png"));
                variant.put(ToyRobotVariant.DEFAULT_INVERTED,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toyrobotinverted.png"));
                variant.put(ToyRobotVariant.WOOD_INVERTED,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toyrobotwoodinverted.png"));
                variant.put(ToyRobotVariant.BEE_INVERTED,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toyrobotbeeinverted.png"));
            });

    public ToyRobotRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ToyRobotModel());
        this.shadowRadius = SHADOW_RADIUS;
    }

    @Override
    public ResourceLocation getTextureLocation(ToyRobotEntity instance) {
        return LOCATION_BY_VARIANT.get(instance.getVariant());
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

