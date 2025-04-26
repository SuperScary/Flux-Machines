package net.superscary.fluxmachines.core.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.superscary.fluxmachines.api.manager.IRecipeManager;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.util.DeferredCoolant;
import net.superscary.fluxmachines.core.util.UpgradeMap;

import java.util.ArrayList;
import java.util.List;

public class FMRegistries {

	private static final List<Registry<?>> REGISTRIES = new ArrayList<>();

	public static final ResourceKey<Registry<IRecipeManager<?>>> DEFERRED_RECIPE_MANAGER = createRegistryKey("deferred_recipe_manager");
	public static final ResourceKey<Registry<UpgradeMap<?>>> UPGRADE_MAPS = createRegistryKey("upgrade_map");
	public static final ResourceKey<Registry<DeferredCoolant<?>>> DEFERRED_COOLANT = createRegistryKey("deferred_coolant");

	public static final Registry<IRecipeManager<?>> RECIPE_MANAGER_REGISTRY = create(DEFERRED_RECIPE_MANAGER);
	public static final Registry<UpgradeMap<?>> UPGRADE_MAP_REGISTRY = create(UPGRADE_MAPS);

	private static <T> Registry<T> create (ResourceKey<? extends Registry<T>> key) {
		var reg = new RegistryBuilder<>(key).sync(true).defaultKey(FluxMachines.getResource("empty")).create();
		REGISTRIES.add(reg);
		return reg;
	}

	private static <T> ResourceKey<Registry<T>> createRegistryKey (String name) {
		return ResourceKey.createRegistryKey(FluxMachines.getResource(name));
	}

	public static void registerRegistries (NewRegistryEvent event) {
		for (var registry : REGISTRIES) {
			event.register(registry);
		}
	}

}
