package net.superscary.fluxmachines.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.superscary.fluxmachines.api.data.BlockData;
import net.superscary.fluxmachines.api.inventory.InventoryHolder;
import net.superscary.fluxmachines.hook.WrenchHook;
import net.superscary.fluxmachines.util.keys.Keys;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class FMBaseBlockEntity extends BlockEntity implements MenuProvider, BlockData, InventoryHolder {

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

    /**
     * Save entity data to the dropped item
     * @param stack
     * @param registries
     */
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

    /**
     * TODO: save data to item and drop that item rather than drop contents.
     * Allows disassembly with wrench. Called by {@link WrenchHook#onPlayerUseBlock(Player, Level, InteractionHand, BlockHitResult)}
     * @param player    {@link Player} the player
     * @param level     {@link Level} the level
     * @param hitResult {@link BlockHitResult} hit result of the interaction
     * @param stack    the {@link ItemStack} used. Already checked to contain {@link net.superscary.fluxmachines.util.tags.FMTag.Items#WRENCH}
     * @return {@link InteractionResult}
     */
    public InteractionResult disassemble (Player player, Level level, BlockHitResult hitResult, ItemStack stack) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);
        var block = state.getBlock();

        if (level instanceof ServerLevel serverLevel) {
            var drops = Block.getDrops(state, serverLevel, pos, this, player, stack);

            for (var item : drops) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), item);
            }
        }

        block.playerWillDestroy(level, pos, state, player);
        level.removeBlock(pos, false);
        block.destroy(level, pos, getBlockState());
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    public InteractionResult rotateOnAxis (Level level, BlockHitResult hitResult, BlockState state, Direction direction) {
        if (!level.isClientSide()) {
            var currentDirection = state.getValue(BlockStateProperties.FACING);
            if (direction != Direction.UP && direction != Direction.DOWN) {
                level.setBlockAndUpdate(hitResult.getBlockPos(), state.setValue(BlockStateProperties.FACING, currentDirection.getClockWise()));
            } else {
                // UP AND DOWN HANDLING
                level.setBlockAndUpdate(hitResult.getBlockPos(), state.setValue(BlockStateProperties.FACING, currentDirection.getClockWise(Direction.Axis.X)));
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public ItemStackHandler getInventory () {
        return inventory;
    }
}
