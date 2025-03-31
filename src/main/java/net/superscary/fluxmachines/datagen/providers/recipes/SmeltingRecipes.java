package net.superscary.fluxmachines.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMItems;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.core.registries.FMItems.HARD_BOILED_EGG;
import static net.superscary.fluxmachines.core.registries.FMItems.STEEL_DUST;

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

        // TODO: Remove this recipe and only allow from crusher
        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(Items.IRON_INGOT), RecipeCategory.MISC, new ItemStack(STEEL_DUST), 0f, DEFAULT_SMELTING_TIME)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(consumer, FluxMachines.getResource("smelting/steel_dust_from_iron"));

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(Items.EGG), RecipeCategory.FOOD, HARD_BOILED_EGG, 0f, 100)
                .unlockedBy("has_egg", has(Items.EGG))
                .save(consumer, FluxMachines.getResource("cooking/hard_boiled_egg"));

    }

}
