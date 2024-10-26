package net.superscary.fluxmachines.blockentity.base;

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
import net.superscary.fluxmachines.api.energy.Decays;
import net.superscary.fluxmachines.api.energy.EnergyDecay;
import net.superscary.fluxmachines.api.energy.FMEnergyStorage;
import net.superscary.fluxmachines.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.registries.FMDataComponents;
import net.superscary.fluxmachines.core.util.keys.Keys;
import org.jetbrains.annotations.Nullable;

public abstract class FMBasePoweredBlockEntity extends FMBaseBlockEntity implements EnergyDecay, Decays {

    private final FMEnergyStorage energyStorage;
    private int energy = 0;

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

    public void tick (Level pLevel, BlockPos pPos, BlockState pState) {
        if (decayChance(decayPercentageChance())) {
            decayEnergy(decayAmount(), getEnergyStorage());
        }
        setChanged();
    }

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

}
