package net.superscary.fluxmachines.core.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.superscary.fluxmachines.api.manager.IRecipeManager;
import net.superscary.fluxmachines.core.FluxMachines;

public class FMRegistries {

	public static final ResourceKey<Registry<IRecipeManager<?>>> DEFERRED_RECIPE_MANAGER = createRegistryKey("deferred_recipe_manager");

	public static final Registry<IRecipeManager<?>> RECIPE_MANAGER_REGISTRY = new RegistryBuilder<>(DEFERRED_RECIPE_MANAGER)
			.sync(true)
			.defaultKey(FluxMachines.getResource("empty"))
			.create();

	@SuppressWarnings("SameParameterValue")
	private static <T> ResourceKey<Registry<T>> createRegistryKey (String name) {
		return ResourceKey.createRegistryKey(FluxMachines.getResource(name));
	}

	public static void registerRegistries (NewRegistryEvent event) {
		event.register(RECIPE_MANAGER_REGISTRY);
	}

}
