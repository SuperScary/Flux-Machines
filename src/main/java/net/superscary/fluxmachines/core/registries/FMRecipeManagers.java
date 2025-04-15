package net.superscary.fluxmachines.core.registries;

import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.api.manager.IRecipeManager;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.CompressorRecipe;
import net.superscary.fluxmachines.core.recipe.CrucibleRecipe;
import net.superscary.fluxmachines.core.recipe.FluxSmeltingRecipe;
import net.superscary.fluxmachines.core.recipe.manager.CompressorRecipeManager;
import net.superscary.fluxmachines.core.recipe.manager.CrucibleRecipeManager;
import net.superscary.fluxmachines.core.recipe.manager.FluxSmeltingRecipeManager;
import net.superscary.fluxmachines.core.util.DeferredRecipeManager;
import net.superscary.fluxmachines.core.util.keys.Keys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FMRecipeManagers {

	private static final List<DeferredRecipeManager<?>> RECIPE_MANAGERS = new ArrayList<>();

	public static final DeferredRegister<IRecipeManager<?>> REGISTRY = DeferredRegister.create(FMRegistries.DEFERRED_RECIPE_MANAGER, FluxMachines.MODID);

	public static final DeferredRecipeManager<FluxSmeltingRecipe> FLUX_SMELTING_RECIPE_MANAGER = create("flux_smelting_recipe_manager", FluxSmeltingRecipeManager.class, FluxSmeltingRecipeManager::new);
	public static final DeferredRecipeManager<CompressorRecipe> COMPRESSOR_RECIPE_RECIPE_MANAGER = create("compressor_recipe_manager", CompressorRecipeManager.class, CompressorRecipeManager::new);
	public static final DeferredRecipeManager<CrucibleRecipe> CRUCIBLE_RECIPE_RECIPE_MANAGER = create("crucible_recipe_manager", CrucibleRecipeManager.class, CrucibleRecipeManager::new);

	private static <T extends Recipe<?>> DeferredRecipeManager<T> create (String id, Class<? extends IRecipeManager<T>> cl, RecipeManagerFactory<T> factory) {
		var deferred = REGISTRY.register(id, factory::create);
		var supplier = new DeferredRecipeManager<>(cl, deferred);
		RECIPE_MANAGERS.add(supplier);
		return supplier;
	}

	public static List<DeferredRecipeManager<?>> getRecipeManagers () {
		return Collections.unmodifiableList(RECIPE_MANAGERS);
	}

	@FunctionalInterface
	interface RecipeManagerFactory<T extends Recipe<?>> {
		IRecipeManager<T> create ();
	}

}
