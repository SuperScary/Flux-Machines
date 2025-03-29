package net.superscary.fluxmachines.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.superscary.fluxmachines.core.registries.FMMenus;
import net.superscary.fluxmachines.gui.screen.FluxFurnaceScreen;

public class FluxMachinesClient extends FluxMachinesBase {

    public FluxMachinesClient (IEventBus eventBus) {
        super(eventBus);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::registerMenuScreens);
    }

    @Override
    public Level getClientLevel () {
        return Minecraft.getInstance().level;
    }

    // TODO: Deprecated method to allow transparency.
    private void clientSetup (FMLClientSetupEvent event) {

    }

    private void registerMenuScreens (RegisterMenuScreensEvent event) {
        event.register(FMMenus.FLUX_FURNACE_MENU.get(), FluxFurnaceScreen::new);
    }

}
