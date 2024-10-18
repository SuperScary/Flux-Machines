package net.superscary.fluxmachines.blockentity.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.fluxmachines.blockentity.base.FMBaseBlockEntity;

public class MachineCasingBlockEntity extends FMBaseBlockEntity {

    public MachineCasingBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

}
