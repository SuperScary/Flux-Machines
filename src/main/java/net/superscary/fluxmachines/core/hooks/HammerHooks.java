package net.superscary.fluxmachines.core.hooks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.superscary.fluxmachines.core.item.tool.SteelTool;
import net.superscary.fluxmachines.core.util.tags.FMTag;

import java.util.HashSet;
import java.util.Set;

public class HammerHooks {

    // thanks CoFH
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    private static final ThreadLocal<Boolean> IS_BREAKING = new ThreadLocal<>();

    public HammerHooks () {

    }

    public static boolean isBreaking () {
        return Boolean.TRUE.equals(IS_BREAKING.get());
    }

    public static void onPlayerUseBlockEvent (BlockEvent.BreakEvent event) {
        onPlayerUseBlock(event.getPlayer(), event.getPlayer().level(), event.getPlayer().getMainHandItem(), event.getPos());
    }

    public static void onPlayerUseBlock (Player player, Level level, ItemStack heldItem, BlockPos blockPos) {
        if (heldItem.is(FMTag.Items.HAMMER) && player instanceof ServerPlayer serverPlayer) {
            var hammer = (SteelTool.Hammer) heldItem.getItem();
            try {
                if (HARVESTED_BLOCKS.contains(blockPos)) {
                    return;
                }

                for (BlockPos pos : SteelTool.Hammer.getBlocksToBeDestroyed(1, blockPos, serverPlayer)) {
                    IS_BREAKING.set(Boolean.TRUE);
                    if (pos == blockPos || !hammer.isCorrectToolForDrops(heldItem, level.getBlockState(pos))) {
                        continue;
                    }

                    HARVESTED_BLOCKS.add(pos);
                    serverPlayer.gameMode.destroyBlock(pos);
                    HARVESTED_BLOCKS.remove(pos);
                    IS_BREAKING.set(Boolean.FALSE);
                }
            } finally {
                IS_BREAKING.remove();
            }
        }
    }

}
