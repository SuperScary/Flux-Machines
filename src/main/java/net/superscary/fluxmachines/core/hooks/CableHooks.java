package net.superscary.fluxmachines.core.hooks;

import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.model.CableModelLoader;
import net.superscary.fluxmachines.model.FacadeBlockColor;

public class CableHooks {

    public static void initModels(ModelEvent.RegisterGeometryLoaders event) {
        CableModelLoader.register(event);
    }

    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register(new FacadeBlockColor(), FMBlocks.FACADE.block());
    }

}
