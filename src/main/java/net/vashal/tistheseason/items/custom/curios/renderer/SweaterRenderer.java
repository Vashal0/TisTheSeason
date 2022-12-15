package net.vashal.tistheseason.items.custom.curios.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.vashal.tistheseason.TisTheSeason;
import net.vashal.tistheseason.items.custom.curios.models.SweaterModel;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class SweaterRenderer implements ICurioRenderer {

    private static final ResourceLocation SWEATER_TEXTURE = new ResourceLocation(TisTheSeason.MOD_ID,
            "textures/entity/sweater_texture.png");

    private final SweaterModel model;

    public SweaterRenderer() {
        this.model = new SweaterModel(
                Minecraft.getInstance().getEntityModels().bakeLayer(CuriosLayerDefinitions.SWEATER));
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
                .getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(SWEATER_TEXTURE), false,
                        stack.hasFoil());
        this.model
                .renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                        1.0F, 1.0F);
    }
}