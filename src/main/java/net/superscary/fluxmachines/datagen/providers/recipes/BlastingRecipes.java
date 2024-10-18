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

import static net.superscary.fluxmachines.registries.FMBlocks.DURACITE_BLOCK;
import static net.superscary.fluxmachines.registries.FMBlocks.DURACITE_BLOCK_RAW;
import static net.superscary.fluxmachines.registries.FMItems.DURACITE_DUST;
import static net.superscary.fluxmachines.registries.FMItems.RAW_DURACITE;

public class BlastingRecipes extends FMRecipeProvider {

    private static final int DEFAULT_BLASTING_TIME = 150;

    public BlastingRecipes (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public String getName () {
        return "FluxMachines Blasting Recipes";
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput consumer) {
        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(RAW_DURACITE), RecipeCategory.MISC, FMItems.DURACITE_INGOT, .35f, DEFAULT_BLASTING_TIME)
                .unlockedBy("has_duracite_raw", has(RAW_DURACITE))
                .save(consumer, FluxMachines.getResource("blasting/duracite_from_raw_duracite"));

        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(DURACITE_DUST), RecipeCategory.MISC, new ItemStack(FMItems.DURACITE_INGOT, 2), .35f, DEFAULT_BLASTING_TIME)
                .unlockedBy("has_duracite_dust", has(DURACITE_DUST))
                .save(consumer, FluxMachines.getResource("blasting/duracite_from_duracite_dust"));

        SimpleCookingRecipeBuilder
                .blasting(Ingredient.of(DURACITE_BLOCK_RAW), RecipeCategory.MISC, DURACITE_BLOCK, 3.15f, DEFAULT_BLASTING_TIME * 9)
                .unlockedBy("has_duracite_block", has(DURACITE_BLOCK_RAW))
                .save(consumer, FluxMachines.getResource("blasting/duracite_block_from_duracite_block_raw"));
    }

}
