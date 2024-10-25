package net.superscary.fluxmachines.core.util.inventory.slots;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class FuelSlot extends SlotItemHandler {

    public FuelSlot (IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace (ItemStack stack) {
        return stack.is(ItemTags.COALS);
    }
}
