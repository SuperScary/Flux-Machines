package net.superscary.fluxmachines.core.recipe.manager;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.superscary.fluxmachines.api.manager.IRecipeManager;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.CompressorRecipe;
import net.superscary.fluxmachines.core.registries.FMRecipes;

import java.util.ArrayList;
import java.util.List;

/**
 * Since the Compressor is a fully custom recipe, we do not need to convert any built in recipes.
 */
public class CompressorRecipeManager implements IRecipeManager<CompressorRecipe> {

    private final CompressorRecipeManager INSTANCE;
    private RecipeManager recipeManager;

    protected List<RecipeHolder<CompressorRecipe>> convertedRecipes = new ArrayList<>();

    public CompressorRecipeManager() {
        INSTANCE = this;
    }

    public void createConvertedRecipes (RecipeManager recipeManager) {
        FluxMachines.LOGGER.info("Converting built-in smelting recipes to Compressor recipes...");

        // Adds standardized recipes for the Compressor
        convertedRecipes.addAll(recipeManager.getAllRecipesFor(FMRecipes.COMPRESSOR_TYPE.get()));
    }

    @Override
    public List<RecipeHolder<CompressorRecipe>> getConvertedRecipes () {
        return convertedRecipes;
    }

    @Override
    public RecipeType<CompressorRecipe> getRecipeType() {
        return FMRecipes.COMPRESSOR_TYPE.get();
    }

    @Override
    public List<CompressorRecipe> asRecipeList() {
        return convertedRecipes.stream().map(RecipeHolder::value).toList();
    }

    @Override
    public IRecipeManager<CompressorRecipe> getInstance () {
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
