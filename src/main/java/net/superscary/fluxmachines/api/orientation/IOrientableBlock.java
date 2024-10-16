package net.superscary.fluxmachines.api.orientation;

import net.minecraft.world.level.block.state.BlockState;

public interface IOrientableBlock {

    IOrientationStrategy getOrientationStrategy ();

    default BlockOrientation getOrientation (BlockState state) {
        var strategy = getOrientationStrategy();
        var facing = strategy.getFacing(state);
        var spin = strategy.getSpin(state);
        return BlockOrientation.get(facing, spin);
    }

}
