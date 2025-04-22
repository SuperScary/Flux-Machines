package net.superscary.fluxmachines.core.blockentity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.superscary.fluxmachines.api.inventory.MachineFluidInventory;
import net.superscary.fluxmachines.api.inventory.MachineItemStackHandler;
import net.superscary.fluxmachines.core.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.gui.menu.FluidTankMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidTankBlockEntity extends FMBaseBlockEntity implements MachineFluidInventory {

    private final FluidTank fluidTank = new FluidTank(64_000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isFluidValid (@NotNull FluidStack stack) {
            return true;
        }
    };

    public FluidTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public MachineItemStackHandler createInventory() {
        return new MachineItemStackHandler(2) {
            @Override
            public int getSlotLimit(int slot) {
                return slot == 1 ? 1 : super.getSlotLimit(slot);
            }
        };
    }

    @Override
    public AbstractContainerMenu menu (int id, Inventory playerInventory, Player player) {
        return new FluidTankMenu(id, playerInventory, this);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (hasFluidStackInSlot(0, this.getInventory())) {
            transferToTank(0, this.getInventory(), fluidTank, null, true);
        }

        if (hasFluidHandlerInSlot(1, this.getInventory(), fluidTank)) {
            transferToHandler(1, this.getInventory(), fluidTank, true);
        }

        pushAbove();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.fluxmachines.fluid_tank");
    }

    @Override
    public FluidStack getFluid() {
        return fluidTank.getFluid();
    }

    @Override
    public FluidTank getTank(@Nullable Direction direction) {
        return fluidTank;
    }

    @Override
    public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
        tag = fluidTank.writeToNBT(registries, tag);
        super.saveClientData(tag, registries);
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.loadClientData(tag, registries);
        fluidTank.readFromNBT(registries, tag);
    }

    private void pushAbove () {
        FluidUtil.getFluidHandler(level, worldPosition.above(), null).ifPresent(iFluidHandler -> {
            FluidUtil.tryFluidTransfer(iFluidHandler, fluidTank, Integer.MAX_VALUE, true);
        });
    }

}
