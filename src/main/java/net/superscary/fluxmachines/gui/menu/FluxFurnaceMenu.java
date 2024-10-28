package net.superscary.fluxmachines.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.superscary.fluxmachines.api.gui.GuiPower;
import net.superscary.fluxmachines.core.block.machine.FluxFurnaceBlock;
import net.superscary.fluxmachines.core.blockentity.machine.FluxFurnaceBlockEntity;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.registries.FMMenus;
import net.superscary.fluxmachines.core.util.inventory.slots.OutputSlot;
import net.superscary.fluxmachines.core.util.inventory.slots.UpgradeSlot;
import net.superscary.fluxmachines.gui.menu.base.BaseMenu;

import static net.superscary.fluxmachines.gui.screen.base.BaseScreen.SETTINGS_PANEL_X_HALF;
import static net.superscary.fluxmachines.gui.screen.base.BaseScreen.Y;

public class FluxFurnaceMenu extends BaseMenu<FluxFurnaceBlock, FluxFurnaceBlockEntity> implements GuiPower {

    public FluxFurnaceMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public FluxFurnaceMenu (int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(FMMenus.FLUX_FURNACE_MENU.get(), containerId, inventory, FMBlocks.FLUX_FURNACE.block(), (FluxFurnaceBlockEntity) blockEntity);
    }

    @Override
    public int defineSlotCount () {
        return 6;
    }

    @Override
    public void addSlots () {
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), getNextIndex(), 56, 35));
        this.addSlot(new OutputSlot(this.blockEntity.getInventory(), getNextIndex(), 116, 35));
    }

    @Override
    public void addUpgradeSlots () {
        super.addUpgradeSlots();
        this.addSlot(new UpgradeSlot(this.blockEntity.getInventory(), getNextIndex(), 198 + SETTINGS_PANEL_X_HALF - 18, Y + 48));
        this.addSlot(new UpgradeSlot(this.blockEntity.getInventory(), getNextIndex(), 198 + SETTINGS_PANEL_X_HALF, Y + 48));
        this.addSlot(new UpgradeSlot(this.blockEntity.getInventory(), getNextIndex(), 198 + SETTINGS_PANEL_X_HALF - 18, Y + 66));
        this.addSlot(new UpgradeSlot(this.blockEntity.getInventory(), getNextIndex(), 198 + SETTINGS_PANEL_X_HALF, Y + 66));
    }

    @Override
    public int getPower () {
        return blockEntity.getEnergyStorage().getEnergyStored();
    }

}
