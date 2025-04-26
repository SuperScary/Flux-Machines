package net.superscary.fluxmachines.core.registries;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.superscary.fluxmachines.core.blockentity.misc.CrucibleBlockEntity;
import net.superscary.fluxmachines.core.blockentity.misc.FluidTankBlockEntity;

/**
 * Register all block capabilities here
 */
public class FMCapabilities {

    public static void registerAll(RegisterCapabilitiesEvent event) {
        machine(event);
        reactor(event);
        cable(event);
        misc(event);
    }

    protected static void machine (RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.FLUX_FURNACE.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.FLUX_FURNACE.get(), (o, direction) -> o.getEnergyStorage());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.COAL_GENERATOR.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.COAL_GENERATOR.get(), (o, direction) -> o.getEnergyStorage());
    }

    protected static void reactor (RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.REACTOR_CORE.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.REACTOR_CORE.get(), (o, direction) -> o.getEnergyStorage());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, FMBlockEntities.REACTOR_CORE.get(), (o, direction) -> o.getCoolantTank());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, FMBlockEntities.REACTOR_CORE.get(), (o, direction) -> o.getFuelTank());

        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, FMBlockEntities.REACTOR_FLUID_PORT.get(), (o, direction) -> o.getFluidTank());

        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.REACTOR_POWER_TAP.get(), (o, direction) -> o.getEnergyStorage());
    }

    protected static void cable (RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.CABLE.get(), (o, direction) -> o.getEnergyStorage());
    }

    protected static void misc (RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.FLUID_TANK.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, FMBlockEntities.FLUID_TANK.get(), FluidTankBlockEntity::getTank);

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.CRUCIBLE.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, FMBlockEntities.CRUCIBLE.get(), CrucibleBlockEntity::getTank);
    }

}
