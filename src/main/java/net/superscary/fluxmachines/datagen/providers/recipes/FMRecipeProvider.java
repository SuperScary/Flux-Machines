package net.superscary.fluxmachines.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.superscary.fluxmachines.api.data.IDataProvider;

import java.util.concurrent.CompletableFuture;

public abstract class FMRecipeProvider extends RecipeProvider implements IDataProvider {

    public FMRecipeProvider (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

}
