package net.superscary.fluxmachines.core.registries;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.api.reactor.IReactorCoolant;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.util.DeferredCoolant;

import java.util.ArrayList;
import java.util.List;

public class FMCoolants {

	public static final List<DeferredCoolant<?>> COOLANTS = new ArrayList<>();

	public static final DeferredRegister<DeferredCoolant<?>> REGISTRY = DeferredRegister.create(FMRegistries.DEFERRED_COOLANT, FluxMachines.MODID);

	/*public static final DeferredCoolant<FlowingFluid> WATER = create("water", Fluids.WATER, new ItemStack(Blocks.PACKED_ICE), 0.5, IReactorCoolant::new);

	private static <F extends Fluid> DeferredCoolant<F> create (String id, F fluid, ItemStack itemStack, double efficiency, CoolantFactory<F> factory) {
		var deferred = REGISTRY.register(id, () -> factory.create(fluid, itemStack.getItem(), efficiency));
		var supplier = new DeferredCoolant<>(deferred);
		COOLANTS.add(supplier);
		return supplier;
	}*/

	@FunctionalInterface
	public interface CoolantFactory<F extends Fluid> {
		IReactorCoolant<F> create (Fluid fluid, Item itemStack, double efficiency);
	}

}
