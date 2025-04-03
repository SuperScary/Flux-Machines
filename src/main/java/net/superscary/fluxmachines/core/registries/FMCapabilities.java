package net.superscary.fluxmachines.core.registries;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.superscary.fluxmachines.core.blockentity.misc.FluidTankBlockEntity;

/**
 * Register all block capabilities here
 */
public class FMCapabilities {

    public static void registerAll(RegisterCapabilitiesEvent event) {
        machine(event);
        cable(event);
        misc(event);
    }

    protected static void machine (RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.FLUX_FURNACE.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.FLUX_FURNACE.get(), (o, direction) -> o.getEnergyStorage());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.COAL_GENERATOR.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.COAL_GENERATOR.get(), (o, direction) -> o.getEnergyStorage());
    }

    protected static void cable (RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.CABLE.get(), (o, direction) -> o.getEnergyStorage());
    }

    protected static void misc (RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.FLUID_TANK.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, FMBlockEntities.FLUID_TANK.get(), FluidTankBlockEntity::getTank);
    }

}
