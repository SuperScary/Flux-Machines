package net.superscary.fluxmachines.api.blockentity;

import net.minecraft.world.item.Item;

public interface EnergizedCrafter {

    boolean hasEnoughEnergy (int required);

    boolean canInsertItem (Item item);

    boolean canInsertAmount (int count);

    int getEnergyAmount ();

}
