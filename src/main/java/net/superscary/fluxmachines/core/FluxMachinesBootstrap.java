package net.superscary.fluxmachines.core;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(FluxMachines.MODID)
public class FluxMachinesBootstrap {

    public FluxMachinesBootstrap (IEventBus modEventBus) {
        switch (FMLEnvironment.dist) {
            case CLIENT -> new FluxMachinesClient(modEventBus);
            case DEDICATED_SERVER -> new FluxMachinesServer(modEventBus);
        }
    }

}
