package net.superscary.fluxmachines.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.registries.FMItems;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.registries.FMItems.STEEL_DUST;

public class BlastingRecipes extends FMRecipeProvider {

    private static final int DEFAULT_BLASTING_TIME = 150;

    public BlastingRecipes (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName () {
        return "Flux Machines Blasting Recipes";
    }

    @Override
    public void buildRecipes (@NotNull RecipeOutput consumer) {
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(STEEL_DUST), RecipeCategory.MISC, new ItemStack(FMItems.STEEL_INGOT, 2), .35f, DEFAULT_BLASTING_TIME)
                .unlockedBy("has_steel_dust", has(STEEL_DUST))
                .save(consumer, FluxMachines.getResource("blasting/steel_from_steel_dust"));
    }

}
