package net.superscary.fluxmachines.core.registries;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.superscary.fluxmachines.model.renderer.FluidTankBlockEntityRenderer;

public class FMRenderers {

	public static void registerBER (EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(FMBlockEntities.FLUID_TANK.get(), FluidTankBlockEntityRenderer::new);
	}

}
