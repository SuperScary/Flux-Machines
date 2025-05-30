package net.superscary.fluxmachines.core.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.superscary.fluxmachines.api.energy.EnergizedCrafter;
import net.superscary.fluxmachines.api.manager.IRecipeManager;
import net.superscary.fluxmachines.api.network.NetworkComponent;
import net.superscary.fluxmachines.api.recipe.FMRecipe;
import net.superscary.fluxmachines.attributes.Attribute.*;
import net.superscary.fluxmachines.core.util.Utilities;
import net.superscary.fluxmachines.core.util.block.FMBlockStates;
import net.superscary.fluxmachines.core.util.helper.PropertyHelper;
import net.superscary.fluxmachines.core.util.helper.Utils;
import net.superscary.fluxmachines.core.util.keys.Keys;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public abstract class BaseEnergyCrafter<T extends FMRecipe<?>> extends FMBasePoweredBlockEntity implements EnergizedCrafter<T>, NetworkComponent {

    private FloatValue progress;
    private BooleanValue isCrafting = Builder.of(Keys.CRAFTING, false);

    public BaseEnergyCrafter(BlockEntityType<?> type, BlockPos pos, BlockState blockState, IntValue capacity, IntValue maxReceive) {
        this(type, pos, blockState, capacity, maxReceive, Builder.of(Keys.POWER, 0));
    }

    public BaseEnergyCrafter(BlockEntityType<?> type, BlockPos pos, BlockState blockState, IntValue capacity, IntValue maxReceive, IntValue current) {
        super(type, pos, blockState, capacity, maxReceive, current);
        progress = Builder.of(Keys.PROGRESS, 0f);
    }

    @Override
    public @NotNull Optional<RecipeHolder<T>> getCurrentRecipe () {
        assert this.level != null;
        var input = getInventory().getStackInSlot(inputSlot());
        if (input == ItemStack.EMPTY || getRecipeManager() == null) return Optional.empty();

        for (var recipe : getRecipeManager().getConvertedRecipes()) {
            if (Utils.convertToItemStack(recipe.value().input()).getItem().equals(input.getItem())) {
                return Optional.of(recipe);
            }
        }

        return Optional.empty();
    }

    @Override
    public void increaseCraftingProgress () {
        progress.increase(1f);
    }

    @Override
    public float getProgress () {
        return progress.get();
    }

    @Override
    public boolean hasEnoughEnergy (int required) {
        var hasEnergy = this.getEnergyStorage().getEnergyStored() >= required;
        if (hasEnergy) this.getEnergyStorage().extractEnergy(required, false);
        return hasEnergy;
    }

    @Override
    public void tick (Level level, BlockPos pos, BlockState state) {
        if (Utilities.isDevEnvironment()) getEnergyStorage().receiveEnergy(10_000, false); // TODO: Dev only environment

        if (getInventory().getStackInSlot(inputSlot()) == ItemStack.EMPTY) {
            progress.set(0f);
            updateBlockState(state.setValue(BlockStateProperties.CRAFTING, false));
        }

        if (hasRecipe(state)) {
            isCrafting.set(true);
            increaseCraftingProgress();
            getEnergyStorage().extractEnergy(getEnergyAmount(), false);

            if (hasFinished()) {
                craftItem(Objects.requireNonNull(getRecipeResultItem(level)));
                progress.set(0f);
                isCrafting.set(false);
            }
        }
    }

    @Override
    public boolean hasRecipe (BlockState state) {
        var recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            updateBlockState(state.setValue(BlockStateProperties.CRAFTING, false));
            return false;
        }
        var result = getRecipeResultItem(Objects.requireNonNull(getLevel()));
        isCrafting.set(result != null);
        var hasRecipe = this.canInsertAmount(result.getCount(), inputSlot(), outputSlot(), getInventory()) && canInsertItem(getInventory(), result.getItem(), outputSlot()) && hasEnoughEnergy(getEnergyAmount());
        updateBlockState(state.setValue(BlockStateProperties.CRAFTING, hasRecipe));
        PropertyHelper.setValues(state, isCrafting(), BlockStateProperties.POWERED, FMBlockStates.REDSTONE_ON);
        return hasRecipe;
    }

    @Override
    public void craftItem (ItemStack result) {
        this.getInventory().extractItem(inputSlot(), 1, false);
        this.getInventory().setStackInSlot(outputSlot(), new ItemStack(result.getItem(), this.getInventory().getStackInSlot(1).getCount() + result.getCount()));
        isCrafting.set(false);
        progress.set(0f);
        PropertyHelper.setValues(getBlockState(), isCrafting(), BlockStateProperties.POWERED, FMBlockStates.REDSTONE_ON);
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
        progress.set(tag.getFloat(Keys.PROGRESS));
        isCrafting.set(tag.getBoolean(Keys.CRAFTING));
    }

    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);
        getRecipeManager().refresh(level.getRecipeManager());
    }

    @Override
    public boolean isCrafting () {
        return isCrafting.getAsBoolean();
    }

    @Override
    public int getScaledProgress() {
        var progress = (int) getProgress();
        var max = getMaxProgress();
        var arrowSize = 26;
        return max != 0 && progress != 0 ? progress * arrowSize / max : 0;
    }

    @Override
    public RecipeType<T> getRecipeType () {
        return getRecipeManager().getRecipeType();
    }

    public abstract int inputSlot ();

    public abstract int outputSlot ();

    public abstract IRecipeManager<T> getRecipeManager();

}
