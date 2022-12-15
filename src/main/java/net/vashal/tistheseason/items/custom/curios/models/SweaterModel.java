package net.vashal.tistheseason.items.custom.curios.models;// Made with Blockbench 4.5.2
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

public class SweaterModel extends HumanoidModel<LivingEntity> {

	public SweaterModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
		super(part, renderType);
	}

	public SweaterModel(ModelPart part) {
		this(part, RenderType::entityCutoutNoCull);
	}

	public static LayerDefinition createLayer() {
		CubeDeformation cube = new CubeDeformation(0.4F);
		MeshDefinition mesh = HumanoidModel.createMesh(cube, 0.0F);
		PartDefinition part = mesh.getRoot();

		PartDefinition body = part.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -1.95F, 8.0F, 11.0F, 4.0F, cube), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightarm = part.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-3.075F, -1.775F, -1.9F, 4.0F, 10.0F, 4.0F, cube), PartPose.offset(9.0F, 2.6F, 0.0F));

		PartDefinition leftarm = part.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-0.95F, -1.6F, -1.9F, 4.0F, 10.0F, 4.0F, cube), PartPose.offset(0.0F, 24.0F, 0.0F));


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
				.of(this.rightArm, this.leftArm, this.body);
	}
}
