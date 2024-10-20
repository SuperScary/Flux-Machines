package net.superscary.fluxmachines.api.blockentity;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Optional;

public interface Crafter<T extends Recipe<?>> {

    void craftItem ();

    boolean hasRecipe ();

    RecipeType<T> getRecipeType ();

    Optional<RecipeHolder<T>> getCurrentRecipe ();

    void increaseCraftingProgress ();

    float getProgress ();

    int getMaxProgress ();

    boolean hasFinished ();

}
