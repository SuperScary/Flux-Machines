package net.superscary.fluxmachines.core.registries;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.api.blockentity.Upgradeable;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.blockentity.machine.FluxFurnaceBlockEntity;
import net.superscary.fluxmachines.core.item.upgrade.UpgradeBase;
import net.superscary.fluxmachines.core.util.DeferredBlockEntityType;
import net.superscary.fluxmachines.core.util.DeferredUpgradeMap;
import net.superscary.fluxmachines.core.util.UpgradeMap;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;

import java.util.ArrayList;
import java.util.List;

public class FMUpgradeRegistry {

	public static final List<UpgradeMap<?>> UPGRADE_MAPS = new ArrayList<>();

	public static final DeferredRegister<UpgradeMap<?>> REGISTRY = DeferredRegister.create(FMRegistries.UPGRADE_MAP_REGISTRY, FluxMachines.MODID);

	public static final DeferredUpgradeMap<FluxFurnaceBlockEntity> FLUX_FURNACE_BLOCK_ENTITY_UPGRADES = build("flux_furnace", FluxFurnaceBlockEntity.class, FMBlockEntities.FLUX_FURNACE,
			ImmutableList.of(new Pair<>(FMUpgrades.SPEED, 4)));

	private static <T extends FMBasePoweredBlockEntity> DeferredUpgradeMap<T> build (String id, Class<? extends FMBasePoweredBlockEntity> cl, DeferredBlockEntityType<T> entity, ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
		Preconditions.checkArgument(cl.isAssignableFrom(Upgradeable.class), "Cannot register upgrades to an object that is not Upgradeable.");

		var deferred = REGISTRY.register(id, () -> new UpgradeMap<>(entity, upgrades));
		var supplier = new DeferredUpgradeMap<T>(cl, deferred);
		UPGRADE_MAPS.add(supplier.get());
		return supplier;
	}

}
