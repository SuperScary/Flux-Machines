package net.superscary.fluxmachines.datagen.providers.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.core.registries.FMItems.*;

public class CompostableProvider extends DataMapProvider {

    public CompostableProvider (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather () {
        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(HARD_BOILED_EGG.id(), new Compostable(0.35f, true), false)
                .add(INDUSTRIAL_SLAG.id(), new Compostable(0.20f, true), false)
                .add(FERTILIZER.id(), new Compostable(0.45f, true), false);

        builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(COKE.id(), new FurnaceFuel(2400), false);
    }

}
