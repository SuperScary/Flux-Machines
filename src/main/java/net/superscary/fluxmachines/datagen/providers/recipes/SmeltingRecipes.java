package net.superscary.fluxmachines.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.registries.FMItems;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.core.registries.FMItems.*;

public class SmeltingRecipes extends FMRecipeProvider {

	private static final int DEFAULT_SMELTING_TIME = 200;

	public SmeltingRecipes (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
		super(packOutput, provider);
	}

	@Override
	public @NotNull String getName () {
		return "Flux Machines Smelting Recipes";
	}

	@Override
	public void buildRecipes (@NotNull RecipeOutput consumer) {

		SimpleCookingRecipeBuilder
				.smelting(Ingredient.of(FMItems.RAW_DURACITE), RecipeCategory.TOOLS, DURACITE_INGOT, 0, DEFAULT_SMELTING_TIME)
				.unlockedBy("has_raw_duracite", has(RAW_DURACITE))
				.save(consumer, FluxMachines.getResource("smelting/duracite_ingot_from_raw_duracite"));

		SimpleCookingRecipeBuilder
				.smelting(Ingredient.of(FMBlocks.DURACITE_BLOCK_RAW), RecipeCategory.TOOLS, FMBlocks.DURACITE_BLOCK, 0, DEFAULT_SMELTING_TIME * 9)
				.unlockedBy("has_raw_duracite_block", has(FMBlocks.DURACITE_BLOCK_RAW))
				.save(consumer, FluxMachines.getResource("smelting/duracite_block_from_raw_duracite_block"));

		SimpleCookingRecipeBuilder
				.smelting(Ingredient.of(Items.EGG), RecipeCategory.FOOD, HARD_BOILED_EGG, 0f, 100)
				.unlockedBy("has_egg", has(Items.EGG))
				.save(consumer, FluxMachines.getResource("cooking/hard_boiled_egg_from_egg"));

	}

}
