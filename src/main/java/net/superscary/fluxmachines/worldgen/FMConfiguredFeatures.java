package net.superscary.fluxmachines.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMBlocks;

import java.util.List;

public class FMConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> STEEL_GEODE_KEY = registerKey("steel_geode");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DURACITE_ORE_KEY_LARGE = registerKey("duracite_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DURACITE_ORE_KEY_SMALL = registerKey("duracite_ore_small");

    public static void bootstrap (BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldDuraciteOres = List.of(OreConfiguration.target(stoneReplaceables, FMBlocks.DURACITE_ORE.block().defaultBlockState()), OreConfiguration.target(deepslateReplaceables, FMBlocks.DURACITE_DEEPSLATE_ORE.block().defaultBlockState()));

        register(context, OVERWORLD_DURACITE_ORE_KEY_LARGE, Feature.ORE, new OreConfiguration(overworldDuraciteOres, 8));
        register(context, OVERWORLD_DURACITE_ORE_KEY_SMALL, Feature.ORE, new OreConfiguration(overworldDuraciteOres, 4));

        register(context, STEEL_GEODE_KEY, Feature.GEODE, new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(Blocks.DEEPSLATE), BlockStateProvider.simple(FMBlocks.STEEL_BLOCK.block()), BlockStateProvider.simple(Blocks.COBBLESTONE), BlockStateProvider.simple(Blocks.COBBLESTONE), List.of(FMBlocks.STEEL_BLOCK.block().defaultBlockState()), BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS), new GeodeLayerSettings(1.7d, 1.2d, 2.5d, 3.5d), new GeodeCrackSettings(0.25d, 1.5d, 1), 0.5d, 0.1d, true, UniformInt.of(3, 8), UniformInt.of(2, 6), UniformInt.of(1, 2), -18, 18, 0.075D, 1));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey (String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FluxMachines.getResource(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register (BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
