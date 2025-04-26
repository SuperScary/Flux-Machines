package net.superscary.fluxmachines.api.reactor;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public interface IReactorCoolant<F extends Fluid> {

	/**
	 * Get the fluid type of the reactor coolant.
	 * @return the fluid type of the reactor coolant.
	 */
	F getReactorCoolant ();

	/**
	 * Get the item type of the reactor coolant.
	 * This converts into the fluid.
	 * @return the item type of the reactor coolant.
	 */
	ItemStack getReactorCoolantItem ();

	/**
	 * Get the efficiency of the reactor coolant.
	 * Value must be constrained between 0.1 and 100.0.
	 * @return the efficiency of the reactor coolant.
	 */
	double getReactorCoolantEfficiency ();

}
