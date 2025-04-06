package net.superscary.fluxmachines.core.recipe.manager;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.superscary.fluxmachines.api.manager.IRecipeManager;
import net.superscary.fluxmachines.core.recipe.CrucibleRecipe;
import net.superscary.fluxmachines.core.registries.FMRecipes;

import java.util.ArrayList;
import java.util.List;

public class CrucibleRecipeManager implements IRecipeManager<CrucibleRecipe> {

	private static final CrucibleRecipeManager INSTANCE = new CrucibleRecipeManager();

	protected List<RecipeHolder<CrucibleRecipe>> convertedRecipes = new ArrayList<>();

	public static CrucibleRecipeManager instance () {
		return INSTANCE;
	}

	public void createConvertedRecipes (RecipeManager recipeManager) {
		convertedRecipes.addAll(recipeManager.getAllRecipesFor(FMRecipes.CRUCIBLE_TYPE.get()));
	}

	@Override
	public void refresh (RecipeManager recipeManager) {
		clear();
		createConvertedRecipes(recipeManager);
	}

	@Override
	public void clear () {
		convertedRecipes.clear();
	}

	@Override
	public List<RecipeHolder<CrucibleRecipe>> getConvertedRecipes () {
		return convertedRecipes;
	}

	@Override
	public RecipeType<CrucibleRecipe> getRecipeType () {
		return FMRecipes.CRUCIBLE_TYPE.get();
	}

	@Override
	public List<CrucibleRecipe> asRecipeList () {
		return getConvertedRecipes().stream().map(RecipeHolder::value).toList();
	}
}
