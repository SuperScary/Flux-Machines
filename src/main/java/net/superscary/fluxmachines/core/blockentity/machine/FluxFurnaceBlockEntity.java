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
import net.superscary.fluxmachines.api.manager.IRecipeManager;
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

public class FluxFurnaceBlockEntity extends BaseEnergyCrafter<FluxSmeltingRecipe> {

    public FluxFurnaceBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState, 100_000, 256);
    }

    public FluxFurnaceBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, int current) {
        super(type, pos, blockState, 100_000, 256, current);
    }

    @Override
    public IRecipeManager<FluxSmeltingRecipe> getRecipeManager() {
        return FluxSmeltingManager.instance();
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
    public int getEnergyAmount () {
        return 8;
    }

    @Override
    public FluidHandlerItemStack getFluidInventory() {
        return null;
    }

    @Override
    public int inputSlot () {
        return 0;
    }

    @Override
    public int outputSlot () {
        return 1;
    }
}
