package net.superscary.fluxmachines.core.util.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidHelper {

	public static int getTintColor (FluidStack stack, Level level, BlockPos pos) {
		return getTintColor(stack.getFluid().defaultFluidState(), level, pos);
	}

	public static int getTintColor (FluidState state, Level level, BlockPos pos) {
		Fluid fluid = state.getType();
		IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluid);
		return fluidTypeExtensions.getTintColor(state, level, pos);
	}

	public static TextureAtlasSprite getStillFluidSprite (FluidStack stack) {
		Fluid fluid = stack.getFluid();
		IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
		ResourceLocation fluidStill = renderProperties.getStillTexture(stack);
		Minecraft minecraft = Minecraft.getInstance();
		return minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
	}

	public static Block getBlockFromFluidStack (FluidStack fluidStack) {
		if (fluidStack == null || fluidStack.isEmpty()) {
			return Blocks.AIR; // or null, depending on your logic
		}

		Fluid fluid = fluidStack.getFluid();

		// Get the default fluid state and the corresponding block state
		FluidState fluidState = fluid.defaultFluidState();

		// Only fluids that are placed in the world as blocks will return a non-null block state
		BlockState blockState = fluidState.createLegacyBlock();

		return blockState.getBlock();
	}

	public record RGBA(int tint) {
		public float getAlpha () {
			return ((tint >> 24) & 0xFF) / 255f;
		}

		public float getRed () {
			return ((tint >> 16) & 0xFF) / 255f;
		}

		public float getGreen () {
			return ((tint >> 8) & 0xFF) / 255f;
		}

		public float getBlue () {
			return (tint & 0xFF) / 255f;
		}
	}

}
