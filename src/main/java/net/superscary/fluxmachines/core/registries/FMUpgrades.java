package net.superscary.fluxmachines.core.registries;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.Tab;
import net.superscary.fluxmachines.core.item.upgrade.UpgradeBase;
import net.superscary.fluxmachines.core.item.upgrade.UpgradeEmpty;
import net.superscary.fluxmachines.core.upgrades.SpeedUpgrade;
import net.superscary.fluxmachines.core.upgrades.UpgradeFunction;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FMUpgrades {

	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(FluxMachines.MODID);

	private static final List<ItemDefinition<?>> UPGRADES = new ArrayList<>();

	public static final ItemDefinition<UpgradeBase> EMPTY = create("upgrade_base", UpgradeEmpty::new, null);
	public static final ItemDefinition<UpgradeBase> SPEED = create("speed_upgrade", UpgradeBase::new, SpeedUpgrade::new);
	public static final ItemDefinition<UpgradeBase> CAPACITY = create("capacity_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> EFFICIENCY = create("efficiency_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> OVERCLOCK = create("overclock_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> THERMAL_BUFFER = create("thermal_buffer_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> AUTO_EJECTOR = create("auto_ejector_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> INPUT_EXPANDER = create("input_expander_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> MULTI_PROCESSOR_UNIT = create("mpu_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> SILENCING_COIL = create("silencing_coil_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> NANITE_INJECTOR = create("nanite_injector_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> PRECISION_GEARBOX = create("precision_gearbox_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> REDSTONE_INTERFACE = create("redstone_interface_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> ECO_DRIVE = create("eco_drive_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> VOID_MOD = create("void_mod_upgrade", UpgradeBase::new, null);
	public static final ItemDefinition<UpgradeBase> REPLICATION_NODE = create("replication_node_upgrade", UpgradeBase::new, null);


	static <T extends UpgradeBase> ItemDefinition<T> create (String id, Function<Item.Properties, T> factory, @Nullable UpgradeFunction<T> caller) {
		return create(id, FluxMachines.getResource(id), factory, caller);
	}

	static <T extends UpgradeBase> ItemDefinition<T> create (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable UpgradeFunction<T> caller) {
		Preconditions.checkArgument(id.getNamespace().equals(FluxMachines.MODID), "Can only register items in FluxMachines");
		var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));
		Tab.add(definition);

		UPGRADES.add(definition);
		return definition;
	}

}
