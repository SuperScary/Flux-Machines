package net.superscary.fluxmachines.gui.menu.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.core.util.inventory.QuickMoveStack;
import org.jetbrains.annotations.NotNull;

public abstract class BaseMenu<B extends FMBaseEntityBlock<?>, T extends FMBaseBlockEntity> extends AbstractContainerMenu {

    public final B block;
    public final T blockEntity;
    private final Level level;
    private int index = 0;

    public BaseMenu (MenuType<?> type, int containerId, Inventory inventory, B block, T blockEntity) {
        super(type, containerId);
        this.block = block;
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        addSlots();
        addUpgradeSlots();

    }

    @SuppressWarnings({"unchecked", "unused"})
    public BaseMenu (MenuType<?> type, int containerId, Inventory inventory, B block, FriendlyByteBuf extraData) {
        this(type, containerId, inventory, block, (T) inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    /**
     * Define the number of slots in the menu
     */
    public abstract int defineSlotCount ();

    /**
     * Add the slots to the menu
     */
    public abstract void addSlots ();

    public void addUpgradeSlots () {
    }

    @Override
    public @NotNull ItemStack quickMoveStack (@NotNull Player player, int index) {
        return new QuickMoveStack(this, defineSlotCount(), player, index).quickMoveStack();
    }

    @Override
    public boolean stillValid (@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, block);
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

    public int getNextIndex () {
        return index++;
    }

}
