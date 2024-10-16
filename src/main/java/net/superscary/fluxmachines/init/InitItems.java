package net.superscary.fluxmachines.init;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.superscary.fluxmachines.registries.FMBlocks;
import net.superscary.fluxmachines.registries.FMItems;

public class InitItems {

    private InitItems () {

    }

    public static void init (Registry<Item> registry) {
        for (var definition : FMBlocks.getBlocks()) {
            Registry.register(registry, definition.id(), definition.asItem());
        }

        for (var definition : FMItems.getItems()) {
            Registry.register(registry, definition.id(), definition.asItem());
        }
    }

}