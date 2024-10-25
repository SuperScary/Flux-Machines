package net.superscary.fluxmachines.datagen.providers.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.core.registries.FMItems.HARD_BOILED_EGG;

public class CompostableProvider extends DataMapProvider {

    public CompostableProvider (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather () {
        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(HARD_BOILED_EGG.id(), new Compostable(0.35f, true), false);
    }

}
