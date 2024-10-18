package net.superscary.fluxmachines.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.registries.FMBlocks;
import net.superscary.fluxmachines.registries.FMItems;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.registries.FMBlocks.DURACITE_BLOCK;
import static net.superscary.fluxmachines.registries.FMBlocks.DURACITE_BLOCK_RAW;
import static net.superscary.fluxmachines.registries.FMItems.*;

public class SmeltingRecipes extends FMRecipeProvider {

    private static final int DEFAULT_SMELTING_TIME = 200;

    public SmeltingRecipes (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public String getName () {
        return "FluxMachines Smelting Recipes";
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput consumer) {
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(RAW_DURACITE), RecipeCategory.MISC, FMItems.DURACITE_INGOT, .35f, DEFAULT_SMELTING_TIME)
                .unlockedBy("has_duracite_raw", has(RAW_DURACITE))
                .save(consumer, FluxMachines.getResource("smelting/duracite_from_raw_duracite"));

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(DURACITE_DUST), RecipeCategory.MISC, new ItemStack(FMItems.DURACITE_INGOT, 2), .35f, DEFAULT_SMELTING_TIME)
                .unlockedBy("has_duracite_dust", has(DURACITE_DUST))
                .save(consumer, FluxMachines.getResource("smelting/duracite_from_duracite_dust"));

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(DURACITE_BLOCK_RAW), RecipeCategory.MISC, DURACITE_BLOCK, 3.15f, DEFAULT_SMELTING_TIME * 9)
                .unlockedBy("has_duracite_block", has(DURACITE_BLOCK_RAW))
                .save(consumer, FluxMachines.getResource("smelting/duracite_block_from_duracite_block_raw"));

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(Items.EGG), RecipeCategory.FOOD, HARD_BOILED_EGG, 0f, 100)
                .unlockedBy("has_eg", has(Items.EGG))
                .save(consumer, FluxMachines.getResource("cooking/hard_boiled_egg"));

    }

}
