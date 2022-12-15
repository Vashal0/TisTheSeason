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

public class ReindeerSlipperModel extends HumanoidModel<LivingEntity> {

	public ReindeerSlipperModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
		super(part, renderType);
	}

	public ReindeerSlipperModel(ModelPart part) {
		this(part, RenderType::entityCutoutNoCull);
	}


	public static LayerDefinition createLayer() {
		CubeDeformation cube = new CubeDeformation(0.0F);
		MeshDefinition mesh = HumanoidModel.createMesh(cube, 0.0F);
		PartDefinition part = mesh.getRoot();

		PartDefinition LeftLeg = part.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(4, 14).addBox(-1.9175F, 10.0636F, -5.1063F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(16, 12).addBox(-0.5675F, 11.0136F, -5.6063F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(22, 25).addBox(2.0825F, 10.0636F, -2.1813F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(22, 25).addBox(-1.9175F, 10.0636F, -2.1813F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 20).addBox(-1.9175F, 11.0636F, -2.1813F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 30).addBox(-1.9175F, 10.0886F, 2.8188F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0827F, 22.0919F, -1.025F));

		PartDefinition RightLeg_r1 = LeftLeg.addOrReplaceChild("RightLeg_r1", CubeListBuilder.create().texOffs(2, 20).addBox(0.3583F, -1.4624F, -2.4562F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 10).addBox(0.6188F, -3.5294F, -2.1062F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(16, 4).addBox(-4.1317F, -0.9508F, -2.1812F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1327F, 10.1831F, -1.225F, 0.0F, 0.0F, 0.3927F));

		PartDefinition RightLeg_r2 = LeftLeg.addOrReplaceChild("RightLeg_r2", CubeListBuilder.create().texOffs(1, 20).addBox(-1.4118F, -1.4619F, -2.4312F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(16, 3).addBox(1.6137F, -0.9583F, -2.0562F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.6368F, -3.5369F, -2.2312F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1327F, 10.1831F, -1.225F, 0.0F, 0.0F, -0.3927F));

		PartDefinition LeftLeg_r1 = LeftLeg.addOrReplaceChild("LeftLeg_r1", CubeListBuilder.create().texOffs(16, 2).addBox(-1.6689F, -2.7405F, -2.0812F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1327F, 10.1831F, -1.225F, 0.0F, 0.0F, 0.7854F));

		PartDefinition LeftLeg_r2 = LeftLeg.addOrReplaceChild("LeftLeg_r2", CubeListBuilder.create().texOffs(16, 4).addBox(-0.8449F, -2.7543F, -2.2062F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1327F, 10.1831F, -1.225F, 0.0F, 0.0F, -0.7854F));

		PartDefinition rightleg = part.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 10).addBox(-0.663F, 11.0233F, -5.7938F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(17, 11).addBox(-2.063F, 10.0733F, -5.2938F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(22, 25).addBox(-2.063F, 10.0733F, -2.3688F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(14, 30).addBox(-2.063F, 10.0983F, 2.6313F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(14, 20).addBox(-2.063F, 11.0733F, -2.3688F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(22, 25).addBox(1.937F, 10.0983F, -2.3688F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2282F, 22.0822F, -0.8375F));

		PartDefinition RightLeg_r3 = rightleg.addOrReplaceChild("RightLeg_r3", CubeListBuilder.create().texOffs(2, 20).addBox(0.4643F, -1.4581F, -2.2813F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.8497F, -3.6251F, -1.7813F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(16, 1).addBox(-3.9008F, -1.0465F, -1.7563F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2782F, 10.1928F, -1.4125F, 0.0F, 0.0F, 0.3927F));

		PartDefinition RightLeg_r4 = rightleg.addOrReplaceChild("RightLeg_r4", CubeListBuilder.create().texOffs(1, 20).addBox(-1.4059F, -1.4412F, -2.2813F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(16, 1).addBox(1.6924F, -1.0972F, -1.7313F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(2, 0).addBox(-1.4059F, -3.4412F, -1.7813F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2782F, 10.1928F, -1.4125F, 0.0F, 0.0F, -0.3927F));

		PartDefinition RightLeg_r5 = rightleg.addOrReplaceChild("RightLeg_r5", CubeListBuilder.create().texOffs(16, 2).addBox(-1.4922F, -2.9172F, -1.7563F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2782F, 10.1928F, -1.4125F, 0.0F, 0.0F, 0.7854F));

		PartDefinition RightLeg_r6 = rightleg.addOrReplaceChild("RightLeg_r6", CubeListBuilder.create().texOffs(16, 1).addBox(-0.6681F, -2.5775F, -1.7312F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2782F, 10.1928F, -1.4125F, 0.0F, 0.0F, -0.7854F));

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
