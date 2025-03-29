package net.superscary.fluxmachines.api.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class MachineItemStackHandler extends ItemStackHandler {

    public static final MachineItemStackHandler EMPTY = new MachineItemStackHandler(0);

    public MachineItemStackHandler() {
        this(1);
    }

    public MachineItemStackHandler(int size) {
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public MachineItemStackHandler(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 128;
    }

}
