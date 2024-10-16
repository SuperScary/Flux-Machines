package net.superscary.fluxmachines.datagen.lang;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;

import static net.superscary.fluxmachines.registries.FMBlocks.*;
import static net.superscary.fluxmachines.registries.FMItems.*;

public class FMEnLangProvider extends LanguageProvider implements IDataProvider {

    public FMEnLangProvider (DataGenerator generator) {
        super(generator.getPackOutput(), FluxMachines.MODID, "en_us");
    }

    @Override
    protected void addTranslations () {
        blocks();
        items();
        misc();
    }

    protected void blocks () {
        add(DURACITE_ORE.block(), "Duracite Ore");
        add(DURACITE_DEEPSLATE_ORE.block(), "Deepslate Duracite Ore");
        add(DURACITE_NETHER_ORE.block(), "Duracite Nether Ore");
        add(DURACITE_BLOCK_RAW.block(), "Raw Duracite Block");
        add(DURACITE_BLOCK.block(), "Duracite Block");
        add(MACHINE_CASING.block(), "Machine Casing");
    }

    protected void items () {
        add(RAW_DURACITE.asItem(), "Raw Duracite");
        add(DURACITE_DUST.asItem(), "Duracite Dust");
        add(DURACITE_INGOT.asItem(), "Duracite Ingot");
        add(DURACITE_NUGGET.asItem(), "Duracite Nugget");
    }

    protected void misc () {
        add("itemGroup.fluxmachines", "Flux Machines");
    }

}