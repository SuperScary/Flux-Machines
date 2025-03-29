package net.superscary.fluxmachines.core.block.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.superscary.fluxmachines.core.blockentity.cable.FacadeBlockEntity;
import net.superscary.fluxmachines.core.item.FacadeBlockItem;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FacadeBlock extends CableBlock {

    public FacadeBlock() {
        super();
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (world.getBlockEntity(pos) instanceof FacadeBlockEntity facade) {
            BlockState mimicBlock = facade.getMimicBlock();
            if (mimicBlock != null) {
                return mimicBlock.getShape(world, pos, context);
            }
        }
        return super.getShape(state, world, pos, context);
    }

    // This function is called when the facade block is successfully harvested by the player
    // When the player destroys the facade we need to drop the facade block item with the correct mimicked block
    @Override
    public void playerDestroy(@Nonnull Level level, @Nonnull Player player, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable BlockEntity te, @Nonnull ItemStack stack) {
        ItemStack item = new ItemStack(FMBlocks.FACADE.block());
        BlockState mimicBlock;
        if (te instanceof FacadeBlockEntity) {
            mimicBlock = ((FacadeBlockEntity) te).getMimicBlock();
        } else {
            mimicBlock = Blocks.COBBLESTONE.defaultBlockState();
        }
        FacadeBlockItem.setMimicBlock(item, mimicBlock);
        popResource(level, pos, item);
    }

    // When the player destroys the facade we need to restore the cable block
    @Override
    public boolean onDestroyedByPlayer(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, boolean willHarvest, @NotNull FluidState fluid) {
        BlockState defaultState = FMBlocks.CABLE.block().defaultBlockState();
        BlockState newState = CableBlock.calculateState(world, pos, defaultState);
        return world.setBlock(pos, newState, world.isClientSide()
                ? Block.UPDATE_ALL + Block.UPDATE_IMMEDIATE
                : Block.UPDATE_ALL);
    }

}
