package net.superscary.fluxmachines.datagen.providers.worldgen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.worldgen.FMBiomeModifiers;
import net.superscary.fluxmachines.worldgen.FMConfiguredFeatures;
import net.superscary.fluxmachines.worldgen.FMPlacedFeatures;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class WorldGenProvider extends DatapackBuiltinEntriesProvider implements IDataProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, FMConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FMPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FMBiomeModifiers::bootstrap);

    public WorldGenProvider (PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(FluxMachines.MODID));
    }

    @Override
    public @NotNull String getName () {
        return "FluxMachines World Gen";
    }
}
