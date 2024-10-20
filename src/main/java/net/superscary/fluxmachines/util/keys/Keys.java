package net.superscary.fluxmachines.util.keys;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.superscary.fluxmachines.core.FluxMachines;

public class Keys {

    public static final ResourceKey<CreativeModeTab> MAIN = create(Registries.CREATIVE_MODE_TAB, "main");

    public static final String INVENTORY = "inventory";
    public static final String POWER = "energy";
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
