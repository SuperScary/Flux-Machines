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

    public static final ResourceKey<BiomeModifier> ADD_DURACITE_ORE = registerKey("add_duracite_ore");
    public static final ResourceKey<BiomeModifier> ADD_DURACITE_DEEPSLATE_ORE = registerKey("add_duracite_deepslate_ore");
    public static final ResourceKey<BiomeModifier> ADD_DURACITE_NETHER_ORE = registerKey("add_duracite_nether_ore");

    public static final ResourceKey<BiomeModifier> ADD_DURACITE_GEODE = registerKey("add_duracite_geode");

    public static void bootstrap (BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_DURACITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.DURACITE_ORE_PLACED_KEY)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_DURACITE_DEEPSLATE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.DURACITE_DEEPSLATE_ORE_PLACED_KEY)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_DURACITE_NETHER_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_NETHER), HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.DURACITE_NETHER_ORE_PLACED_KEY)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_DURACITE_GEODE, new BiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeatures.getOrThrow(FMPlacedFeatures.DURACITE_GEODE_PLACED_KEY)), GenerationStep.Decoration.LOCAL_MODIFICATIONS));
    }

    private static ResourceKey<BiomeModifier> registerKey (String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FluxMachines.getResource(name));
    }

}
