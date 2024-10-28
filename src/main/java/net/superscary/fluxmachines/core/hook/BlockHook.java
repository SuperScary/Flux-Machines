package net.superscary.fluxmachines.core.hook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;

public class BlockHook {

    public static void place (BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            var level = event.getLevel();
            var state = event.getPlacedBlock();
            var block = state.getBlock();
            place(level, player, block, state, event.getPos());
        }
    }

    private static void place (LevelAccessor level, Player player, Block block, BlockState state, BlockPos pos) {
        var heldstack = player.getMainHandItem();
        if (block instanceof FMBaseEntityBlock<?> baseEntityBlock) {
            if (baseEntityBlock.getBlockEntity(level, pos) instanceof FMBasePoweredBlockEntity entity) {
                entity.setData(heldstack);
            }
        }
    }

}
