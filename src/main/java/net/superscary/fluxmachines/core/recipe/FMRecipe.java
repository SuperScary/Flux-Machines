package net.superscary.fluxmachines.core.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.util.helper.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class FMRecipe extends SerializableRecipe {

    private final List<Ingredient> inputItems = new ArrayList<>();
    private final List<FluidIngredient> inputFluids = new ArrayList<>();

    private final List<ItemStack> outputItems = new ArrayList<>();
    private final List<FluidStack> outputFluids = new ArrayList<>();
    private final List<Float> outputChance = new ArrayList<>();

    private int energy;
    private  float xp;

    public FMRecipe (int energy, float xp, List<Ingredient> inputItems, List<FluidIngredient> inputFluids, List<ItemStack> outputItems, List<Float> outputChance, List<FluidStack> outputFluids) {
        if ((inputItems == null || inputItems.isEmpty()) && (inputFluids == null || inputFluids.isEmpty()) || (outputItems == null || outputItems.isEmpty()) && (outputFluids == null || outputFluids.isEmpty())) {
            FluxMachines.LOGGER.warn("Invalid entry for Flux Machines Recipes.");
        }
        this.energy = energy;
        this.xp = Math.max(0.f, xp);

        this.inputItems.addAll(Utils.assignIfNotNull(inputItems));
        this.inputFluids.addAll(Utils.assignIfNotNull(inputFluids));
        this.outputItems.addAll(Utils.assignIfNotNull(outputItems));
        this.outputFluids.addAll(Utils.assignIfNotNull(outputFluids));
        trim();
    }

    private void trim () {
        ((ArrayList<Ingredient>) this.inputItems).trimToSize();
        ((ArrayList<FluidIngredient>) this.inputFluids).trimToSize();
        ((ArrayList<ItemStack>) this.outputItems).trimToSize();
        ((ArrayList<FluidStack>) this.outputFluids).trimToSize();
        ((ArrayList<Float>) this.outputChance).trimToSize();
    }

    public List<Ingredient> getInputItems() {
        return inputItems;
    }

    public List<FluidIngredient> getInputFluids() {
        return inputFluids;
    }

    public List<ItemStack> getOutputItems() {
        return outputItems;
    }

    public List<FluidStack> getOutputFluids() {
        return outputFluids;
    }

    public List<Float> getOutputChance() {
        return outputChance;
    }

    public int getEnergy() {
        return energy;
    }

    public float getXp() {
        return xp;
    }

}
