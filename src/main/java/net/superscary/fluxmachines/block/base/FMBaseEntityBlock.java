package net.superscary.fluxmachines.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.fluxmachines.blockentity.base.FMBaseBlockEntity;

import javax.annotation.Nullable;

public abstract class FMBaseEntityBlock<T extends FMBaseBlockEntity> extends BaseBlock implements EntityBlock {

    private Class<T> blockEntityClass;
    private BlockEntityType<T> blockEntityType;

    public FMBaseEntityBlock (Properties properties) {
        super(properties);
    }

    public void setBlockEntity (Class<T> blockEntityClass, BlockEntityType<T> blockEntityType) {
        this.blockEntityClass = blockEntityClass;
        this.blockEntityType = blockEntityType;
    }

    @Nullable
    public T getBlockEntity (BlockGetter level, int x, int y, int z) {
        return this.getBlockEntity(level, new BlockPos(x, y, z));
    }

    @Nullable
    public T getBlockEntity (BlockGetter level, BlockPos pos) {
        final BlockEntity te = level.getBlockEntity(pos);
        // FIXME: This gets called as part of building the block state cache
        if (this.blockEntityClass != null && this.blockEntityClass.isInstance(te)) {
            return this.blockEntityClass.cast(te);
        }

        return null;
    }

    public BlockEntityType<T> getBlockEntityType () {
        return blockEntityType;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos pos, BlockState state) {
        return blockEntityType.create(pos, state);
    }

}
