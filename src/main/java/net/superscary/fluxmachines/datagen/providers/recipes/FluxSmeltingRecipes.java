package net.superscary.fluxmachines.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.builder.FluxSmeltingBuilder;
import net.superscary.fluxmachines.core.registries.FMItems;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class FluxSmeltingRecipes extends FMRecipeProvider {

    public FluxSmeltingRecipes(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName() {
        return "Flux Machines Flux Smelting Recipes";
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput consumer) {
        FluxSmeltingBuilder.smelt(consumer, FluxMachines.getResource("fluxsmelting/steel_from_steel_dust"), FMItems.STEEL_DUST, FMItems.STEEL_INGOT.stack(2), 8, 200);
    }

}
