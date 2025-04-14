package net.superscary.fluxmachines.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.datagen.providers.data.AchievementProvider;
import net.superscary.fluxmachines.datagen.providers.data.CompostableProvider;
import net.superscary.fluxmachines.datagen.providers.data.SoundProvider;
import net.superscary.fluxmachines.datagen.providers.data.StructureUpdater;
import net.superscary.fluxmachines.datagen.providers.lang.FMEnLangProvider;
import net.superscary.fluxmachines.datagen.providers.loot.FMLootTableProvider;
import net.superscary.fluxmachines.datagen.providers.models.BlockModelProvider;
import net.superscary.fluxmachines.datagen.providers.models.FMItemModelProvider;
import net.superscary.fluxmachines.datagen.providers.recipes.*;
import net.superscary.fluxmachines.datagen.providers.tag.FMBlockTagGenerator;
import net.superscary.fluxmachines.datagen.providers.tag.FMItemTagGenerator;
import net.superscary.fluxmachines.datagen.providers.tag.FMPoiTagGenerator;
import net.superscary.fluxmachines.datagen.providers.worldgen.WorldGenProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = FluxMachines.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gather (GatherDataEvent event) {
        var generator = event.getGenerator();
        var registries = event.getLookupProvider();
        var pack = generator.getVanillaPack(true);
        var existingFileHelper = event.getExistingFileHelper();
        var localization = new FMEnLangProvider(generator);

        // WORLD GEN
        pack.addProvider(output -> new WorldGenProvider(output, registries));

        // SOUNDS
        pack.addProvider(packOutput -> new SoundProvider(packOutput, existingFileHelper));

        // LOOT TABLE
        pack.addProvider(bindRegistries(FMLootTableProvider::new, registries));

        // POI
        //pack.addProvider(pOutput -> new FMPoiTypeTagsProvider(pOutput, registries, existingFileHelper));
        pack.addProvider(packOutput -> new FMPoiTagGenerator(packOutput, registries, existingFileHelper));

        // TAGS
        var blockTagsProvider = pack.addProvider(pOutput -> new FMBlockTagGenerator(pOutput, registries, existingFileHelper));
        pack.addProvider(pOutput -> new FMItemTagGenerator(pOutput, registries, blockTagsProvider.contentsGetter(), existingFileHelper));
        //pack.addProvider(pOutput -> new FMFluidTagsProvider(pOutput, registries, existingFileHelper));

        // MODELS & STATES
        pack.addProvider(pOutput -> new BlockModelProvider(pOutput, existingFileHelper));
        pack.addProvider(pOutput -> new FMItemModelProvider(pOutput, existingFileHelper));

        // RECIPES
        pack.addProvider(bindRegistries(CraftingRecipes::new, registries));
        pack.addProvider(bindRegistries(SmeltingRecipes::new, registries));
        pack.addProvider(bindRegistries(BlastingRecipes::new, registries));
        pack.addProvider(bindRegistries(FluxSmeltingRecipes::new, registries));
        pack.addProvider(bindRegistries(CompressorRecipes::new, registries));
        //pack.addProvider(bindRegistries(CrusherRecipes::new, registries));
        //pack.addProvider(bindRegistries(SawmillRecipes::new, registries));
        //pack.addProvider(bindRegistries(FluidInfusingRecipes::new, registries));

        pack.addProvider(packOutput -> new CompostableProvider(packOutput, registries));
        pack.addProvider(packOutput -> new AchievementProvider(packOutput, registries, existingFileHelper));
        pack.addProvider(pOutput -> new StructureUpdater("structures", FluxMachines.MODID, existingFileHelper, pOutput));

        // LOCALIZATION MUST RUN LAST
        pack.addProvider(output -> localization);

    }

    private static <T extends DataProvider> DataProvider.Factory<T> bindRegistries (BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> factory, CompletableFuture<HolderLookup.Provider> factories) {
        return pOutput -> factory.apply(pOutput, factories);
    }

}