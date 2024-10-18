package net.superscary.fluxmachines.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.superscary.fluxmachines.core.FluxMachines;

import java.util.List;

public class FMPlacedFeatures {

    public static final ResourceKey<PlacedFeature> DURACITE_ORE_PLACED_KEY = registerKey("duracite_ore_placed");
    public static final ResourceKey<PlacedFeature> DURACITE_DEEPSLATE_ORE_PLACED_KEY = registerKey("duracite_deepslate_ore_placed");
    public static final ResourceKey<PlacedFeature> DURACITE_NETHER_ORE_PLACED_KEY = registerKey("duracite_nether_ore_placed");

    public static final ResourceKey<PlacedFeature> DURACITE_GEODE_PLACED_KEY = registerKey("duracite_geode_placed_key");

    public static void bootstrap (BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, DURACITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(FMConfiguredFeatures.OVERWORLD_DURACITE_KEY), OrePlacements.commonOrePlacement(90, HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, DURACITE_DEEPSLATE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(FMConfiguredFeatures.OVERWORLD_DURACITE_DEEPSLATE_KEY), OrePlacements.commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        register(context, DURACITE_NETHER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(FMConfiguredFeatures.NETHER_DURACITE_KEY), OrePlacements.commonOrePlacement(12, PlacementUtils.RANGE_10_10));
        register(context, DURACITE_GEODE_PLACED_KEY, configuredFeatures.getOrThrow(FMConfiguredFeatures.DURACITE_GEODE_KEY), List.of(RarityFilter.onAverageOnceEvery(50), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(60)), BiomeFilter.biome()));
    }

    public static ResourceKey<PlacedFeature> registerKey (String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, FluxMachines.getResource(name));
    }

    private static void register (BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

}
