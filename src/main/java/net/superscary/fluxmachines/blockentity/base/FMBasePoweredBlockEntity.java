package net.superscary.fluxmachines.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.superscary.fluxmachines.util.keys.Keys;

public abstract class FMBasePoweredBlockEntity extends FMBaseBlockEntity {

    private final EnergyStorage energyStorage;

    public FMBasePoweredBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, int capacity, int maxReceive) {
        super(type, pos, blockState);
        this.energyStorage = new EnergyStorage(capacity, maxReceive);
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

    public EnergyStorage getEnergyStorage () {
        return energyStorage;
    }

    public void updateBlockState (BlockState state) {
        assert level != null;
        level.setBlockAndUpdate(getBlockPos(), state);
    }

    public abstract void tick (Level pLevel, BlockPos pPos, BlockState pState);

}
