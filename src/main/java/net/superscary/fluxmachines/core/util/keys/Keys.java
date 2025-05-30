package net.superscary.fluxmachines.core.util.keys;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.superscary.fluxmachines.core.FluxMachines;

public class Keys {

    public static final ResourceKey<CreativeModeTab> MAIN = create(Registries.CREATIVE_MODE_TAB, "main");
    public static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(Registries.PROCESSOR_LIST, FluxMachines.getMinecraftResource("empty"));

    public static final String INVENTORY = "inventory";
    public static final String INVENTORY_UPGRADE = "inventory_upgrade";
    public static final String POWER = "energy";
    public static final String MAX_POWER = "max_energy";
    public static final String MAX_DRAIN = "max_energy_drain";
    public static final String FLUID = "fluid";
    public static final String MAX_PROGRESS = "max_progress";
    public static final String RF_AMOUNT = "rf_amount";
    public static final String PROGRESS = "progress";
    public static final String CRAFTING = "crafting";

    private Keys () {

    }

    public static <T> ResourceKey<T> create (ResourceKey<? extends Registry<T>> registryKey, String path) {
        return ResourceKey.create(registryKey, FluxMachines.getResource(path));
    }

}
