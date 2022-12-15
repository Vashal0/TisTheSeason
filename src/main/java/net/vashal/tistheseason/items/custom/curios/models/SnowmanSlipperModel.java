package net.vashal.tistheseason.items.custom.curios.models;

// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;

public class SnowmanSlipperModel extends HumanoidModel<LivingEntity> {

	public SnowmanSlipperModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
		super(part, renderType);
	}

	public SnowmanSlipperModel(ModelPart part) {
		this(part, RenderType::entityCutoutNoCull);
	}


	public static LayerDefinition createLayer() {
		CubeDeformation cube = new CubeDeformation(0.0F);
		MeshDefinition mesh = HumanoidModel.createMesh(cube, 0.0F);
		PartDefinition part = mesh.getRoot();

		PartDefinition LeftLeg = part.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(13, 0).addBox(-1.9175F, 10.0636F, -5.1063F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(2.0825F, 10.0636F, -2.1813F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(11, 7).addBox(-1.9175F, 10.0636F, -2.1813F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.9175F, 11.0636F, -2.1813F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(13, 10).addBox(-1.9175F, 10.0886F, 2.8188F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(8, 18).addBox(-0.95F, 6.775F, -3.975F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(14, 15).addBox(-1.475F, 7.85F, -4.375F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(13, 6).addBox(-1.475F, 8.125F, -4.55F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0827F, 22.0919F, -1.025F));

		PartDefinition LeftLeg_r1 = LeftLeg.addOrReplaceChild("LeftLeg_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.55F, 3.125F, -4.575F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.498F, 9.0886F, -4.1813F, 0.7362F, -0.6387F, -1.3629F));

		PartDefinition cube_r1 = LeftLeg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 15).addBox(-1.022F, -1.485F, -1.4975F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(14, 15).addBox(-1.022F, 1.49F, -1.4975F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, 8.35F, -3.3875F, 0.0F, 1.5708F, -1.5708F));

		PartDefinition cube_r2 = LeftLeg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 15).addBox(-1.5F, 1.015F, -1.4975F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(14, 15).addBox(-1.5F, -0.985F, -1.4975F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.025F, 8.35F, -3.3875F, 1.5708F, 0.0F, 0.0F));

		PartDefinition rightleg = part.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 12).addBox(-2.063F, 10.0733F, -5.2938F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(11, 9).addBox(-2.063F, 10.0733F, -2.3688F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(18, 5).addBox(-2.063F, 10.0983F, 2.6313F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(-2.063F, 11.0733F, -2.3688F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(10, 12).addBox(1.937F, 10.0983F, -2.3688F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 18).addBox(-1.4955F, 8.1348F, -4.7375F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(14, 15).addBox(-1.4955F, 7.8598F, -4.5625F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(8, 18).addBox(-0.9705F, 6.7848F, -4.1625F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2282F, 22.0822F, -0.8375F));

		PartDefinition cube_r3 = rightleg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 15).addBox(-6.375F, 1.015F, -1.4975F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(14, 15).addBox(-6.375F, -0.985F, -1.4975F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.8795F, 8.3598F, -3.575F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r4 = rightleg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 15).addBox(-1.022F, 1.49F, -1.4975F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(14, 15).addBox(-1.022F, -1.485F, -1.4975F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0045F, 8.3598F, -3.575F, 0.0F, 1.5708F, -1.5708F));

		PartDefinition LeftLeg_r2 = rightleg.addOrReplaceChild("LeftLeg_r2", CubeListBuilder.create().texOffs(0, 2).addBox(-0.675F, -0.3F, -0.575F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.187F, 9.0983F, -4.3688F, 0.7362F, 0.6387F, 1.3629F));

		return LayerDefinition.create(mesh, 32, 32);
	}

	@Override
	@Nonnull
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	@Nonnull
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList
				.of(this.rightLeg, this.leftLeg);
	}
}
