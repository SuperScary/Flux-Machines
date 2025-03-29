package net.superscary.fluxmachines.api.recipe;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * This is literally because i am lazy and wanted input() instead of having to get the first
 * object in the ingredients.
 * @param <T>
 */
public interface FMRecipe<T extends RecipeInput> extends Recipe<T> {

    default Ingredient input () {
        return getIngredients().getFirst();
    }

}
