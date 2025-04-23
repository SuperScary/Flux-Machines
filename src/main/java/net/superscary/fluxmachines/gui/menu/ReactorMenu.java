package net.superscary.fluxmachines.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.superscary.fluxmachines.api.gui.GuiPower;
import net.superscary.fluxmachines.core.block.reactor.ReactorCoreBlock;
import net.superscary.fluxmachines.core.blockentity.reactor.ReactorCoreBlockEntity;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.registries.FMMenus;
import net.superscary.fluxmachines.gui.menu.base.BaseMenu;

public class ReactorMenu extends BaseMenu<ReactorCoreBlock, ReactorCoreBlockEntity> implements GuiPower {

	public ReactorMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData) {
		this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
	}

	public ReactorMenu (int containerId, Inventory inventory, BlockEntity blockEntity) {
		super(FMMenus.REACTOR_MENU.get(), containerId, inventory, FMBlocks.REACTOR_CORE.block(), (ReactorCoreBlockEntity) blockEntity);
	}

	@Override
	public int getPower () {
		return blockEntity.getEnergyStorage().getEnergyStored();
	}

	@Override
	public int defineSlotCount () {
		return 2;
	}

	@Override
	public void addSlots () {
		this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), getNextIndex(), 11, 81));
		this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), getNextIndex(), 11, 149));
	}

	@Override
	public boolean isUpgradeable () {
		return false;
	}

	@Override
	public void addPlayerInventory (Inventory playerInventory) {
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				this.addSlot(new Slot(playerInventory, col + row * 9 + 9, (8 + getUpgradeableMoveFactor() + col * 18), 123 + row * 18));
			}
		}
	}

	@Override
	public void addPlayerHotbar (Inventory playerInventory) {
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, (8 + getUpgradeableMoveFactor() + i * 18), 181));
		}
	}
}
