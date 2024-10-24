package net.superscary.fluxmachines.hook;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.superscary.fluxmachines.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.util.helper.ItemHelper;
import net.superscary.fluxmachines.util.tags.FMTag;

public class WrenchHook {

    private static final ThreadLocal<Boolean> IS_DISASSEMBLING = new ThreadLocal<>();

    private WrenchHook () {

    }

    public static boolean isDisassembling () {
        return Boolean.TRUE.equals(IS_DISASSEMBLING.get());
    }

    public static void onPlayerUseBlockEvent (PlayerInteractEvent.RightClickBlock event) {
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
                    var result = baseBlockEntity.disassemble(player, level, hitResult, itemStack);
                    if (result.consumesAction()) {
                        SoundEvent sound = SoundEvents.ANVIL_HIT;
                        level.playSound(player, hitResult.getBlockPos(), sound, SoundSource.BLOCKS, 0.7f, 1.0f);
                        if (itemStack.is(FMTag.Items.WRENCH)) {
                            ItemHelper.damageStack(itemStack);
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
                    ItemHelper.damageStack(itemStack);
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
