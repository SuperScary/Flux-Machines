package net.superscary.fluxmachines.core.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.superscary.fluxmachines.api.blockentity.Upgradeable;
import net.superscary.fluxmachines.api.data.PropertyComponent;
import net.superscary.fluxmachines.api.data.DataLinkInteract;
import net.superscary.fluxmachines.api.energy.FMEnergyStorage;
import net.superscary.fluxmachines.api.energy.PoweredBlock;
import net.superscary.fluxmachines.api.network.NetworkComponent;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.registries.FMDataComponents;
import net.superscary.fluxmachines.core.util.helper.PropertyHelper;
import net.superscary.fluxmachines.core.util.keys.Keys;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class FMBasePoweredBlockEntity extends FMBaseBlockEntity implements PoweredBlock, Upgradeable, DataLinkInteract {

    private final FMEnergyStorage energyStorage;
    private ArrayList<PropertyComponent<?>> dataComponents = new ArrayList<>();

    public FMBasePoweredBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, int capacity, int maxReceive) {
        this(type, pos, blockState, capacity, maxReceive, 0);
    }

    public FMBasePoweredBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, int capacity, int maxReceive, int current) {
        super(type, pos, blockState);
        this.energyStorage = new FMEnergyStorage(capacity, maxReceive, maxReceive, current);
    }

    @Override
    public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.saveClientData(tag, registries);
        tag.put(Keys.POWER, energyStorage.serializeNBT(registries));
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.loadClientData(tag, registries);
        energyStorage.deserializeNBT(registries, IntTag.valueOf(tag.getInt(Keys.POWER)));
    }

    @Override
    public FMEnergyStorage getEnergyStorage () {
        return energyStorage;
    }

    public void updateBlockState (BlockState state) {
        assert level != null;
        level.setBlockAndUpdate(getBlockPos(), state);
    }

    @Override
    public InteractionResult disassemble (Player player, Level level, BlockHitResult hitResult, ItemStack stack, @Nullable ItemStack existingData) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);
        var block = (FMBaseEntityBlock<?>) state.getBlock();
        var itemstack = getEither(existingData, new ItemStack(block));
        if (level instanceof ServerLevel) {
            itemstack.set(FMDataComponents.ENERGY_STORED, getEnergyStorage().getEnergyStored());
            itemstack.set(FMDataComponents.ENERGY_MAX, getEnergyStorage().getMaxEnergyStored());
        }
        return super.disassemble(player, level, hitResult, stack, itemstack);
    }

    /**
     * Called every tick. Main tick method for base powered block entities.
     */
    public abstract void tick (Level level, BlockPos pos, BlockState state);

    @Override
    public void setData (ItemStack stack) {
        if (stack.has(FMDataComponents.ENERGY_STORED) && stack.has(FMDataComponents.ENERGY_MAX)) {
            var stored = stack.get(FMDataComponents.ENERGY_STORED);
            var max = stack.get(FMDataComponents.ENERGY_MAX);
            getEnergyStorage().setStored(stored);
            getEnergyStorage().setMaxStorage(max);
        }
        super.setData(stack);
    }

    @Override
    public ArrayList<PropertyComponent<?>> getLinkedData () {
        dataComponents.clear();
        for (var data : getBlockState().getProperties()) {
            dataComponents.add(PropertyHelper.of(data, getBlockState()));
        }
        return this.dataComponents;
    }

    @Override
    public void setLinkedData (ArrayList<PropertyComponent<?>> data) {
        if (data.isEmpty()) return;
        this.dataComponents = data;
        for (var component : data) {
            var state = getBlockState();
            state = component.withProperty(state);
            updateBlockState(state);
        }
    }

}
