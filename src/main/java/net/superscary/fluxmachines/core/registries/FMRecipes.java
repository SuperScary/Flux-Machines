package net.superscary.fluxmachines.core.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.CompressorRecipe;
import net.superscary.fluxmachines.core.recipe.FluxSmeltingRecipe;
import net.superscary.fluxmachines.core.recipe.serializer.CompressorSerializer;
import net.superscary.fluxmachines.core.recipe.serializer.FluxSmeltingSerializer;

public class FMRecipes {

    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, FluxMachines.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, FluxMachines.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FluxSmeltingRecipe>> FLUX_SMELTING_SERIALIZER = SERIALIZERS.register("fluxsmelting", FluxSmeltingSerializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FluxSmeltingRecipe>> FLUX_SMELTING_TYPE = TYPES.register("fluxsmelting", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "fluxsmelting";
        }
    });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CompressorRecipe>> COMPRESSOR_SERIALIZER = SERIALIZERS.register("compressor", CompressorSerializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<CompressorRecipe>> COMPRESSOR_TYPE = TYPES.register("compressor", () -> new RecipeType<>() {
        @Override
        public String toString() {
            return "compressor";
        }
    });

}
