package net.superscary.fluxmachines.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.superscary.fluxmachines.core.FluxMachines;

public class FMBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_STEEL_GEODE = registerKey("add_steel_geode");

    public static final ResourceKey<BiomeModifier> DURACITE_ORE_SMALL = registerKey("duracite_ore_small");
    public static final ResourceKey<BiomeModifier> DURACITE_ORE_LARGE = registerKey("duracite_ore_large");
    public static final ResourceKey<BiomeModifier> URANIUM_ORE_SMALL = registerKey("uranium_ore_small");
    public static final ResourceKey<BiomeModifier> URANIUM_ORE_LARGE = registerKey("uranium_ore_large");

    public static void bootstrap (BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(DURACITE_ORE_SMALL, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.DURACITE_ORE_PLACED_SMALL_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(DURACITE_ORE_LARGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.DURACITE_ORE_PLACED_LARGE_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(URANIUM_ORE_SMALL, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.URANIUM_ORE_PLACED_SMALL_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(URANIUM_ORE_LARGE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.URANIUM_ORE_PLACED_LARGE_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_STEEL_GEODE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.STEEL_GEODE_PLACED_KEY)), GenerationStep.Decoration.LOCAL_MODIFICATIONS));
    }

    private static ResourceKey<BiomeModifier> registerKey (String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FluxMachines.getResource(name));
    }

}
