package net.superscary.fluxmachines.api.inventory;

import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Interface for blocks that have an inventory
 */
public interface MachineInventory {

    /**
     * Get the inventory
     * @return the inventory
     */
    ItemStackHandler getInventory ();

    FluidHandlerItemStack getFluidInventory ();

    /**
     * Clear the contents of the inventory
     */
    void clearContents ();

}
