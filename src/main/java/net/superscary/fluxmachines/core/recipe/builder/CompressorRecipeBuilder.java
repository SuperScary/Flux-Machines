package net.superscary.fluxmachines.core.recipe.builder;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.superscary.fluxmachines.core.recipe.CompressorRecipe;

public class CompressorRecipeBuilder {

    public static void compress(RecipeOutput consumer, ResourceLocation id, ItemLike input, ItemLike output, int energyReq, int time) {
        consumer.accept(id, new CompressorRecipe(Ingredient.of(input), energyReq, time, new ItemStack(output.asItem())), null);
    }

    public static void compress (RecipeOutput consumer, ResourceLocation id, TagKey<Item> input, ItemLike output, int energyReq, int time) {
        consumer.accept(id, new CompressorRecipe(Ingredient.of(input), energyReq, time, new ItemStack(output.asItem())), null);
    }

    public static void compress (RecipeOutput consumer, ResourceLocation id, Ingredient input, ItemLike output, int energyReq, int time) {
        consumer.accept(id, new CompressorRecipe(input, energyReq, time, new ItemStack(output.asItem())), null);
    }

    public static void compress (RecipeOutput consumer, ResourceLocation id, ItemLike input, ItemStack output, int energyReq, int time) {
        consumer.accept(id, new CompressorRecipe(Ingredient.of(input), energyReq, time, output), null);
    }

}
