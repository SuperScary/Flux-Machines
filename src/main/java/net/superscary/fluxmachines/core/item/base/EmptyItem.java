package net.superscary.fluxmachines.core.item.base;

import net.minecraft.world.item.CreativeModeTab;

public class EmptyItem extends BaseItem {

    public EmptyItem (Properties properties) {
        super(properties.stacksTo(1));
    }

    /**
     * Don't add to creative tab.
     * @param output
     */
    @Override
    public void addToCreativeTab (CreativeModeTab.Output output) {

    }
}
