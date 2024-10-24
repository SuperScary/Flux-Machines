package net.superscary.fluxmachines.hook;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.superscary.fluxmachines.registries.FMItems;

// TODO: Redstone signal not working on right click
public class RedstoneHook {

    public static void onPlayerUseBlockEvent (PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled()) return;
        onPlayerUseBlock(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
    }

    public static InteractionResult onPlayerUseBlock (Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isSpectator() || hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        var itemStack = player.getItemInHand(hand);
        var blockpos = hitResult.getBlockPos();

        if (level.getBlockState(hitResult.getBlockPos()).getBlock() instanceof RedStoneWireBlock block && itemStack.is(FMItems.REDSTONE_AND_STEEL.asItem())) {
            var state = level.getBlockState(hitResult.getBlockPos());
            if (state.hasProperty(BlockStateProperties.POWER)) {
                state.setValue(BlockStateProperties.POWER, 15);
                level.updateNeighborsAt(blockpos, block);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

}
