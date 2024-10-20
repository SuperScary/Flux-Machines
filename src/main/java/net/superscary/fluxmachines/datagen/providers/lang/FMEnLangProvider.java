package net.superscary.fluxmachines.datagen.providers.lang;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.block.machine.FluxFurnaceBlock;
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
        add(FLUX_FURNACE.block(), "Flux Furnace");
    }

    protected void items () {
        add(RAW_DURACITE.asItem(), "Raw Duracite");
        add(DURACITE_DUST.asItem(), "Duracite Dust");
        add(DURACITE_INGOT.asItem(), "Duracite Ingot");
        add(DURACITE_NUGGET.asItem(), "Duracite Nugget");
        add(DURACITE_HELMET.asItem(), "Duracite Helmet");
        add(DURACITE_CHESTPLATE.asItem(), "Duracite Chestplate");
        add(DURACITE_LEGGINGS.asItem(), "Duracite Leggings");
        add(DURACITE_BOOTS.asItem(), "Duracite Boots");
        add(DURACITE_SWORD.asItem(), "Duracite Sword");
        add(DURACITE_PICKAXE.asItem(), "Duracite Pickaxe");
        add(DURACITE_SHOVEL.asItem(), "Duracite Shovel");
        add(DURACITE_AXE.asItem(), "Duracite Axe");
        add(DURACITE_HOE.asItem(), "Duracite Hoe");
        add(DURACITE_PAXEL.asItem(), "Duracite Paxel");
        add(DURACITE_HAMMER.asItem(), "Duracite Hammer");

        add(HONEY_BUN.asItem(), "Honey Bun");
        add(HARD_BOILED_EGG.asItem(), "Hard Boiled Egg");
    }

    protected void misc () {
        add("itemGroup.fluxmachines", "Flux Machines");
        add("advancement.fluxmachines.title", "Flux Machines");
        add("advancement.fluxmachines.desc", "Try something spicy!");
        add("advancement.fluxmachines.newworld.title", "Whole New World!");
        add("advancement.fluxmachines.newworld.desc", "It's ok to close your eyes.");
        add("advancement.fluxmachines.why.title", "Why would you do that?");
        add("advancement.fluxmachines.why.desc", "You aren't Eduardo Saverin.");
        add("advancement.fluxmachines.strongerthaniron.title", "Stronger Than Iron");
        add("advancement.fluxmachines.strongerthaniron.desc", "Heavier too...");
        add("advancement.fluxmachines.justincase.title", "Just in case...");
        add("advancement.fluxmachines.justincase.desc", "You'll probably need it.");

        add("armor.status.effect.tooltip", "§7§nFull Set Status Effect:§r");
        add("armor.status.effect.kb2.tooltip", "§8- Knockback II§r");
    }

}