package net.vashal.tistheseason.items.custom.curios.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.TisTheSeason;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class GloveRenderer implements ICurioRenderer {

    private static final ResourceLocation GLOVES_TEXTURE = new ResourceLocation(TisTheSeason.MOD_ID,
            "textures/entity/power_glove.png");

    private final GloveModel model;

    public GloveRenderer() {
        this.model = new GloveModel(
                Minecraft.getInstance().getEntityModels().bakeLayer(CuriosLayerDefinitions.GLOVES));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                          SlotContext slotContext,
                                                                          PoseStack matrixStack,
                                                                          RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer,
                                                                          int light, float limbSwing,
                                                                          float limbSwingAmount,
                                                                          float partialTicks,
                                                                          float ageInTicks,
                                                                          float netHeadYaw,
                                                                          float headPitch) {
        LivingEntity entity = slotContext.entity();
        this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ICurioRenderer.followBodyRotations(entity, this.model);
        VertexConsumer vertexconsumer = ItemRenderer
                .getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(GLOVES_TEXTURE), false,
                        stack.hasFoil());
        this.model
                .renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                        1.0F, 1.0F);
    }
}