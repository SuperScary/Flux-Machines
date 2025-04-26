package net.superscary.fluxmachines.core.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.superscary.fluxmachines.api.reactor.IReactorCoolant;

import java.util.function.Supplier;

public class DeferredCoolant<F extends Fluid> implements Supplier<IReactorCoolant<F>> {

	private final DeferredHolder<IReactorCoolant<?>, IReactorCoolant<F>> holder;

	public DeferredCoolant (DeferredHolder<IReactorCoolant<?>, IReactorCoolant<F>> holder) {
		this.holder = holder;
	}

	@Override
	public IReactorCoolant<F> get () {
		return holder.get();
	}

	public F getFluid () {
		return get().getReactorCoolant();
	}

	public ItemStack getItem () {
		return get().getReactorCoolantItem();
	}

	public double getEfficiency () {
		return get().getReactorCoolantEfficiency();
	}
}
