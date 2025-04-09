package net.superscary.fluxmachines.core.blockentity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.superscary.fluxmachines.api.blockentity.Crafter;
import net.superscary.fluxmachines.api.inventory.MachineFluidInventory;
import net.superscary.fluxmachines.api.inventory.MachineItemStackHandler;
import net.superscary.fluxmachines.core.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.core.recipe.CrucibleRecipe;
import net.superscary.fluxmachines.core.registries.FMRecipes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrucibleBlockEntity extends FMBaseBlockEntity implements MachineFluidInventory, Crafter<CrucibleRecipe> {

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
	private float progress;

	public CrucibleBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
		progress = 0;
	}

	@Override
	public MachineItemStackHandler createInventory () {
		return new MachineItemStackHandler(5) {
			@Override
			protected void onContentsChanged (int slot) {
				setChanged();
				assert level != null;
				if (!level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public AbstractContainerMenu menu (int id, Inventory playerInventory, Player player) {
		return null;
	}

	@Override
	public void tick (Level level, BlockPos pos, BlockState state) {

	}

	@Override
	public @NotNull Component getDisplayName () {
		return Component.translatable("block.fluxmachines.crucible");
	}

	@Override
	public FluidStack getFluid () {
		return fluidTank.getFluid();
	}

	@Override
	public FluidTank getTank (@Nullable Direction direction) {
		return fluidTank;
	}

	@Override
	public void craftItem (ItemStack result) {

	}

	@Override
	public boolean hasRecipe (BlockState state) {
		return false;
	}

	@Override
	public RecipeType<CrucibleRecipe> getRecipeType () {
		return FMRecipes.CRUCIBLE_TYPE.get();
	}

	@Override
	public @NotNull Optional<RecipeHolder<CrucibleRecipe>> getCurrentRecipe () {
		return Optional.empty();
	}

	@Override
	public void increaseCraftingProgress () {
		progress++;
	}

	@Override
	public float getProgress () {
		return progress;
	}

	@Override
	public boolean isCrafting () {
		return false;
	}

	@Override
	public int getScaledProgress () {
		return 0;
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
}
