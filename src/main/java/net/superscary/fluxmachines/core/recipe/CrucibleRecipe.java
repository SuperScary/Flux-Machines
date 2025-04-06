package net.superscary.fluxmachines.core.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.superscary.fluxmachines.api.recipe.FMRecipe;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.input.CrucibleInput;
import net.superscary.fluxmachines.core.registries.FMRecipes;
import net.superscary.fluxmachines.core.util.Codecs;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public record CrucibleRecipe(Ingredient inputItems, FluidStack inputFluid, ItemStack output, int craftTime) implements FMRecipe<CrucibleInput> {

	public static final ResourceLocation TYPE_ID;
	public static final RecipeType<CrucibleRecipe> TYPE;
	public static final MapCodec<CrucibleRecipe> CODEC;
	public static final StreamCodec<RegistryFriendlyByteBuf, CrucibleRecipe> STREAM_CODEC;

	@Override
	public boolean matches (@NotNull CrucibleInput crucibleInput, Level level) {
		if (level.isClientSide()) return false;
		return Arrays.equals(inputItems.getItems(), crucibleInput.input());
	}

	@Override
	public @NotNull ItemStack assemble (@NotNull CrucibleInput crucibleInput, HolderLookup.@NotNull Provider provider) {
		return output.copy();
	}

	@Override
	public boolean canCraftInDimensions (int i, int i1) {
		return true;
	}

	@Override
	public @NotNull ItemStack getResultItem (HolderLookup.@NotNull Provider provider) {
		return output.copy();
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer () {
		return FMRecipes.CRUCIBLE_SERIALIZER.get();
	}

	@Override
	public @NotNull RecipeType<?> getType () {
		return FMRecipes.CRUCIBLE_TYPE.get();
	}

	static {
		TYPE_ID = FluxMachines.getResource("crucible");
		TYPE = FMRecipes.CRUCIBLE_TYPE.get();
		CODEC = RecordCodecBuilder.mapCodec(
				builder -> builder
						.group(Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(er -> er.inputItems),
								FluidStack.CODEC.fieldOf("inputFluid").forGetter(er -> er.inputFluid),
								ItemStack.CODEC.fieldOf("result").forGetter(er -> er.output),
								ExtraCodecs.POSITIVE_INT.fieldOf("craftTime").forGetter(er -> er.craftTime))
						.apply(builder, CrucibleRecipe::new));

		STREAM_CODEC = StreamCodec.composite(
				Ingredient.CONTENTS_STREAM_CODEC, CrucibleRecipe::inputItems,
				FluidStack.STREAM_CODEC, CrucibleRecipe::inputFluid,
				ItemStack.STREAM_CODEC, CrucibleRecipe::output,
				Codecs.INTEGER_STREAM_CODEC, CrucibleRecipe::craftTime,
				CrucibleRecipe::new);
	}
}
