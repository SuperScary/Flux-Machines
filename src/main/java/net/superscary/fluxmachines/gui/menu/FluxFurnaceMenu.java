package net.superscary.fluxmachines.gui.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.superscary.fluxmachines.blockentity.machine.FluxFurnaceBlockEntity;
import net.superscary.fluxmachines.registries.FMBlocks;
import net.superscary.fluxmachines.registries.FMMenus;
import net.superscary.fluxmachines.util.inventory.OutputSlot;
import net.superscary.fluxmachines.util.inventory.QuickMoveStack;
import org.jetbrains.annotations.NotNull;

public class FluxFurnaceMenu extends AbstractContainerMenu {

    public final FluxFurnaceBlockEntity blockEntity;
    private final Level level;

    public FluxFurnaceMenu (int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public FluxFurnaceMenu (int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(FMMenus.FLUX_FURNACE_MENU.get(), containerId);
        this.blockEntity = (FluxFurnaceBlockEntity) blockEntity;
        this.level = inventory.player.level();

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory(), 0, 56, 35));
        this.addSlot(new OutputSlot(this.blockEntity.getInventory(), 1, 116, 35));

    }

    @Override
    public @NotNull ItemStack quickMoveStack (@NotNull Player player, int index) {
        return new QuickMoveStack(this, 2, player, index).quickMoveStack();
    }

    @Override
    public boolean stillValid (Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, FMBlocks.FLUX_FURNACE.block());
    }

    private void addPlayerInventory (Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar (Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public int getPower () {
        return blockEntity.getEnergyStorage().getEnergyStored();
    }

}
