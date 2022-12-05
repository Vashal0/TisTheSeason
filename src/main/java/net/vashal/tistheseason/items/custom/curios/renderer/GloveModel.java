package net.vashal.tistheseason.items.custom.curios.renderer;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


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

public class GloveModel extends HumanoidModel<LivingEntity> {

	public GloveModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
		super(part, renderType);
	}

	public GloveModel(ModelPart part) {
		this(part, RenderType::entityCutoutNoCull);
	}

	public static LayerDefinition createLayer() {
		CubeDeformation cube = new CubeDeformation(0.4F);
		MeshDefinition mesh = HumanoidModel.createMesh(cube, 0.0F);
		PartDefinition part = mesh.getRoot();

		PartDefinition bone = part.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.0774F, -1.5976F, 6.7729F, 1.5708F, 0.0F, 0.0F));

		PartDefinition RightArm_r1 = bone.addOrReplaceChild("RightArm_r1", CubeListBuilder.create().texOffs(45, 55).addBox(1.5804F, -4.6061F, -0.5493F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0976F, 6.8408F, 1.9278F, 1.5708F, 0.0F, 0.8727F));

		PartDefinition RightArm_r2 = bone.addOrReplaceChild("RightArm_r2", CubeListBuilder.create().texOffs(42, 14).addBox(-2.565F, -4.6058F, -1.9709F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 2).addBox(-2.545F, -4.6063F, -3.6209F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0976F, 6.8408F, 1.9278F, 1.5708F, 0.0F, 0.0F));

		PartDefinition RightArm_r3 = bone.addOrReplaceChild("RightArm_r3", CubeListBuilder.create().texOffs(30, 28).addBox(-1.6F, -3.4658F, 5.7491F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(30, 20).addBox(-1.6F, -3.4678F, 1.3341F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(40, 7).addBox(-2.3F, -3.4688F, 2.0541F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6476F, 8.3408F, 0.9278F, 1.5708F, 0.0F, 0.0F));

		PartDefinition RightArm_r4 = bone.addOrReplaceChild("RightArm_r4", CubeListBuilder.create().texOffs(31, 24).addBox(2.6825F, -3.4678F, 4.8954F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6476F, 8.3408F, 0.9278F, 1.5708F, 0.0F, -0.7854F));

		PartDefinition RightArm_r5 = bone.addOrReplaceChild("RightArm_r5", CubeListBuilder.create().texOffs(30, 32).addBox(-3.0802F, -3.4678F, -0.1827F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6476F, 8.3408F, 0.9278F, 1.5708F, 0.0F, 0.7854F));

		PartDefinition RightArm_r6 = bone.addOrReplaceChild("RightArm_r6", CubeListBuilder.create().texOffs(46, 19).addBox(1.5839F, -1.4349F, 3.8041F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(43, 19).addBox(-2.4839F, -1.4349F, 3.8041F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(40, 51).addBox(-2.425F, 1.5562F, 3.8041F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(36, 29).addBox(-2.5F, -2.9313F, 0.8666F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(38, 40).addBox(-2.5F, -2.9313F, -3.1334F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6726F, 8.3408F, 0.9278F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(mesh, 64, 64);

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
				.of(this.rightArm);
	}
}