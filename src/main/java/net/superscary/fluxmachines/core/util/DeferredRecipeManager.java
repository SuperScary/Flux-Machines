package net.superscary.fluxmachines.core.util;

import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.superscary.fluxmachines.api.manager.IRecipeManager;

import java.util.function.Supplier;

public class DeferredRecipeManager<T extends Recipe<?>> implements Supplier<IRecipeManager<T>> {

	private final Class<? extends IRecipeManager<T>> recipeManagerClass;

	private final DeferredHolder<IRecipeManager<?>, IRecipeManager<T>> holder;

	public DeferredRecipeManager (Class<? extends IRecipeManager<T>> recipeManagerClass, DeferredHolder<IRecipeManager<?>, IRecipeManager<T>> holder) {
		this.recipeManagerClass = recipeManagerClass;
		this.holder = holder;
	}

	public Class<? extends IRecipeManager<T>> getRecipeManagerClass () {
		return recipeManagerClass;
	}

	@Override
	public IRecipeManager<T> get () {
		return holder.get();
	}
}
