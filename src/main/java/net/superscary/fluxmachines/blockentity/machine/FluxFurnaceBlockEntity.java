package net.superscary.fluxmachines.blockentity.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.superscary.fluxmachines.api.blockentity.Crafter;
import net.superscary.fluxmachines.api.blockentity.EnergizedCrafter;
import net.superscary.fluxmachines.block.machine.FluxFurnaceBlock;
import net.superscary.fluxmachines.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.gui.menu.FluxFurnaceMenu;
import net.superscary.fluxmachines.util.keys.Keys;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public class FluxFurnaceBlockEntity extends FMBasePoweredBlockEntity implements Crafter<SmeltingRecipe>, EnergizedCrafter {

    public static boolean TESTING = true;

    private int progress;
    private boolean isCrafting = false;

    public FluxFurnaceBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState, 100_000, 256);
        progress = 0;
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
        tag.putInt(Keys.PROGRESS, progress);
        tag.putBoolean(Keys.CRAFTING, isCrafting);
    }

    @Override
    public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
        super.loadClientData(tag, registries);
        progress = tag.getInt(Keys.PROGRESS);
        isCrafting = tag.getBoolean(Keys.CRAFTING);
    }

    @Override
    public void tick (Level pLevel, BlockPos pPos, BlockState pState) {
        var block = (FluxFurnaceBlock) pState.getBlock();
        if (TESTING) getEnergyStorage().receiveEnergy(10000, false); // TODO: not for production
        if (hasRecipe()) {
            isCrafting = true;
            updateBlockState(pState.setValue(BlockStateProperties.POWERED, Boolean.TRUE));
            increaseCraftingProgress();
            getEnergyStorage().extractEnergy(getEnergyAmount(), false);
            setChanged();

            if (hasFinished()) {
                craftItem();
                progress = 0;
                isCrafting = false;
            }
        } else {
            updateBlockState(pState.setValue(BlockStateProperties.POWERED, Boolean.FALSE));
            isCrafting = false;
            progress = 0;
        }
        setChanged();
    }

    @Override
    public void craftItem () {
        var recipe = getCurrentRecipe();
        ItemStack result = recipe.get().value().getResultItem(Objects.requireNonNull(getLevel()).registryAccess());

        this.getInventory().extractItem(0, 1, false);
        this.getInventory().setStackInSlot(1, new ItemStack(result.getItem(), this.getInventory().getStackInSlot(1).getCount() + result.getCount()));
    }

    @Override
    public boolean hasRecipe () {
        var recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;
        var result = recipe.get().value().getResultItem(Objects.requireNonNull(getLevel()).registryAccess());

        return canInsertAmount(result.getCount()) && canInsertItem(result.getItem()) && hasEnoughEnergy(getEnergyAmount());
    }

    @Override
    public RecipeType<SmeltingRecipe> getRecipeType () {
        return RecipeType.SMELTING;
    }

    @Override
    public Optional<RecipeHolder<SmeltingRecipe>> getCurrentRecipe () {
        SimpleContainer inventory = new SimpleContainer(this.getInventory().getSlots());
        for (int i = 0; i < getInventory().getSlots(); i++) {
            inventory.setItem(i, this.getInventory().getStackInSlot(i));
        }
        assert this.level != null;
        return this.level.getRecipeManager().getRecipeFor(getRecipeType(), new SingleRecipeInput(getInventory().getStackInSlot(0)), level);
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
    public int getMaxProgress () {
        return 176;
    }

    @Override
    public boolean hasFinished () {
        return getProgress() >= getMaxProgress();
    }

    @Override
    public boolean hasEnoughEnergy (int required) {
        var hasEnergy = this.getEnergyStorage().getEnergyStored() >= required;
        if (hasEnergy) this.getEnergyStorage().extractEnergy(required, false);
        return hasEnergy;
    }

    @Override
    public boolean canInsertItem (Item item) {
        return this.getInventory().getStackInSlot(1).isEmpty() || this.getInventory().getStackInSlot(1).is(item);
    }

    @Override
    public boolean canInsertAmount (int count) {
        return this.getInventory().getStackInSlot(1).getCount() + count <= this.getInventory().getStackInSlot(1).getMaxStackSize();
    }

    @Override
    public int getEnergyAmount () {
        return 8;
    }

    @Override
    public boolean isCrafting () {
        return isCrafting;
    }

    public int getScaledProgress () {
        int progress = (int) getProgress();
        int max = getMaxProgress();
        int arrowSize = 26;
        return max != 0 && progress != 0 ? progress * arrowSize / max : 0;
    }

}
