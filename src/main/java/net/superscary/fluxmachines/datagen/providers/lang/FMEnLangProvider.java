package net.superscary.fluxmachines.datagen.providers.lang;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;

import static net.superscary.fluxmachines.core.registries.FMBlocks.*;
import static net.superscary.fluxmachines.core.registries.FMItems.*;

public class FMEnLangProvider extends LanguageProvider implements IDataProvider {

    public FMEnLangProvider (DataGenerator generator) {
        super(generator.getPackOutput(), FluxMachines.MODID, "en_us");
    }

    @Override
    protected void addTranslations () {
        blocks();
        items();
        misc();
        subtitles();
        entity();
    }

    protected void blocks () {
        add(STEEL_BLOCK.block(), "Steel Block");
        add(MACHINE_CASING.block(), "Machine Casing");
        add(FLUX_FURNACE.block(), "Flux Furnace");
        add(COAL_GENERATOR.block(), "Coal Generator");
        add(CABLE.block(), "Network Cable");
        add(FACADE.block(), "Cable Facade");
    }

    protected void items () {
        add(STEEL_DUST.asItem(), "Steel Dust");
        add(STEEL_INGOT.asItem(), "Steel Ingot");
        add(STEEL_NUGGET.asItem(), "Steel Nugget");
        add(STEEL_HELMET.asItem(), "Steel Helmet");
        add(STEEL_CHESTPLATE.asItem(), "Steel Chestplate");
        add(STEEL_LEGGINGS.asItem(), "Steel Leggings");
        add(STEEL_BOOTS.asItem(), "Steel Boots");
        add(STEEL_SWORD.asItem(), "Steel Sword");
        add(STEEL_PICKAXE.asItem(), "Steel Pickaxe");
        add(STEEL_SHOVEL.asItem(), "Steel Shovel");
        add(STEEL_AXE.asItem(), "Steel Axe");
        add(STEEL_HOE.asItem(), "Steel Hoe");
        add(STEEL_PAXEL.asItem(), "Steel Paxel");
        add(STEEL_HAMMER.asItem(), "Steel Hammer");
        add(WRENCH.asItem(), "Wrench");
        add(DATA_LINK.asItem(), "Data Linker");
        add(RUBBER.asItem(), "Rubber");
        add(INDUSTRIAL_SLAG.asItem(), "Industrial Slag");
        add(FERTILIZER.asItem(), "Fertilizer");

        add(REDSTONE_AND_STEEL.asItem(), "Redstone and Steel");

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

        add("gui.fluxmachines.progress", "Progress: "); // the space is important!!!!
        add("gui.fluxmachines.idle", "Idle");
        add("gui.fluxmachines.gui.settings.right", "Right Click to Expand");
        add("gui.fluxmachines.gui.settings.left", "Left Click to Expand");
        add("gui.fluxmachines.gui.settings", "Settings");

        add("message.fluxmachines.data_link_tool.copied_data", "§aCopied Data");
        add("message.fluxmachines.data_link_tool.wrote_data", "§aWrote Data");
        add("message.fluxmachines.data_link_tool.empty_data", "§cNo Data");
        add("message.fluxmachines.data_link_tool.cleared_data", "§9§oCleared Data");
        add("tooltip.fluxmachines.data_link_tool.has_data", "§aHas Data");

        add("gui.fluxmachines.itemlist", "Slot %s: %sx %s");
        add("gui.fluxmachines.press_shift", "Hold §e[SHIFT]§r for more info.");

        add("item.fluxmachines.patchouli.book.name", "Flux Machines");
        add("item.fluxmachines.patchouli.book.subtitle", "Flux Machines & You");

        add("armor.status.effect.tooltip", "§7§nFull Set Status Effect:§r");
        add("armor.status.effect.kb2.tooltip", "§8- Knockback II§r");
    }

    protected void subtitles () {
        add("sound.fluxmachines.flux_furnace_on", "Flux Furnace");
        add("sound.fluxmachines.ratchet", "Ratchet");
    }

    protected void entity () {
        add("entity.minecraft.villager.fluxmachines.engineer", "Engineer");
    }

}