package net.superscary.fluxmachines.core.blockentity.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.superscary.fluxmachines.api.inventory.MachineItemStackHandler;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.util.keys.Keys;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CableBlockEntity extends FMBasePoweredBlockEntity {

    public static final String ENERGY_TAG = Keys.POWER;

    // Cached outputs
    private Set<BlockPos> outputs = null;

    public static final int MAX_TRANSFER = 100;
    public static final int CAPACITY = 1000;

    public CableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, CAPACITY, MAX_TRANSFER);
    }

    @Override
    public MachineItemStackHandler createInventory() {
        return MachineItemStackHandler.EMPTY;
    }

    @Override
    public AbstractContainerMenu menu(int id, Inventory playerInventory, Player player) {
        return null;
    }

    // This function will cache all outputs for this cable network. It will do this
    // by traversing all cables connected to this cable and then check for all energy
    // receivers around those cables.
    private void checkOutputs() {
        if (outputs == null) {
            outputs = new HashSet<>();
            traverse(worldPosition, cable -> {
                // Check for all energy receivers around this position (ignore cables)
                for (Direction direction : Direction.values()) {
                    BlockPos p = cable.getBlockPos().relative(direction);
                    BlockEntity te = level.getBlockEntity(p);
                    if (te != null && !(te instanceof CableBlockEntity)) {
                        IEnergyStorage handler = level.getCapability(Capabilities.EnergyStorage.BLOCK, p, null);
                        if (handler != null) {
                            if (handler.canReceive()) {
                                outputs.add(p);
                            }
                        }
                    }
                }
            });
        }
    }

    public void markDirty() {
        traverse(worldPosition, cable -> cable.outputs = null);
    }

    // This is a generic function that will traverse all cables connected to this cable
    // and call the given consumer for each cable.
    private void traverse(BlockPos pos, Consumer<CableBlockEntity> consumer) {
        Set<BlockPos> traversed = new HashSet<>();
        traversed.add(pos);
        consumer.accept(this);
        traverse(pos, traversed, consumer);
    }

    private void traverse(BlockPos pos, Set<BlockPos> traversed, Consumer<CableBlockEntity> consumer) {
        for (Direction direction : Direction.values()) {
            BlockPos p = pos.relative(direction);
            if (!traversed.contains(p)) {
                traversed.add(p);
                if (level.getBlockEntity(p) instanceof CableBlockEntity cable) {
                    consumer.accept(cable);
                    cable.traverse(p, traversed, consumer);
                }
            }
        }
    }

    public void tickServer() {
        if (getEnergyStorage().getEnergyStored() > 0) {
            // Only do something if we have energy
            checkOutputs();
            if (!outputs.isEmpty()) {
                // Distribute energy over all outputs
                int amount = getEnergyStorage().getEnergyStored() / outputs.size();
                for (BlockPos p : outputs) {
                    IEnergyStorage handler = level.getCapability(Capabilities.EnergyStorage.BLOCK, p, null);
                    if (handler != null) {
                        if (handler.canReceive()) {
                            int received = handler.receiveEnergy(amount, false);
                            getEnergyStorage().extractEnergy(received, false);
                        }
                    }
                }
            }
        }
    }

    public IEnergyStorage getEnergyHandler() {
        return getEnergyStorage();
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        tickServer();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("");
    }

    @Override
    public FluidHandlerItemStack getFluidInventory() {
        return null;
    }

    @Override
    public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
        tag.put(Keys.POWER, getEnergyStorage().serializeNBT(registries));
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        getEnergyStorage().deserializeNBT(registries, IntTag.valueOf(tag.getInt(Keys.POWER)));
    }
}