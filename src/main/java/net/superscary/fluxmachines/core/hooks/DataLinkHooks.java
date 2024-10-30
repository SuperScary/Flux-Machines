package net.superscary.fluxmachines.core.hooks;

import com.google.common.base.Preconditions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.superscary.fluxmachines.api.data.DataLinkInteract;
import net.superscary.fluxmachines.core.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.core.item.tool.DataLinkTool;
import net.superscary.fluxmachines.core.registries.FMItems;
import net.superscary.fluxmachines.core.util.helper.ItemHelper;
import net.superscary.fluxmachines.core.util.helper.SoundHelper;
import net.superscary.fluxmachines.core.util.tags.FMTag;

import static net.superscary.fluxmachines.core.util.helper.SoundHelper.Sounds.*;

public class DataLinkHooks {

    private static final ThreadLocal<Boolean> IS_COPYING_OR_WRITING = new ThreadLocal<>();

    private DataLinkHooks () {

    }

    public static boolean isCopyingOrWriting () {
        return Boolean.TRUE.equals(IS_COPYING_OR_WRITING.get());
    }

    public static void onPlayerUseBlockEvent (PlayerInteractEvent.RightClickBlock event) {
        Preconditions.checkArgument(!isCopyingOrWriting());
        if (event.isCanceled()) return;
        var result = onPlayerUseBlock(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
        if (result != InteractionResult.PASS) {
            event.setCanceled(true);
            event.setCancellationResult(result);
        }
    }

    public static InteractionResult onPlayerUseBlock (Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isSpectator() || hand != InteractionHand.MAIN_HAND || level.isClientSide()) return InteractionResult.PASS;
        if (player.getItemInHand(hand).getItem() instanceof DataLinkTool dataLinkTool) {
            var item = dataLinkTool;
            // write data to tool
            if (alternateUseMode(player)) {
                var be = level.getBlockEntity(hitResult.getBlockPos());
                if (be instanceof DataLinkInteract baseBlockEntity) {
                    IS_COPYING_OR_WRITING.set(true);
                    try {
                        item.clearLinkedData();
                        item.writeLinkedData(baseBlockEntity.getLinkedData());
                        SoundHelper.fire(level, player, hitResult.getBlockPos(), COPY_DATA);
                        player.sendSystemMessage(Component.translatable("message.fluxmachines.data_link_tool.copied_data"));
                        return InteractionResult.sidedSuccess(level.isClientSide());
                    } finally {
                        IS_COPYING_OR_WRITING.remove();
                    }
                }
            }
            // read data from tool
            else {
                var be = level.getBlockEntity(hitResult.getBlockPos());
                if (be instanceof DataLinkInteract) {
                    IS_COPYING_OR_WRITING.set(true);
                    try {
                        var data = ((DataLinkInteract) be).getLinkedData();
                        if (!data.isEmpty()) {
                            data.addAll(item.getLinkedData());
                            player.sendSystemMessage(Component.translatable("message.fluxmachines.data_link_tool.wrote_data"));
                            SoundHelper.fire(level, player, hitResult.getBlockPos(), WRITE_DATA);
                        } else {
                            player.sendSystemMessage(Component.translatable("message.fluxmachines.data_link_tool.empty_data"));
                        }

                        return InteractionResult.sidedSuccess(level.isClientSide());
                    } finally {
                        IS_COPYING_OR_WRITING.remove();
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    public static boolean alternateUseMode (Player player) {
        return player.isShiftKeyDown();
    }

}
