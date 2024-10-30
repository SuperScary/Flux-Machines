package net.superscary.fluxmachines.api.energy;

import net.minecraft.world.item.Item;

/**
 * An interface for block entities that can be powered by Forge Energy and have the ability to craft items.
 */
public interface EnergizedCrafter {

    /**
     * Has enough energy to iterate once in the crafting process.
     * @param required the amount of energy required to do a single tick process
     * @return
     */
    boolean hasEnoughEnergy (int required);

    /**
     * Checks if the item can be inserted into the input slot.
     * @return true if the item can be inserted, false otherwise.
     */
    boolean canInsertItem (Item item);

    /**
     * Checks if the amount can be inserted into the input slot.
     * @return true if the amount can be inserted, false otherwise.
     */
    boolean canInsertAmount (int count);

    /**
     * FE/t - The Forge Energy used per tick operation.
     * @return any amount
     */
    int getEnergyAmount ();

}
