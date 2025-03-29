package net.superscary.fluxmachines.core.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.superscary.fluxmachines.api.recipe.FMRecipe;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.input.SingleItemInput;
import net.superscary.fluxmachines.core.registries.FMRecipes;
import net.superscary.fluxmachines.core.util.Codecs;
import org.jetbrains.annotations.NotNull;

public record CompressorRecipe(Ingredient input, int energyReq, int craftTime, ItemStack output) implements FMRecipe<SingleItemInput> {

    public static final ResourceLocation TYPE_ID;
    public static final RecipeType<CompressorRecipe> TYPE;
    public static final MapCodec<CompressorRecipe> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, CompressorRecipe> STREAM_CODEC;

    @Override
    public boolean matches(@NotNull SingleItemInput container, Level level) {
        if (level.isClientSide()) return false;
        return input.test(container.getItem(0));
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input);
        return list;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleItemInput container, HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return FMRecipes.COMPRESSOR_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return FMRecipes.COMPRESSOR_TYPE.get();
    }

    static {
        TYPE_ID = FluxMachines.getResource("fluxsmelting");
        TYPE = FMRecipes.COMPRESSOR_TYPE.get();
        CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder
                        .group(Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(er -> er.input),
                                ExtraCodecs.POSITIVE_INT.fieldOf("energy").forGetter(er -> er.energyReq),
                                ExtraCodecs.POSITIVE_INT.fieldOf("craftTime").forGetter(er -> er.craftTime),
                                net.minecraft.world.item.ItemStack.CODEC.fieldOf("result").forGetter(er -> er.output))
                        .apply(builder, CompressorRecipe::new));

        STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, CompressorRecipe::input,
                Codecs.INTEGER_STREAM_CODEC, CompressorRecipe::energyReq,
                Codecs.INTEGER_STREAM_CODEC, CompressorRecipe::craftTime,
                net.minecraft.world.item.ItemStack.STREAM_CODEC, CompressorRecipe::output,
                CompressorRecipe::new);
    }
}