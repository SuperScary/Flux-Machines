package net.superscary.fluxmachines.api.blockentity;

import net.minecraft.world.item.Item;

public interface EnergizedCrafter {

    boolean hasEnoughEnergy (int required);

    boolean canInsertItem (Item item);

    boolean canInsertAmount (int count);

    /**
     * FE/t - The Forge Energy used per tick operation.
     * @return any amount
     */
    int getEnergyAmount ();

}
