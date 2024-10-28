package net.superscary.fluxmachines.core.item.tool;

import net.minecraft.world.item.ItemStack;
import net.superscary.fluxmachines.core.item.base.BaseItem;

public class Wrench extends BaseItem {

    public Wrench (Properties properties) {
        super(properties.durability(143).setNoRepair());
    }

    @Override
    public boolean isDamageable (ItemStack stack) {
        return true;
    }

}
