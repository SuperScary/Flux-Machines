package net.superscary.fluxmachines.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.superscary.fluxmachines.core.FluxMachines;

import java.util.List;

public class FMPlacedFeatures {

    public static final ResourceKey<PlacedFeature> STEEL_GEODE_PLACED_KEY = registerKey("steel_geode_placed_key");

    public static final ResourceKey<PlacedFeature> DURACITE_ORE_PLACED_SMALL_KEY = registerKey("duracite_ore_placed_small");
    public static final ResourceKey<PlacedFeature> DURACITE_ORE_PLACED_LARGE_KEY = registerKey("duracite_ore_placed_large");

    public static void bootstrap (BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, STEEL_GEODE_PLACED_KEY, configuredFeatures.getOrThrow(FMConfiguredFeatures.STEEL_GEODE_KEY), List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(60)), BiomeFilter.biome()));

        register(context, DURACITE_ORE_PLACED_SMALL_KEY, configuredFeatures.getOrThrow(FMConfiguredFeatures.OVERWORLD_DURACITE_ORE_KEY_SMALL),
                FMOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
        register(context, DURACITE_ORE_PLACED_LARGE_KEY, configuredFeatures.getOrThrow(FMConfiguredFeatures.OVERWORLD_DURACITE_ORE_KEY_LARGE),
                FMOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
    }

    public static ResourceKey<PlacedFeature> registerKey (String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, FluxMachines.getResource(name));
    }

    private static void register (BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
