package net.superscary.fluxmachines.core.blockentity.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.superscary.fluxmachines.api.energy.EnergizedCrafter;
import net.superscary.fluxmachines.api.network.NetworkComponent;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.recipe.FluxSmeltingRecipe;
import net.superscary.fluxmachines.core.recipe.input.FluxSmeltingInput;
import net.superscary.fluxmachines.core.recipe.manager.FluxSmeltingManager;
import net.superscary.fluxmachines.core.registries.FMRecipes;
import net.superscary.fluxmachines.core.util.Utilities;
import net.superscary.fluxmachines.core.util.block.FMBlockStates;
import net.superscary.fluxmachines.core.util.helper.PropertyHelper;
import net.superscary.fluxmachines.core.util.helper.Utils;
import net.superscary.fluxmachines.core.util.keys.Keys;
import net.superscary.fluxmachines.gui.menu.FluxFurnaceMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class FluxFurnaceBlockEntity extends FMBasePoweredBlockEntity implements EnergizedCrafter<FluxSmeltingRecipe>, NetworkComponent {

    private int progress;
    private boolean isCrafting = false;

    public FluxFurnaceBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        this(type, pos, blockState, 0);
    }

    public FluxFurnaceBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, int current) {
        super(type, pos, blockState, 100_000, 256, current);
        progress = 0;
    }

    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);
        FluxSmeltingManager.instance().refresh(level.getRecipeManager());
    }

    @Override
    public ItemStackHandler createInventory () {
        return INVENTORY_1X1;
    }

    @Override
    public AbstractContainerMenu menu (int id, Inventory playerInventory, Player player) {
        return new FluxFurnaceMenu(id, playerInventory, this);
    }

    @Override
    public @NotNull Component getDisplayName () {
        return Component.translatable("block.fluxmachines.flux_furnace");
    }

    @Override
    public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.saveClientData(tag, registries);
        tag.putFloat(Keys.PROGRESS, getProgress());
        tag.putBoolean(Keys.CRAFTING, isCrafting());
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.loadClientData(tag, registries);
        progress = tag.getInt(Keys.PROGRESS);
        isCrafting = tag.getBoolean(Keys.CRAFTING);
    }

    @Override
    public void tick (Level level, BlockPos pos, BlockState state) {
        if (Utilities.isDevEnvironment()) getEnergyStorage().receiveEnergy(10_000, false); // TODO: Dev only environment
        if (hasRecipe(state)) {
            isCrafting = true;
            increaseCraftingProgress();
            getEnergyStorage().extractEnergy(getEnergyAmount(), false);

            if (hasFinished()) {
                craftItem(Objects.requireNonNull(getRecipeResultItem(level)));
                progress = 0;
                isCrafting = false;
            }
        }
    }

    @Override
    public void craftItem (ItemStack result) {
        this.getInventory().extractItem(0, 1, false);
        this.getInventory().setStackInSlot(1, new ItemStack(result.getItem(), this.getInventory().getStackInSlot(1).getCount() + result.getCount()));
        isCrafting = false;
        progress = 0;
        PropertyHelper.setValues(getBlockState(), isCrafting(), BlockStateProperties.POWERED, FMBlockStates.REDSTONE_ON);
    }

    @Override
    public boolean hasRecipe (BlockState state) {
        var recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            updateBlockState(state.setValue(BlockStateProperties.CRAFTING, false));
            return false;
        }
        var result = getRecipeResultItem(Objects.requireNonNull(getLevel()));
        assert result != null;
        var hasRecipe = this.canInsertAmount(result.getCount(), 0, 1, getInventory()) && canInsertItem(getInventory(), result.getItem(), 1) && hasEnoughEnergy(getEnergyAmount());
        updateBlockState(state.setValue(BlockStateProperties.CRAFTING, hasRecipe));
        PropertyHelper.setValues(state, isCrafting(), BlockStateProperties.POWERED, FMBlockStates.REDSTONE_ON);
        return hasRecipe;
    }

    @Override
    public RecipeType<FluxSmeltingRecipe> getRecipeType () {
        return FMRecipes.FLUX_SMELTING_TYPE.get();
    }

    @Override
    public @NotNull Optional<RecipeHolder<FluxSmeltingRecipe>> getCurrentRecipe () {
        assert this.level != null;
        var input = getInventory().getStackInSlot(0);
        if (input == ItemStack.EMPTY) return Optional.empty();

        for (var recipe : FluxSmeltingManager.instance().getConvertedRecipes()) {
            if (Utils.convertToItemStack(recipe.value().input()).getItem().equals(input.getItem())) {
                return Optional.of(recipe);
            }
        }

        return this.level.getRecipeManager().getRecipeFor(getRecipeType(), new FluxSmeltingInput(getInventory().getStackInSlot(0), getEnergyAmount(), 200), level);
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
    public boolean hasEnoughEnergy (int required) {
        var hasEnergy = this.getEnergyStorage().getEnergyStored() >= required;
        if (hasEnergy) this.getEnergyStorage().extractEnergy(required, false);
        return hasEnergy;
    }

    // TODO: Make dynamic based on cook time of inserted item
    // TODO: Ignore crafting time requirement and set based off of SmeltingRecipe.
    // 2x faster than a normal furnace
    private int getCookTime() {
        return getInventory().getStackInSlot(0).getBurnTime(getRecipeType()) / 2;
    }

    @Override
    public int getEnergyAmount () {
        return 8;
    }

    @Override
    public boolean isCrafting () {
        return isCrafting;
    }

    @Override
    public int getScaledProgress() {
        var progress = (int) getProgress();
        var max = getMaxProgress();
        var arrowSize = 26;
        return max != 0 && progress != 0 ? progress * arrowSize / max : 0;
    }

    @Override
    public FluidHandlerItemStack getFluidInventory() {
        return null;
    }
}
