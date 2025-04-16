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

public class FluxSmeltingRecipeManager implements IRecipeManager<FluxSmeltingRecipe> {

    private final FluxSmeltingRecipeManager INSTANCE;
    private RecipeManager recipeManager;

    protected List<RecipeHolder<FluxSmeltingRecipe>> convertedRecipes = new ArrayList<>();

    public FluxSmeltingRecipeManager () {
        INSTANCE = this;
    }

    public void createConvertedRecipes (RecipeManager recipeManager) {
        FluxMachines.LOGGER.info("Converting built-in smelting recipes to Flux Smelting recipes...");
        for (var recipe : recipeManager.getAllRecipesFor(RecipeType.SMELTING)) {
            createConvertedRecipe(recipe.value());
        }

        // Adds standardized recipes for the Flux Smelter
        convertedRecipes.addAll(recipeManager.getAllRecipesFor(FMRecipes.FLUX_SMELTING_TYPE.get()));
    }

    protected void createConvertedRecipe (AbstractCookingRecipe recipe) {
        if (recipe.isSpecial() || recipe.getResultItem(null).isEmpty()) return;
        convertedRecipes.add(convert(recipe));
    }

    protected RecipeHolder<FluxSmeltingRecipe> convert (AbstractCookingRecipe recipe) {
        ItemStack recipeOutput = recipe.getResultItem(null);
        float experience = recipe.getExperience();
        int cookingTime = recipe.getCookingTime() / 2;
        int energy = 200; //TODO: Get energy from recipe
        return new RecipeHolder<>(FluxMachines.getResource("fluxsmelting/fluxfurnace_" + recipe.getIngredients().getFirst().hashCode()), new FluxSmeltingRecipe(recipe.getIngredients().getFirst(), energy, cookingTime, recipeOutput));
    }

    @Override
    public List<RecipeHolder<FluxSmeltingRecipe>> getConvertedRecipes () {
        if (recipeManager != null) refresh(recipeManager);
        if (convertedRecipes.isEmpty()) FluxMachines.LOGGER.warn("Flux Smelting Recipes list is empty!");
        return convertedRecipes;
    }

    @Override
    public RecipeType<FluxSmeltingRecipe> getRecipeType() {
        return FMRecipes.FLUX_SMELTING_TYPE.get();
    }

    @Override
    public List<FluxSmeltingRecipe> asRecipeList() {
        return getConvertedRecipes().stream().map(RecipeHolder::value).toList();
    }

    @Override
    public IRecipeManager<FluxSmeltingRecipe> getInstance () {
        return INSTANCE;
    }

    @Override
    public void refresh(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;

        clear();
        createConvertedRecipes(getRecipeManager());
    }

    @Override
    public void clear() {
        convertedRecipes.clear();
    }

    @Override
    public RecipeManager getRecipeManager () {
        return recipeManager;
    }

}
