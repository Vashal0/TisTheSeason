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

public class MittenModel extends HumanoidModel<LivingEntity> {

	public MittenModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
		super(part, renderType);
	}

	public MittenModel(ModelPart part) {
		this(part, RenderType::entityCutoutNoCull);
	}

//TODO maybe some time i'll learn how to render differently for slim arms, but that won't be today
	public static LayerDefinition createLayer() {
		CubeDeformation cube = new CubeDeformation(0.4F);
		MeshDefinition mesh = HumanoidModel.createMesh(cube, 0.0F);
		PartDefinition part = mesh.getRoot();

		PartDefinition rightarm = part.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(3, 4).addBox(-3.6F, 7.975F, -2.35F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 2.6F, 0.0F));


		PartDefinition leftarm = part.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(3, 4).addBox(-1.5F, 7.975F, -2.35F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 2.6F, 0.0F));

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
				.of(this.rightArm, this.leftArm);
	}
}