package net.superscary.fluxmachines.core.item.upgrade;

import net.minecraft.world.level.block.state.BlockState;
import net.superscary.fluxmachines.api.blockentity.Upgradeable;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.item.base.BaseItem;

public class UpgradeBase extends BaseItem {

    private boolean hasBeenApplied = false;

    public UpgradeBase (Properties properties) {
        super(properties.stacksTo(16));
    }

    public void functionalUpgrade (FMBaseEntityBlock<?> block, BlockState state) {
        if (block instanceof Upgradeable) {

        } else throw new IllegalStateException("Cannot apply an upgrade to a non-upgradeable block.");
    }

    public boolean setApplied (boolean hasBeenApplied) {
        return this.hasBeenApplied = hasBeenApplied;
    }

    public boolean hasBeenApplied () {
        return this.hasBeenApplied;
    }

}
