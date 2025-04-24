package net.superscary.fluxmachines.core.blockentity.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.superscary.fluxmachines.api.inventory.MachineItemStackHandler;
import net.superscary.fluxmachines.attributes.Attribute;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.util.keys.Keys;
import net.superscary.fluxmachines.core.util.tags.FMTag;
import net.superscary.fluxmachines.gui.menu.ReactorMenu;
import org.jetbrains.annotations.NotNull;

public class ReactorCoreBlockEntity extends FMBasePoweredBlockEntity {

	private FluidTank heatTank;
	private FluidTank coolantTank;
	private FluidTank fuelTank;

	public ReactorCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		this(type, pos, blockState, 20_000_000, Integer.MAX_VALUE);
	}

	public ReactorCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int capacity, int maxReceive) {
		super(type, pos, blockState, Attribute.Builder.of(Keys.MAX_POWER, capacity), Attribute.Builder.of(Keys.MAX_DRAIN, maxReceive));
		assignTanks();
	}

	@Override
	public void tick(Level level, BlockPos pos, BlockState state) {

	}

	@Override
	public MachineItemStackHandler createInventory() {
		return INVENTORY_SINGLE;
	}

	@Override
	public AbstractContainerMenu menu(int id, Inventory playerInventory, Player player) {
		return new ReactorMenu(id, playerInventory, this);
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable("multiblock.fluxmachines.reactor");
	}

	public FluidTank getHeatTank () {
		return this.heatTank;
	}

	public FluidTank getCoolantTank () {
		return this.coolantTank;
	}

	public FluidTank getFuelTank () {
		return this.fuelTank;
	}

	protected void assignTanks () {
		this.heatTank = new FluidTank(64_000) {
			@Override
			protected void onContentsChanged() {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}

			@Override
			public boolean isFluidValid(@NotNull FluidStack stack) {
				return true;
			}
		};
		this.coolantTank = new FluidTank(64_000) {
			@Override
			protected void onContentsChanged() {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}

			@Override
			public boolean isFluidValid(@NotNull FluidStack stack) {
				return stack.is(FMTag.Fluids.REACTOR_COOLANT);
			}
		};
		this.fuelTank = new FluidTank(64_000) {
			@Override
			protected void onContentsChanged() {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}

			@Override
			public boolean isFluidValid(@NotNull FluidStack stack) {
				return stack.is(FMTag.Fluids.REACTOR_FUEL);
			}
		};
	}

}
