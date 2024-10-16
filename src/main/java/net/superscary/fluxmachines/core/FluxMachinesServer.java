package net.superscary.fluxmachines.core;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;

public class FluxMachinesServer extends FluxMachinesBase {

    public FluxMachinesServer (IEventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Level getClientLevel () {
        return null;
    }

}
