package net.superscary.fluxmachines.core.recipe.manager;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.superscary.fluxmachines.api.manager.IRecipeManager;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.FluxSmeltingRecipe;
import net.superscary.fluxmachines.core.registries.FMRecipes;

import java.util.ArrayList;
import java.util.List;

public class FluxSmeltingManager implements IRecipeManager<FluxSmeltingRecipe> {

    private static final FluxSmeltingManager INSTANCE = new FluxSmeltingManager();

    protected List<RecipeHolder<FluxSmeltingRecipe>> convertedRecipes = new ArrayList<>();

    private FluxSmeltingManager() {

    }

    public static FluxSmeltingManager instance () {
        return INSTANCE;
    }

    public void createConvertedRecipes (RecipeManager recipeManager) {
        FluxMachines.LOGGER.info("Converting built-in smelting recipes to Flux Smelting recipes...");
        for (var recipe : recipeManager.getAllRecipesFor(RecipeType.SMELTING)) {
            createConvertedRecipe(recipe.value());
        }

        // Adds standardized recipes for the Flux Smelter
        convertedRecipes.addAll(recipeManager.getAllRecipesFor(FMRecipes.FLUX_SMELTING_TYPE.get()));
    }

    protected boolean createConvertedRecipe (AbstractCookingRecipe recipe) {
        if (recipe.isSpecial() || recipe.getResultItem(null).isEmpty()) return false;
        convertedRecipes.add(convert(recipe));
        return true;
    }

    protected RecipeHolder<FluxSmeltingRecipe> convert (AbstractCookingRecipe recipe) {
        ItemStack recipeOutput = recipe.getResultItem(null);
        float experience = recipe.getExperience();
        int cookingTime = recipe.getCookingTime();
        int energy = 200; //TODO: Get energy from recipe
        return new RecipeHolder<>(FluxMachines.getResource("fluxsmelting/fluxfurnace_" + recipe.getIngredients().get(0).hashCode()), new FluxSmeltingRecipe(recipe.getIngredients().getFirst(), energy, cookingTime, recipeOutput));
    }

    @Override
    public List<RecipeHolder<FluxSmeltingRecipe>> getConvertedRecipes () {
        return convertedRecipes;
    }

    @Override
    public RecipeType<FluxSmeltingRecipe> getRecipeType() {
        return FMRecipes.FLUX_SMELTING_TYPE.get();
    }

    @Override
    public void refresh(RecipeManager recipeManager) {
        clear();
        createConvertedRecipes(recipeManager);
    }

    @Override
    public void clear() {
        convertedRecipes.clear();
    }
}
