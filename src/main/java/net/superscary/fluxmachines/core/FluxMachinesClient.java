package net.superscary.fluxmachines.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;

public class FluxMachinesClient extends FluxMachinesBase {

    public FluxMachinesClient (IEventBus eventBus) {
        super(eventBus);
    }

    @Override
    public Level getClientLevel () {
        return Minecraft.getInstance().level;
    }

}
