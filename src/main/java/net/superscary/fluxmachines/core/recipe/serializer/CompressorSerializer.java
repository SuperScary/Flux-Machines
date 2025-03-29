package net.superscary.fluxmachines.core.recipe.serializer;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.superscary.fluxmachines.core.recipe.CompressorRecipe;
import org.jetbrains.annotations.NotNull;

public class CompressorSerializer implements RecipeSerializer<CompressorRecipe> {

    public static final FluxSmeltingSerializer INSTANCE = new FluxSmeltingSerializer();

    @Override
    public @NotNull MapCodec<CompressorRecipe> codec () {
        return CompressorRecipe.CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, CompressorRecipe> streamCodec () {
        return CompressorRecipe.STREAM_CODEC;
    }
}