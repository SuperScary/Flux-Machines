package net.superscary.fluxmachines.core.util.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
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
