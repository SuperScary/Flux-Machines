package net.superscary.fluxmachines.core.hooks;

import com.google.common.base.Preconditions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.superscary.fluxmachines.core.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.core.util.helper.ItemHelper;
import net.superscary.fluxmachines.core.util.helper.SoundHelper;
import net.superscary.fluxmachines.core.util.tags.FMTag;

import static net.superscary.fluxmachines.core.util.helper.SoundHelper.Sounds.DECONSTRUCT;
import static net.superscary.fluxmachines.core.util.helper.SoundHelper.Sounds.ROTATE;

public class WrenchHooks {

    private static final ThreadLocal<Boolean> IS_DISASSEMBLING = new ThreadLocal<>();

    private WrenchHooks () {

    }

    public static boolean isDisassembling () {
        return Boolean.TRUE.equals(IS_DISASSEMBLING.get());
    }

    public static void onPlayerUseBlockEvent (PlayerInteractEvent.RightClickBlock event) {
        Preconditions.checkArgument(!isDisassembling());
        if (event.isCanceled()) return;
        var result = onPlayerUseBlock(event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
        if (result != InteractionResult.PASS) {
            event.setCanceled(true);
            event.setCancellationResult(result);
        }
    }

    public static InteractionResult onPlayerUseBlock (Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (player.isSpectator() || hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS;
        var itemStack = player.getItemInHand(hand);

        // disassemble
        if (alternateUseMode(player) && canDisassemble(itemStack)) {
            var be = level.getBlockEntity(hitResult.getBlockPos());
            if (be instanceof FMBaseBlockEntity baseBlockEntity) {
                IS_DISASSEMBLING.set(true);
                try {
                    var result = baseBlockEntity.disassemble(player, level, hitResult, itemStack, null);
                    if (result.consumesAction()) {
                        SoundHelper.fire(level, player, be.getBlockPos(), DECONSTRUCT);
                        if (itemStack.is(FMTag.Items.WRENCH)) {
                            ItemHelper.damageStack(itemStack, level, player);
                        }
                    }
                    return result;
                } finally {
                    IS_DISASSEMBLING.remove();
                }
            }
        }
        // rotate
        else if (!alternateUseMode(player) && canRotate(itemStack)) {
            IS_DISASSEMBLING.set(true);
            try {
                var be = level.getBlockEntity(hitResult.getBlockPos());
                var pos = hitResult.getBlockPos();
                var state = level.getBlockState(pos);
                var clickedFace = hitResult.getDirection();
                if (be instanceof FMBaseBlockEntity baseBlockEntity) {
                    var result = baseBlockEntity.rotateOnAxis(level, hitResult, state, clickedFace);
                    if (result.consumesAction()) {
                        SoundHelper.fire(level, player, pos, ROTATE);
                        ItemHelper.damageStack(itemStack, level, player);
                    }
                    return result;
                }
            } finally {
                IS_DISASSEMBLING.remove();
            }
        }

        return InteractionResult.PASS;
    }

    public static boolean alternateUseMode (Player player) {
        return player.isShiftKeyDown();
    }

    public static boolean canDisassemble (ItemStack tool) {
        return tool.is(FMTag.Items.WRENCH);
    }

    public static boolean canRotate (ItemStack tool) {
        return tool.is(FMTag.Items.WRENCH);
    }

}
