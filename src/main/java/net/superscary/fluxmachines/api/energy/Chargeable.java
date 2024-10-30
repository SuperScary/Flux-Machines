package net.superscary.fluxmachines.api.energy;

import net.minecraft.world.item.ItemStack;

public interface Chargeable {

    void charge (ItemStack stack, int amount);

}
