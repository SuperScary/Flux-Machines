package net.superscary.fluxmachines.core.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.superscary.fluxmachines.api.FMRecipe;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.input.FluxSmeltingInput;
import net.superscary.fluxmachines.core.registries.FMRecipes;
import net.superscary.fluxmachines.core.util.Codecs;
import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public record FluxSmeltingRecipe(Ingredient input, int energyReq, int craftTime, ItemStack output) implements FMRecipe<FluxSmeltingInput> {

    public static final ResourceLocation TYPE_ID;
    public static final RecipeType<FluxSmeltingRecipe> TYPE;
    public static final MapCodec<FluxSmeltingRecipe> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, FluxSmeltingRecipe> STREAM_CODEC;

    @Override
    public boolean matches (@NotNull FluxSmeltingInput container, Level level) {
        if (level.isClientSide()) return false;
        return input.test(container.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients () {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(input);
        return list;
    }

    @Override
    public ItemStack assemble (@NotNull FluxSmeltingInput container, HolderLookup.@NotNull Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions (int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem (HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer () {
        return FMRecipes.FLUX_SMELTING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType () {
        return FMRecipes.FLUX_SMELTING_TYPE.get();
    }

    static {
        TYPE_ID = FluxMachines.getResource("fluxsmelting");
        TYPE = FMRecipes.FLUX_SMELTING_TYPE.get();
        CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder
                        .group(Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(er -> er.input),
                                ExtraCodecs.POSITIVE_INT.fieldOf("energy").forGetter(er -> er.energyReq),
                                ExtraCodecs.POSITIVE_INT.fieldOf("craftTime").forGetter(er -> er.craftTime),
                                ItemStack.CODEC.fieldOf("result").forGetter(er -> er.output))
                        .apply(builder, FluxSmeltingRecipe::new));

        STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, FluxSmeltingRecipe::input,
                Codecs.INTEGER_STREAM_CODEC, FluxSmeltingRecipe::energyReq,
                Codecs.INTEGER_STREAM_CODEC, FluxSmeltingRecipe::craftTime,
                ItemStack.STREAM_CODEC, FluxSmeltingRecipe::output,
                FluxSmeltingRecipe::new);
    }

}
