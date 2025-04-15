package net.superscary.fluxmachines.core;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class FluxMachinesServer extends FluxMachinesBase {

    public FluxMachinesServer (ModContainer container, IEventBus eventBus) {
        super(container, eventBus);
    }

    @Override
    public Level getClientLevel () {
        return null;
    }

}
