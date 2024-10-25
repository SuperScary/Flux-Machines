package net.superscary.fluxmachines.core.util.inventory.slots;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.superscary.fluxmachines.core.registries.FMItems;

public class OutputSlot extends SlotItemHandler {

    public OutputSlot (IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace (ItemStack stack) {
        return false;
    }

    @Override
    public boolean mayPickup (Player playerIn) {
        return !getItem().is(FMItems.EMPTY.asItem());
    }
}
