package net.superscary.fluxmachines.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.superscary.fluxmachines.core.block.misc.FluidTankBlock;
import net.superscary.fluxmachines.core.blockentity.misc.FluidTankBlockEntity;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.registries.FMMenus;
import net.superscary.fluxmachines.core.util.inventory.slots.OutputSlot;
import net.superscary.fluxmachines.gui.menu.base.BaseMenu;

public class FluidTankMenu extends BaseMenu<FluidTankBlock, FluidTankBlockEntity> {

    public FluidTankMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public FluidTankMenu (int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(FMMenus.FLUID_TANK_MENU.get(), containerId, inventory, FMBlocks.FLUID_TANK.block(), (FluidTankBlockEntity) blockEntity);
    }

    @Override
    public int defineSlotCount() {
        return 6;
    }

    @Override
    public void addSlots() {
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), getNextIndex(), 8, 54));
        this.addSlot(new OutputSlot(this.blockEntity.getInventory(), getNextIndex(), 152, 54));
    }
}
