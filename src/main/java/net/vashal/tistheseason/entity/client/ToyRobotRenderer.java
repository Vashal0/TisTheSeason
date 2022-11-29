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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

import static net.vashal.tistheseason.constants.ToyRobotConstants.SHADOW_RADIUS;

public class ToyRobotRenderer extends GeoEntityRenderer<ToyRobotEntity> {
    public static final Map<ToyRobotVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ToyRobotVariant.class), (variant) -> {
                variant.put(ToyRobotVariant.BLUE,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_blue.png"));
                variant.put(ToyRobotVariant.BROWN,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_brown.png"));
                variant.put(ToyRobotVariant.PURPLE,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_purple.png"));
                variant.put(ToyRobotVariant.YELLOW,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_yellow.png"));
                variant.put(ToyRobotVariant.BLACK,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_black.png"));
                variant.put(ToyRobotVariant.CYAN,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_cyan.png"));
                variant.put(ToyRobotVariant.GRAY,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_gray.png"));
                variant.put(ToyRobotVariant.GREEN,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_green.png"));
                variant.put(ToyRobotVariant.LIGHT_BLUE,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_light_blue.png"));
                variant.put(ToyRobotVariant.LIGHT_GRAY,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_light_gray.png"));
                variant.put(ToyRobotVariant.LIME,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_lime.png"));
                variant.put(ToyRobotVariant.MAGENTA,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_magenta.png"));
                variant.put(ToyRobotVariant.ORANGE,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_orange.png"));
                variant.put(ToyRobotVariant.PINK,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_pink.png"));
                variant.put(ToyRobotVariant.RED,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_red.png"));
                variant.put(ToyRobotVariant.WHITE,
                        new ResourceLocation(TisTheSeason.MOD_ID, "textures/entity/toyrobot/toy_robot_white.png"));
            });

    public ToyRobotRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ToyRobotModel());
        this.shadowRadius = SHADOW_RADIUS;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ToyRobotEntity instance) {
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


