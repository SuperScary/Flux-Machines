package net.superscary.fluxmachines.core.recipe.serializer;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.superscary.fluxmachines.core.recipe.CrucibleRecipe;
import org.jetbrains.annotations.NotNull;

public class CrucibleSerializer implements RecipeSerializer<CrucibleRecipe> {

	public static final CrucibleSerializer INSTANCE = new CrucibleSerializer();

	@Override
	public @NotNull MapCodec<CrucibleRecipe> codec () {
		return CrucibleRecipe.CODEC;
	}

	@Override
	public @NotNull StreamCodec<RegistryFriendlyByteBuf, CrucibleRecipe> streamCodec () {
		return CrucibleRecipe.STREAM_CODEC;
	}

}
