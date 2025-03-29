package net.superscary.fluxmachines.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.builder.CompressorRecipeBuilder;
import net.superscary.fluxmachines.core.registries.FMItems;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class CompressorRecipes extends FMRecipeProvider {

    public CompressorRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName() {
        return "Flux Machines Compressor Recipes";
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput consumer) {
        CompressorRecipeBuilder.compress(consumer, FluxMachines.getResource("compressor/rubber_from_leaves"), ItemTags.LEAVES, FMItems.RUBBER, 8, 200);
    }

}