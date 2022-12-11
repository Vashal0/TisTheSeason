package net.vashal.tistheseason.items.custom.curios.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

/**
 * Renderer for the MariHobbyHorse curio
 */
public class MariHobbyHorseRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                  PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
                  int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                  float netHeadYaw, float headPitch) {

        LivingEntity livingEntity = slotContext.entity();
        ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
        ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
        matrixStack.pushPose();
        matrixStack.scale(1.2f, 1.2f, 1.2f);
        matrixStack.translate(0.0f, 0.4f, -0.4f);
        matrixStack.mulPose(new Quaternion(0f, -0.1f, 1f, 0.0f));
        Minecraft.getInstance().getItemRenderer()
                .renderStatic(stack, ItemTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, matrixStack,
                        renderTypeBuffer, 0);
        matrixStack.popPose();
    }
}
