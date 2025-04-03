package net.superscary.fluxmachines.api.inventory;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

public interface MachineFluidInventory {

    FluidStack getFluid ();

    IFluidHandler getTank (@Nullable Direction direction);

    default boolean hasFluidStackInSlot (int slot, MachineItemStackHandler inventory) {
        return !inventory.getStackInSlot(slot).isEmpty()
                && inventory.getStackInSlot(slot).getCapability(Capabilities.FluidHandler.ITEM, null) != null
                && !inventory.getStackInSlot(slot).getCapability(Capabilities.FluidHandler.ITEM, null).getFluidInTank(0).isEmpty();
    }

    default boolean hasFluidHandlerInSlot (int slot, MachineItemStackHandler inventory, FluidTank tank) {
        return !inventory.getStackInSlot(slot).isEmpty()
                && inventory.getStackInSlot(slot).getCapability(Capabilities.FluidHandler.ITEM, null) != null
                && (inventory.getStackInSlot(slot).getCapability(Capabilities.FluidHandler.ITEM, null).getFluidInTank(0).isEmpty() ||
                FluidUtil.tryFluidTransfer(inventory.getStackInSlot(slot).getCapability(Capabilities.FluidHandler.ITEM, null), tank, Integer.MAX_VALUE, false) != FluidStack.EMPTY);
    }

    default void transferToTank (int slot, MachineItemStackHandler inventory, FluidTank tank, Player player, boolean doDrain) {
        FluidActionResult result = FluidUtil.tryEmptyContainer(inventory.getStackInSlot(slot), tank, Integer.MAX_VALUE, player, doDrain);
        if (result.result != ItemStack.EMPTY) {
            inventory.setStackInSlot(slot, result.result);
        }
    }

    default void transferToHandler (int slot, MachineItemStackHandler inventory, FluidTank tank, boolean doFill) {
        FluidActionResult result = FluidUtil.tryFillContainer(inventory.getStackInSlot(slot), tank, Integer.MAX_VALUE, null, doFill);
        if (result.result != ItemStack.EMPTY) {
            inventory.setStackInSlot(slot, result.result);
        }
    }

}
