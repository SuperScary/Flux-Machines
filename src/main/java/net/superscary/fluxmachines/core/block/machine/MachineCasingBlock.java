package net.superscary.fluxmachines.core.block.machine;

import net.minecraft.world.level.block.Blocks;
import net.superscary.fluxmachines.core.block.base.DecorativeBlock;

public class MachineCasingBlock extends DecorativeBlock {

    public MachineCasingBlock (Properties properties) {
        super(properties.noOcclusion());
    }

    public MachineCasingBlock () {
        this(Blocks.IRON_BLOCK.properties());
    }

}
