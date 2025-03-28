package net.superscary.fluxmachines.api.manager;

import net.minecraft.world.item.crafting.RecipeManager;

public interface IRecipeManager {

    void refresh (RecipeManager recipeManager);

    void clear ();

}
