package net.superscary.fluxmachines.model.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.superscary.fluxmachines.core.blockentity.misc.FluidTankBlockEntity;
import net.superscary.fluxmachines.core.util.helper.FluidHelper;
import org.jetbrains.annotations.NotNull;

public class FluidTankBlockEntityRenderer implements BlockEntityRenderer<FluidTankBlockEntity> {

	private float height;

	public FluidTankBlockEntityRenderer (BlockEntityRendererProvider.Context context) {
		this.height = 0.125f;
	}

	private static void drawVertex (VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int packedOverlay, FluidHelper.RGBA rgba) {
		builder.addVertex(poseStack.last().pose(), x, y, z).setColor(rgba.tint()).setUv(u, v).setUv2(packedLight, packedOverlay).setNormal(1, 0, 0);
	}

	private static void drawQuad (VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int pPackedOverlay, FluidHelper.RGBA rgba) {
		drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, pPackedOverlay, rgba);
		drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, pPackedOverlay, rgba);
		drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, pPackedOverlay, rgba);
		drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, pPackedOverlay, rgba);
	}

	@Override
	public void render (@NotNull FluidTankBlockEntity fluidTankBlockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
		renderFluid(fluidTankBlockEntity, partialTick, poseStack, multiBufferSource, packedLight, packedOverlay);
	}

	private void renderFluid (FluidTankBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
		FluidStack fluidStack = pBlockEntity.getFluid();
		if (fluidStack.isEmpty()) return;

		FluidState state = fluidStack.getFluid().defaultFluidState();

		TextureAtlasSprite sprite = FluidHelper.getStillFluidSprite(fluidStack);
		int tint = FluidHelper.getTintColor(fluidStack, pBlockEntity.getLevel(), pBlockEntity.getBlockPos());
		FluidHelper.RGBA rgba = new FluidHelper.RGBA(tint);

		height = ((((float) fluidStack.getAmount()) / ((float) pBlockEntity.getTank(null).getTankCapacity(0))) * 0.750f) + 0.125f;

		VertexConsumer builder = pBufferSource.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

		float minY = 0.125f;
		float maxY = height;
		drawFluidCube(builder, pPoseStack, minY, maxY, pPackedLight, pPackedOverlay, rgba, sprite);
	}

	private static void drawFluidCube(VertexConsumer builder, PoseStack poseStack, float minY, float maxY, int packedLight, int packedOverlay, FluidHelper.RGBA rgba, TextureAtlasSprite sprite) {
		final float inset = 0.005f;

		float minX = 0.0f + inset;
		float maxX = 1.0f - inset;
		float minZ = 0.0f + inset;
		float maxZ = 1.0f - inset;

		// Top
		drawQuad(builder, poseStack,
				minX, maxY, minZ,
				maxX, maxY, maxZ,
				sprite.getU0(), sprite.getV0(),
				sprite.getU1(), sprite.getV1(),
				packedLight, packedOverlay, rgba);

		// Bottom
		drawQuad(builder, poseStack,
				maxX, minY, maxZ,
				minX, minY, minZ,
				sprite.getU0(), sprite.getV0(),
				sprite.getU1(), sprite.getV1(),
				packedLight, packedOverlay, rgba);

		// North
		drawQuad(builder, poseStack,
				minX, minY, minZ,
				maxX, maxY, minZ,
				sprite.getU0(), sprite.getV0(),
				sprite.getU1(), sprite.getV1(),
				packedLight, packedOverlay, rgba);

		// South
		drawQuad(builder, poseStack,
				maxX, minY, maxZ,
				minX, maxY, maxZ,
				sprite.getU0(), sprite.getV0(),
				sprite.getU1(), sprite.getV1(),
				packedLight, packedOverlay, rgba);

		// East
		drawQuad(builder, poseStack,
				maxX, minY, minZ,
				maxX, maxY, maxZ,
				sprite.getU0(), sprite.getV0(),
				sprite.getU1(), sprite.getV1(),
				packedLight, packedOverlay, rgba);

		// West
		drawQuad(builder, poseStack,
				minX, minY, maxZ,
				minX, maxY, minZ,
				sprite.getU0(), sprite.getV0(),
				sprite.getU1(), sprite.getV1(),
				packedLight, packedOverlay, rgba);
	}

	private int getLightLevel (Level level, BlockPos pos) {
		int bLight = level.getBrightness(LightLayer.BLOCK, pos);
		int sLight = level.getBrightness(LightLayer.SKY, pos);
		return LightTexture.pack(bLight, sLight);
	}

}
