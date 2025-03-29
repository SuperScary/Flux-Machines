package net.superscary.fluxmachines.core.registries;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.components.InventoryComponent;
import net.superscary.fluxmachines.core.util.Codecs;
import net.superscary.fluxmachines.core.util.item.Mimicking;

import java.util.function.UnaryOperator;

public class FMDataComponents {

    public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, FluxMachines.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY_STORED = register("energy_stored", builder -> builder.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY_MAX = register("energy_max", builder -> builder.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<InventoryComponent>> INVENTORY = register("inventory_comp", builder -> builder.persistent(InventoryComponent.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Mimicking>> MIMICKING = register("mimicking", builder -> builder.persistent(Codecs.MIMICKING_CODEC));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register (String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return REGISTRY.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }

}
