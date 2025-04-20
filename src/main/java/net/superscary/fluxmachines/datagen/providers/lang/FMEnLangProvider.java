package net.superscary.fluxmachines.datagen.providers.lang;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.item.upgrade.UpgradeBase;
import net.superscary.fluxmachines.core.registries.FMUpgrades;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;

import static net.superscary.fluxmachines.core.registries.FMBlocks.*;
import static net.superscary.fluxmachines.core.registries.FMItems.*;
import static net.superscary.fluxmachines.core.registries.FMUpgrades.*;

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
        upgrades();
    }

    protected void blocks () {
        add(STEEL_BLOCK.block(), "Steel Block");

        add(DURACITE_ORE.block(), "Duracite Ore");
        add(DURACITE_DEEPSLATE_ORE.block(), "Duracite Deepslate Ore");
        add(DURACITE_NETHER_ORE.block(), "Duracite Nether Ore");
        add(DURACITE_BLOCK_RAW.block(), "Raw Duracite Block");
        add(DURACITE_BLOCK.block(), "Block of Duracite");

        add(MACHINE_CASING.block(), "Machine Casing");
        add(FLUX_FURNACE.block(), "Flux Furnace");
        add(COAL_GENERATOR.block(), "Coal Generator");
        add(FLUID_TANK.block(), "Fluid Tank");
        add(CRUCIBLE.block(), "Crucible");
        add(CABLE.block(), "Network Cable");
        add(FACADE.block(), "Cable Facade");
        add(REFRACTORY_BRICK.block(), "Refractory Bricks");
        add(REFRACTORY_BRICK_SLAB.block(), "Refractory Brick Slab");
        add(REFRACTORY_BRICK_STAIRS.block(), "Refractory Brick Stairs");
        add(REFRACTORY_WALL.block(), "Refractory Wall");
        add(CALCITE_STAIRS.block(), "Calcite Stairs");
        add(CALCITE_SLAB.block(), "Calcite Slab");
        add(LIMESTONE.block(), "Limestone");
        add(LIMESTONE_STAIRS.block(), "Limestone Stairs");
        add(LIMESTONE_SLAB.block(), "Limestone Slab");
        add(LIMESTONE_BRICKS.block(), "Limestone Bricks");
        add(LIMESTONE_BRICK_STAIRS.block(), "Limestone Brick Stairs");
        add(LIMESTONE_BRICK_SLAB.block(), "Limestone Brick Slab");
        add(LIMESTONE_POLISHED.block(), "Polished Limestone");
        add(LIMESTONE_POLISHED_STAIRS.block(), "Polished Limestone Stairs");
        add(LIMESTONE_POLISHED_SLAB.block(), "Polished Limestone Slab");
    }

    protected void items () {
        add(STEEL_DUST.asItem(), "Steel Dust");
        add(STEEL_INGOT.asItem(), "Steel Ingot");
        add(STEEL_NUGGET.asItem(), "Steel Nugget");

        add(RAW_DURACITE.asItem(), "Raw Duracite");
        add(DURACITE_DUST.asItem(), "Duracite Dust");
        add(DURACITE_INGOT.asItem(), "Duracite Ingot");
        add(DURACITE_NUGGET.asItem(), "Duracite Nugget");

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
        add(FLUX_POWDER.asItem(), "Flux Powder");
        add(CALCITE_DUST.asItem(), "Calcite Dust");
        add(COKE.asItem(), "Coke");

        add(REDSTONE_AND_STEEL.asItem(), "Redstone and Steel");

        add(HONEY_BUN.asItem(), "Honey Bun");
        add(HARD_BOILED_EGG.asItem(), "Hard Boiled Egg");
    }

    protected void upgrades () {
        add(FMUpgrades.EMPTY.asItem(), "Upgrade Base");
        addUpgrade(SPEED, "Speed Module", "Increases machine operation speed.");
        addUpgrade(CAPACITY, "Capacity Mod", "Expands the operational size of machines.");
        addUpgrade(EFFICIENCY, "Efficiency Core", "Reduces power consumption per operation.");
        addUpgrade(OVERCLOCK, "Overclock Chip", "Greatly increases speed but at a power efficiency cost.");
        addUpgrade(THERMAL_BUFFER, "Thermal Buffer", "Allows the machine to operate under extreme conditions.");
        addUpgrade(AUTO_EJECTOR, "Auto Ejector", "Automatically pushes output to connected inventories.");
        addUpgrade(INPUT_EXPANDER, "Input Expander", "Allows the machine to accept input from multiple sides.");
        addUpgrade(MULTI_PROCESSOR_UNIT, "Multi-Processor Unit", "Enables running multiple operations at once.");
        addUpgrade(SILENCING_COIL, "Silencing Coil", "Suppresses sounds emitted from the machine.");
        addUpgrade(NANITE_INJECTOR, "Nanite Injector", "Increases yield of byproducts or rare drops.");
        addUpgrade(PRECISION_GEARBOX, "Precision Gearbox", "Increases accuracy for machines with chance-based outputs.");
        addUpgrade(REDSTONE_INTERFACE, "Redstone Interface", "Adds advanced redstone control options.");
        addUpgrade(ECO_DRIVE, "EcoDrive Module", "Idle machines draw near-zero power.");
        addUpgrade(VOID_MOD, "Void Module", "Destroys overflow items instead of clogging the machine.");
        addUpgrade(REPLICATION_NODE, "Replication Node", "Duplicates output at a high power cost.");
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
        add("gui.fluxmachines.itemlist", "Slot %s: %sx %s");
        add("gui.fluxmachines.press_shift", "Hold §e[SHIFT]§r for more info.");
        add("gui.fluxmachines.upgrade_tooltip", "Compatible Upgrades");
        add("gui.fluxmachines.upgrade_tooltip.item", "§7§o- §7§o%s §7§ox%s");

        add("message.fluxmachines.data_link_tool.copied_data", "§aCopied Data");
        add("message.fluxmachines.data_link_tool.wrote_data", "§aWrote Data");
        add("message.fluxmachines.data_link_tool.empty_data", "§cNo Data");
        add("message.fluxmachines.data_link_tool.cleared_data", "§9§oCleared Data");
        add("tooltip.fluxmachines.data_link_tool.has_data", "§aHas Data");

        add("item.fluxmachines.patchouli.book.name", "Flux Machines Field Manual");
        add("item.fluxmachines.patchouli.book.subtitle", "Flux Machines & You");

        add("armor.status.effect.tooltip", "§7§nFull Set Status Effect:§r");
        add("armor.status.effect.kb2.tooltip", "§8- Knockback II§r");

        add("fluxmachines.tooltip.liquid.amount.with.capacity", "%s / %s mB");
        add("fluxmachines.tooltip.liquid.amount", "%s mB");
    }

    protected void subtitles () {
        add("sound.fluxmachines.flux_furnace_on", "Flux Furnace");
        add("sound.fluxmachines.ratchet", "Ratchet");
    }

    protected void entity () {
        add("entity.minecraft.villager.fluxmachines.engineer", "Engineer");
    }

    protected void addUpgrade (ItemDefinition<UpgradeBase> item, String name, String desc) {
        add(item.asItem(), name);
        add(item.asItem().getDescriptionId() + ".desc", "§7§o" + desc);
    }

}