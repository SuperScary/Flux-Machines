package net.superscary.fluxmachines.api.gui;

import net.neoforged.neoforge.fluids.FluidStack;
import net.superscary.fluxmachines.gui.FluidTankRenderer;

public interface GuiFluid {

	FluidStack getFluidStack();

	FluidTankRenderer getFluidTankRenderer();

}
