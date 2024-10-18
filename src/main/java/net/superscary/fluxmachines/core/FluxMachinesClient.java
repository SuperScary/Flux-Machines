package net.superscary.fluxmachines.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.superscary.fluxmachines.registries.FMBlocks;

public class FluxMachinesClient extends FluxMachinesBase {

    public FluxMachinesClient (IEventBus eventBus) {
        super(eventBus);
        eventBus.addListener(this::clientSetup);
    }

    @Override
    public Level getClientLevel () {
        return Minecraft.getInstance().level;
    }

    // TODO: Deprecated method to allow transparency.
    private void clientSetup (FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FMBlocks.MACHINE_CASING.block(), RenderType.CUTOUT);
    }

}
