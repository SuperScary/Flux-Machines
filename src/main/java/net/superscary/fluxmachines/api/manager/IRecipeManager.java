package net.superscary.fluxmachines.api.manager;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.superscary.fluxmachines.core.recipe.FluxSmeltingRecipe;

import java.util.List;

public interface IRecipeManager<T extends Recipe<?>> {

    void refresh (RecipeManager recipeManager);

    void clear ();

    List<RecipeHolder<T>> getConvertedRecipes ();

    RecipeType<T> getRecipeType ();

}
