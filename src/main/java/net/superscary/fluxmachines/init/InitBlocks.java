package net.superscary.fluxmachines.init;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.superscary.fluxmachines.registries.FMBlocks;
import net.superscary.fluxmachines.util.block.BlockDefinition;

public class InitBlocks {

    private InitBlocks () {

    }

    public static void init (Registry<Block> registry) {
        for (BlockDefinition<?> definition : FMBlocks.getBlocks()) {
            Registry.register(registry, definition.id(), definition.block());
        }
    }

}