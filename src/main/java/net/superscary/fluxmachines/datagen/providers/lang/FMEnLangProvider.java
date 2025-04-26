package net.superscary.fluxmachines.datagen.providers.lang;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.item.upgrade.UpgradeBase;
import net.superscary.fluxmachines.core.registries.FMUpgrades;
import net.superscary.fluxmachines.core.util.block.BlockDefinition;
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
        multiblock();
        subtitles();
        entity();
        upgrades();
    }

    protected void blocks () {
        add(STEEL_BLOCK, "Steel Block");

        add(DURACITE_ORE, "Duracite Ore");
        add(DURACITE_DEEPSLATE_ORE, "Duracite Deepslate Ore");
        add(DURACITE_NETHER_ORE, "Duracite Nether Ore");
        add(DURACITE_BLOCK_RAW, "Raw Duracite Block");
        add(DURACITE_BLOCK, "Block of Duracite");

        add(URANIUM_ORE, "Uranium Ore");

        add(MACHINE_CASING, "Machine Casing");
        add(REACTOR_FRAME, "Reactor Frame");
        add(REACTOR_GLASS, "Reactor Glass");
        add(REACTOR_CORE, "Reactor Core");
        add(REACTOR_FLUID_PORT, "Reactor Fluid Port");
        add(REACTOR_REDSTONE_PORT, "Reactor Redstone Port");
        add(REACTOR_POWER_TAP, "Reactor Power Tap");

        add(LASER_LENS, "Laser Lens");
        add(LASER_FRAME, "Laser Frame");

        add(BATTERY_CASING, "Battery Casing");

        add(FLUX_FURNACE, "Flux Furnace");
        add(COAL_GENERATOR, "Coal Generator");
        add(FLUID_TANK, "Fluid Tank");
        add(CRUCIBLE, "Crucible");
        add(CABLE, "Network Cable");
        add(FACADE, "Cable Facade");
        add(REFRACTORY_BRICK, "Refractory Bricks");
        add(REFRACTORY_BRICK_SLAB, "Refractory Brick Slab");
        add(REFRACTORY_BRICK_STAIRS, "Refractory Brick Stairs");
        add(REFRACTORY_WALL, "Refractory Wall");
        add(CALCITE_STAIRS, "Calcite Stairs");
        add(CALCITE_SLAB, "Calcite Slab");
        add(LIMESTONE, "Limestone");
        add(LIMESTONE_STAIRS, "Limestone Stairs");
        add(LIMESTONE_SLAB, "Limestone Slab");
        add(LIMESTONE_BRICKS, "Limestone Bricks");
        add(LIMESTONE_BRICK_STAIRS, "Limestone Brick Stairs");
        add(LIMESTONE_BRICK_SLAB, "Limestone Brick Slab");
        add(LIMESTONE_POLISHED, "Polished Limestone");
        add(LIMESTONE_POLISHED_STAIRS, "Polished Limestone Stairs");
        add(LIMESTONE_POLISHED_SLAB, "Polished Limestone Slab");
    }

    protected void items () {
        add(STEEL_DUST, "Steel Dust");
        add(STEEL_INGOT, "Steel Ingot");
        add(STEEL_NUGGET, "Steel Nugget");

        add(RAW_DURACITE, "Raw Duracite");
        add(DURACITE_DUST, "Duracite Dust");
        add(DURACITE_INGOT, "Duracite Ingot");
        add(DURACITE_NUGGET, "Duracite Nugget");

        add(RAW_URANIUM, "Raw Uranium");
        add(URANIUM_DUST, "Uranium Dust");
        add(URANIUM_INGOT, "Uranium Ingot");
        add(URANIUM_NUGGET, "Uranium Nugget");
        add(REFINED_URANIUM, "Refined Uranium");

        add(STEEL_HELMET, "Steel Helmet");
        add(STEEL_CHESTPLATE, "Steel Chestplate");
        add(STEEL_LEGGINGS, "Steel Leggings");
        add(STEEL_BOOTS, "Steel Boots");
        add(STEEL_SWORD, "Steel Sword");
        add(STEEL_PICKAXE, "Steel Pickaxe");
        add(STEEL_SHOVEL, "Steel Shovel");
        add(STEEL_AXE, "Steel Axe");
        add(STEEL_HOE, "Steel Hoe");
        add(STEEL_PAXEL, "Steel Paxel");
        add(STEEL_HAMMER, "Steel Hammer");
        add(WRENCH, "Wrench");
        add(DATA_LINK, "Data Linker");
        add(RUBBER, "Rubber");
        add(INDUSTRIAL_SLAG, "Industrial Slag");
        add(FERTILIZER, "Fertilizer");
        add(FLUX_POWDER, "Flux Powder");
        add(CALCITE_DUST, "Calcite Dust");
        add(COKE, "Coke");

        add(REDSTONE_AND_STEEL, "Redstone and Steel");

        add(HONEY_BUN, "Honey Bun");
        add(HARD_BOILED_EGG, "Hard Boiled Egg");
    }

    protected void upgrades () {
        add(FMUpgrades.EMPTY, "Upgrade Base");
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
        add("gui.fluxmachines.reactor.start", "Start");
        add("gui.fluxmachines.reactor.stop", "Stop");

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

    protected void multiblock () {
        add("multiblock.fluxmachines.reactor", "Reactor");
        add("multiblock.fluxmachines.reactor.invalid", "Invalid Reactor Structure");
        add("multiblock.fluxmachines.reactor.valid", "Reactor Structure Validated");
        add("multiblock.fluxmachines.reactor.fluid_port.mode.input", "Fluid Port Mode: Input");
        add("multiblock.fluxmachines.reactor.fluid_port.mode.output", "Fluid Port Mode: Output");
        add("multiblock.fluxmachines.reactor.redstone_port.mode.input", "Redstone Port Mode: Input");
        add("multiblock.fluxmachines.reactor.redstone_port.mode.output", "Redstone Port Mode: Output");
        add("multiblock.fluxmachines.reactor.temperature.kelvin", "Temp: %s K");
        add("multiblock.fluxmachines.reactor.temperature.celsius", "Temp: %s °C");
        add("multiblock.fluxmachines.reactor.temperature.fahrenheit", "Temp: %s °F");
        add("multiblock.fluxmachines.reactor.efficiency", "Efficiency: %s%%");
        add("multiblock.fluxmachines.reactor.tooltip.fuel", "Fuel");
        add("multiblock.fluxmachines.reactor.tooltip.coolant", "Coolant");
        add("multiblock.fluxmachines.reactor.tooltip.heat", "Heat");
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

    protected void add (ItemDefinition<?> item, String name) {
        add(item.asItem(), name);
    }

    protected void add (BlockDefinition<?> block, String name) {
        add(block.block(), name);
    }

}