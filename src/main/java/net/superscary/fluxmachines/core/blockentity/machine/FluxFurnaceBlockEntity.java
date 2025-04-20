package net.superscary.fluxmachines.core.blockentity.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.fluxmachines.api.inventory.MachineItemStackHandler;
import net.superscary.fluxmachines.api.manager.IRecipeManager;
import net.superscary.fluxmachines.attributes.Attribute;
import net.superscary.fluxmachines.core.blockentity.base.BaseEnergyCrafter;
import net.superscary.fluxmachines.core.recipe.FluxSmeltingRecipe;
import net.superscary.fluxmachines.core.registries.FMRecipeManagers;
import net.superscary.fluxmachines.core.util.keys.Keys;
import net.superscary.fluxmachines.gui.menu.FluxFurnaceMenu;
import org.jetbrains.annotations.NotNull;

public class FluxFurnaceBlockEntity extends BaseEnergyCrafter<FluxSmeltingRecipe> {

    public FluxFurnaceBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState, Attribute.Builder.of(Keys.MAX_POWER, 100_000), Attribute.Builder.of(Keys.MAX_DRAIN, 256));
    }

    public FluxFurnaceBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, int current) {
        super(type, pos, blockState, Attribute.Builder.of(Keys.MAX_POWER, 100_000), Attribute.Builder.of(Keys.MAX_DRAIN, 256), Attribute.Builder.of(Keys.POWER, current));
    }

    @Override
    public IRecipeManager<FluxSmeltingRecipe> getRecipeManager() {
        return FMRecipeManagers.FLUX_SMELTING_RECIPE_MANAGER.get();
    }

    @Override
    public MachineItemStackHandler createInventory () {
        return INVENTORY_1X1;
    }

    @Override
    public AbstractContainerMenu menu (int id, Inventory playerInventory, Player player) {
        return new FluxFurnaceMenu(id, playerInventory, this);
    }

    @Override
    public void tick (Level level, BlockPos pos, BlockState state) {
        super.tick(level, pos, state);
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
    public int inputSlot () {
        return 0;
    }

    @Override
    public int outputSlot () {
        return 1;
    }
}
