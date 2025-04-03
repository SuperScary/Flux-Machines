package net.superscary.fluxmachines.core.util.inventory.slots;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.superscary.fluxmachines.core.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.core.item.upgrade.UpgradeBase;
import net.superscary.fluxmachines.gui.screen.base.BaseScreen;

public class UpgradeSlot extends SlotItemHandler {

    public UpgradeSlot (IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace (ItemStack stack) {
        return stack.getItem() instanceof UpgradeBase;
    }

    @Override
    public boolean isActive () {
        return true;
    }
}
