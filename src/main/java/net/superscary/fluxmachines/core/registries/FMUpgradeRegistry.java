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
import net.superscary.fluxmachines.core.util.block.BlockDefinition;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;

import java.util.ArrayList;
import java.util.List;

import static net.superscary.fluxmachines.core.registries.FMUpgrades.*;

public class FMUpgradeRegistry {

	public static final List<DeferredUpgradeMap<?>> UPGRADE_MAPS = new ArrayList<>();

	public static final DeferredRegister<UpgradeMap<?>> REGISTRY = DeferredRegister.create(FMRegistries.UPGRADE_MAP_REGISTRY, FluxMachines.MODID);

	public static final DeferredUpgradeMap<FluxFurnaceBlockEntity> FLUX_FURNACE_BLOCK_ENTITY_UPGRADES = build(FMBlocks.FLUX_FURNACE, FluxFurnaceBlockEntity.class, FMBlockEntities.FLUX_FURNACE, UpgradePairs.POWERED_CRAFTER.getUpgrades());

	private static <T extends FMBasePoweredBlockEntity> DeferredUpgradeMap<T> build (BlockDefinition<?> id, Class<? extends FMBasePoweredBlockEntity> cl, DeferredBlockEntityType<T> entity, ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
		return build(id.getEnglishName(), cl, entity, upgrades);
	}

	private static <T extends FMBasePoweredBlockEntity> DeferredUpgradeMap<T> build (String id, Class<? extends FMBasePoweredBlockEntity> cl, DeferredBlockEntityType<T> entity, ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
		Preconditions.checkArgument(Upgradeable.class.isAssignableFrom(cl), "Cannot register upgrades to an object that is not Upgradeable.");

		var deferred = REGISTRY.register(id, () -> new UpgradeMap<>(entity, upgrades));
		var supplier = new DeferredUpgradeMap<>(cl, deferred);
		UPGRADE_MAPS.add(supplier);
		return supplier;
	}

	enum UpgradePairs {
		POWERED_CRAFTER (ImmutableList.of(
				make(SPEED),
				make(CAPACITY),
				make(EFFICIENCY),
				make(OVERCLOCK, 1),
				make(AUTO_EJECTOR, 1),
				make(SILENCING_COIL, 1),
				make(REDSTONE_INTERFACE, 1),
				make(ECO_DRIVE, 4),
				make(VOID_MOD, 1),
				make(REPLICATION_NODE)
		));

		private final ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades;
		UpgradePairs (ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
			this.upgrades = upgrades;
		}

		public ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> getUpgrades () {
			return this.upgrades;
		}

		private static Pair<ItemDefinition<UpgradeBase>, Integer> make (ItemDefinition<UpgradeBase> base, Integer maximum) {
			return new Pair<>(base, maximum);
		}

		private static Pair<ItemDefinition<UpgradeBase>, Integer> make (ItemDefinition<UpgradeBase> base) {
			return make(base, MAX_UPGRADES);
		}
	}

}
