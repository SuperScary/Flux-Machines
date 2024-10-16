package net.superscary.fluxmachines.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.superscary.fluxmachines.core.FluxMachines;

public class CreativeKeys {

    public static final ResourceKey<CreativeModeTab> MAIN = create("main");

    private CreativeKeys () {

    }

    private static ResourceKey<CreativeModeTab> create (String path) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, FluxMachines.getResource(path));
    }

}
