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

public class SoldierHatModel extends HumanoidModel<LivingEntity> {

	public SoldierHatModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
		super(part, renderType);
	}

	public SoldierHatModel(ModelPart part) {
		this(part, RenderType::entityCutoutNoCull);
	}

	public static LayerDefinition createLayer() {
		CubeDeformation cube = new CubeDeformation(0.4F);
		MeshDefinition mesh = HumanoidModel.createMesh(cube, 0.0F);
		PartDefinition part = mesh.getRoot();

		PartDefinition hat = part.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.9827F, -20.9736F, -3.0539F, 8.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.9827F, -8.9736F, -7.0539F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition hat_3_r1 = hat.addOrReplaceChild("hat_3_r1", CubeListBuilder.create().texOffs(24, 13).addBox(-12.72F, 12.4696F, -5.2335F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.2373F, -22.1892F, -7.1512F, 0.5236F, 0.0F, 0.0F));

		PartDefinition chinstrap3_r1 = hat.addOrReplaceChild("chinstrap3_r1", CubeListBuilder.create().texOffs(22, 24).addBox(-2.1056F, -0.555F, -4.3226F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2373F, -1.0379F, -1.9716F, 1.5274F, -0.24F, -1.5718F));

		PartDefinition chinstrap2_r1 = hat.addOrReplaceChild("chinstrap2_r1", CubeListBuilder.create().texOffs(22, 24).addBox(3.3194F, -0.555F, -2.0726F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(22, 24).addBox(-4.6806F, -0.555F, -2.0726F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2373F, -1.0379F, -1.9716F, 1.3306F, 0.0421F, -0.0113F));

		return LayerDefinition.create(mesh, 64, 64);
	}





	@Override
	@Nonnull
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	@Nonnull
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList
				.of();
	}
}
