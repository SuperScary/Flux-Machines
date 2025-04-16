package net.superscary.fluxmachines.core.util;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;

import java.util.function.Supplier;

public class DeferredUpgradeMap<T extends FMBasePoweredBlockEntity> implements Supplier<UpgradeMap<T>> {

	private final Class<? extends FMBasePoweredBlockEntity> entity;
	private final DeferredHolder<UpgradeMap<?>, UpgradeMap<T>> holder;

	public DeferredUpgradeMap (Class<? extends FMBasePoweredBlockEntity> entity, DeferredHolder<UpgradeMap<?>, UpgradeMap<T>> holder) {
		this.entity = entity;
		this.holder = holder;
	}

	public Class<? extends FMBasePoweredBlockEntity> getEntity () {
		return entity;
	}

	@Override
	public UpgradeMap<T> get () {
		return holder.get();
	}
}
