package net.superscary.fluxmachines.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.superscary.fluxmachines.api.data.BlockData;
import net.superscary.fluxmachines.util.keys.Keys;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class FMBaseBlockEntity extends BlockEntity implements MenuProvider, BlockData {

    public final ItemStackHandler INVENTORY_SINGLE = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged (int slot) {
            setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    public final ItemStackHandler INVENTORY_1X1 = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged (int slot) {
            setChanged();
            assert level != null;
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private final ItemStackHandler inventory = createInventory();

    public FMBaseBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public abstract ItemStackHandler createInventory ();

    public abstract AbstractContainerMenu menu (int id, Inventory playerInventory, Player player);

    @Nullable
    @Override
    public AbstractContainerMenu createMenu (int id, @NotNull Inventory inventory, @NotNull Player player) {
        return menu(id, inventory, player);
    }

    @Override
    protected void saveAdditional (@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        saveClientData(tag, registries);
    }

    @Override
    protected void loadAdditional (@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        loadClientData(tag, registries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket () {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @MustBeInvokedByOverriders
    public void addAdditionalDrops (Level level, BlockPos pos, List<ItemStack> drops) {

    }

    @Override
    public @NotNull CompoundTag getUpdateTag (HolderLookup.@NotNull Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
        tag.put(Keys.INVENTORY, inventory.serializeNBT(registries));
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        inventory.deserializeNBT(registries, tag.getCompound(Keys.INVENTORY));
    }

    @Override
    public void handleUpdateTag (@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        loadClientData(tag, lookupProvider);
    }

    @Override
    public void saveToItem (@NotNull ItemStack stack, HolderLookup.@NotNull Provider registries) {
        super.saveToItem(stack, registries);
    }

    public void clearContents () {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void drops () {
        var container = new SimpleContainer(getInventory().getSlots());
        for (int i = 0; i < getInventory().getSlots(); i++) {
            container.setItem(i, getInventory().getStackInSlot(i));
        }
        assert level != null;
        Containers.dropContents(level, worldPosition, container);
    }

    public InteractionResult disassembleWithWrench (Player player, Level level, BlockHitResult hitResult, ItemStack wrench) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);
        var block = state.getBlock();

        if (level instanceof ServerLevel serverLevel) {
            var drops = Block.getDrops(state, serverLevel, pos, this, player, wrench);

            for (var item : drops) {
                player.getInventory().placeItemBackInInventory(item);
            }
        }

        block.playerWillDestroy(level, pos, state, player);
        level.removeBlock(pos, false);
        block.destroy(level, pos, getBlockState());
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public ItemStackHandler getInventory () {
        return inventory;
    }
}
