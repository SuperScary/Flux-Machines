package net.superscary.fluxmachines.core.recipe.input;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public record CrucibleInput (ItemStack[] input, FluidStack fluidInput, int craftTime) implements RecipeInput {
	@Override
	public @NotNull ItemStack getItem (int i) {
		return input[i];
	}

	@Override
	public int size () {
		return 4;
	}
}
