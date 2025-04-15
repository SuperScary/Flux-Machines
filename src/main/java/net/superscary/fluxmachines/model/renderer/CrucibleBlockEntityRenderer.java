package net.superscary.fluxmachines.model.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.superscary.fluxmachines.core.blockentity.misc.CrucibleBlockEntity;
import net.superscary.fluxmachines.core.config.FMClientConfig;
import net.superscary.fluxmachines.core.util.helper.FluidHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CrucibleBlockEntityRenderer implements BlockEntityRenderer<CrucibleBlockEntity> {

	private float height;
	private final BlockEntityRendererProvider.Context context;
	private final BlockRenderDispatcher blockRenderer;

	public CrucibleBlockEntityRenderer (BlockEntityRendererProvider.Context context) {
		this.height = 0.125f;
		this.context = context;
		this.blockRenderer = Minecraft.getInstance().getBlockRenderer();
	}

	private static void drawVertex (VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int packedOverlay, FluidHelper.RGBA rgba) {
		builder.addVertex(poseStack.last().pose(), x, y, z).setColor(rgba.getRed(), rgba.getGreen(), rgba.getBlue(), rgba.getAlpha()).setUv(u, v).setUv2(packedLight, packedOverlay).setNormal(1, 0, 0);
	}

	private static void drawQuad (VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int pPackedOverlay, FluidHelper.RGBA rgba) {
		drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, pPackedOverlay, rgba);
		drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, pPackedOverlay, rgba);
		drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, pPackedOverlay, rgba);
		drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, pPackedOverlay, rgba);
	}

	@Override
	public void render (@NotNull CrucibleBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
		renderFluid(blockEntity, partialTick, poseStack, multiBufferSource, packedLight, packedOverlay);

		if (!FMClientConfig.renderCrucibleItems.get()) return;
		renderItem(blockEntity, partialTick, poseStack, multiBufferSource, packedLight, packedOverlay, 0, ItemPosition.LEFT_TOP);
		renderItem(blockEntity, partialTick, poseStack, multiBufferSource, packedLight, packedOverlay, 1, ItemPosition.RIGHT_TOP);
		renderItem(blockEntity, partialTick, poseStack, multiBufferSource, packedLight, packedOverlay, 2, ItemPosition.LEFT_BOTTOM);
		renderItem(blockEntity, partialTick, poseStack, multiBufferSource, packedLight, packedOverlay, 3, ItemPosition.RIGHT_BOTTOM);
	}

	private void renderItem (CrucibleBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay, int slot, ItemPosition position) {
		ItemRenderer itemRenderer = context.getItemRenderer();
		ItemStack itemStack = pBlockEntity.getInventory().getStackInSlot(slot);

		if (itemStack.isEmpty()) return;

		pPoseStack.pushPose();
		pPoseStack.translate(position.getX(), height, position.getZ());
		pPoseStack.scale(0.35f, 0.35f, 0.35f);
		pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

		RenderSystem.applyModelViewMatrix();
		itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);
		pPoseStack.popPose();
	}

	private void renderFluid (CrucibleBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
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

		float minX = 0.02f + inset;
		float maxX = 0.99f - inset;
		float minZ = 0.02f + inset;
		float maxZ = 0.99f - inset;

		// Top
		drawQuad(builder, poseStack,
				minX, maxY, minZ,
				maxX, maxY, maxZ,
				sprite.getU0(), sprite.getV0(),
				sprite.getU1(), sprite.getV1(),
				packedLight, packedOverlay, rgba);

	}

	private int getLightLevel (Level level, BlockPos pos) {
		int bLight = level.getBrightness(LightLayer.BLOCK, pos);
		int sLight = level.getBrightness(LightLayer.SKY, pos);
		return LightTexture.pack(bLight, sLight);
	}

	enum ItemPosition {
		LEFT_TOP(0.25f, 0.25f),
		RIGHT_TOP(0.75f, 0.25f),
		LEFT_BOTTOM(0.25f, 0.75f),
		RIGHT_BOTTOM(0.75f, 0.75f);

		final float x, z;
		ItemPosition (float x, float z) {
			this.x = x;
			this.z = z;
		}

		public float getX () {
			return x;
		}

		public float getZ () {
			return z;
		}
	}
}
