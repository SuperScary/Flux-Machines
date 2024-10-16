package net.superscary.fluxmachines.api.orientation;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class HorizontalFacingStrategy extends FacingStrategy {
    protected HorizontalFacingStrategy () {
        super(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement (BlockState state, BlockPlaceContext context) {
        return setFacing(state, context.getHorizontalDirection().getOpposite());
    }
}