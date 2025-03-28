package net.superscary.fluxmachines.core.recipe.serializer;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.superscary.fluxmachines.core.recipe.FluxSmeltingRecipe;
import org.jetbrains.annotations.NotNull;

public class FluxSmeltingSerializer implements RecipeSerializer<FluxSmeltingRecipe> {

    public static final FluxSmeltingSerializer INSTANCE = new FluxSmeltingSerializer();

    @Override
    public @NotNull MapCodec<FluxSmeltingRecipe> codec () {
        return FluxSmeltingRecipe.CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, FluxSmeltingRecipe> streamCodec () {
        return FluxSmeltingRecipe.STREAM_CODEC;
    }
}
