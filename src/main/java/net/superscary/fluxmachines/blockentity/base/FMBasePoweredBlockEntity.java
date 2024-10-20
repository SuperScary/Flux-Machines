package net.superscary.fluxmachines.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.superscary.fluxmachines.util.keys.Keys;
import org.jetbrains.annotations.NotNull;

public abstract class FMBasePoweredBlockEntity extends FMBaseBlockEntity {

    private final EnergyStorage energyStorage;

    public FMBasePoweredBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, int capacity, int maxReceive) {
        super(type, pos, blockState);
        this.energyStorage = new EnergyStorage(capacity, maxReceive);
    }

    @Override
    protected void saveAdditional (@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(Keys.POWER, energyStorage.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional (@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        energyStorage.deserializeNBT(registries, tag.getCompound(Keys.POWER));
    }

    public EnergyStorage getEnergyStorage () {
        return energyStorage;
    }

    public abstract void tick (Level pLevel, BlockPos pPos, BlockState pState);

}
