package net.superscary.fluxmachines.core.hooks;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMRecipeManagers;
import net.superscary.fluxmachines.core.util.DeferredRecipeManager;

@EventBusSubscriber(modid = FluxMachines.MODID)
public class ServerHooks {

	@SubscribeEvent
	public static void registerRecipeManagers (EntityJoinLevelEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		var recipes = event.getLevel().getRecipeManager();
		for (DeferredRecipeManager<?> manager : FMRecipeManagers.getRecipeManagers()) {
			FluxMachines.LOGGER.info("Registering {}", manager.get().getRecipeType());
			manager.get().refresh(recipes);
		}
	}

}
