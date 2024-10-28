package net.superscary.fluxmachines.api.blockentity;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.superscary.fluxmachines.api.inventory.InventoryHolder;

import java.util.Optional;

/**
 * An interface for block entities that have the ability to craft items.
 * @param <T>
 */
public interface Crafter<T extends Recipe<?>> extends InventoryHolder {

    /**
     * Crafts the item.
     */
    void craftItem ();

    /**
     * Checks if the block entity has a recipe.
     * @return true if the block entity has a recipe, false otherwise
     */
    boolean hasRecipe ();

    /**
     * Gets the recipe type.
     * @return the recipe type
     */
    RecipeType<T> getRecipeType ();

    /**
     * Gets the current recipe.
     * @return the current recipe
     */
    Optional<RecipeHolder<T>> getCurrentRecipe ();

    /**
     * Increases the crafting progress.
     */
    void increaseCraftingProgress ();

    /**
     * Gets the crafting progress.
     * @return the crafting progress
     */
    float getProgress ();

    /**
     * Gets the maximum progress.
     * @return the maximum progress
     */
    int getMaxProgress ();

    /**
     * Checks if the crafting has finished.
     * @return true if the crafting has finished, false otherwise
     */
    boolean hasFinished ();

    /**
     * Checks if the block entity is crafting.
     * @return true if the block entity is crafting, false otherwise
     */
    boolean isCrafting();

    /**
     * Gets the scaled progress.
     * @return the scaled progress
     */
    int getScaledProgress ();

}
