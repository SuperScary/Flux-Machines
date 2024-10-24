package net.superscary.fluxmachines.block.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.superscary.fluxmachines.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.util.ContentDropper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;

public abstract class FMBaseEntityBlock<T extends FMBaseBlockEntity> extends BaseBlock implements EntityBlock {

    private Class<T> blockEntityClass;
    private BlockEntityType<T> blockEntityType;

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private MapCodec<BaseBlock> codec = getCodec();

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
        if (this.blockEntityClass != null && this.blockEntityClass.isInstance(te)) {
            return this.blockEntityClass.cast(te);
        }

        return null;
    }

    public abstract MapCodec<BaseBlock> getCodec ();

    public BlockEntityType<T> getBlockEntityType () {
        return blockEntityType;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (BlockPos pos, BlockState state) {
        return blockEntityType.create(pos, state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.POWERED, false)
                .setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED, FACING);
    }

    @Override
    protected @NotNull RenderShape getRenderShape (@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove (BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            var blockentity = this.getBlockEntity(level, pos);
            if (blockentity != null) {
                blockentity.drops();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn (ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected MapCodec<? extends Block> codec () {
        return codec;
    }
}
