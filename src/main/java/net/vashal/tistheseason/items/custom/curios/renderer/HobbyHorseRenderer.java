package net.vashal.tistheseason.items.custom.curios.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

/**
 * Renderer for the HobbyHorse curio
 */
public class HobbyHorseRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                  PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
                  int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                  float netHeadYaw, float headPitch) {

        LivingEntity livingEntity = slotContext.entity();
        ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
        ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);

        //X scale, Y scale, Z scale
        matrixStack.scale(1.1f, 1.1f, 1.1f);
        //X offset, Y offset (higher Y go up, lower Y go down), Z offset
        matrixStack.translate(0.0f, 0.5f, -0.4f);
        //some sort of math magic
        matrixStack.mulPose(new Quaternion(0f, -0.1f, 1f, 0.0f));

        Minecraft.getInstance().getItemRenderer()
                .renderStatic(stack, ItemTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, matrixStack,
                        renderTypeBuffer, 0);
    }
}
