package net.superscary.fluxmachines.core.util.helper;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemHelper {

    public static void damageStack (ItemStack stack, Level level, Player player) {
        damageStack(stack, 1, level, player);
    }

    public static void damageStack (ItemStack stack, int amount, Level level, Player player) {
        var item = stack.getItem();
        if (item.isDamageable(stack)) {
            item.setDamage(stack, item.getDamage(stack) + amount);
            if (item.getDamage(stack) >= item.getMaxDamage(stack)) {
                destroy(stack, level, player);
            }
        }
    }

    public static void destroy (ItemStack stack, Level level, Player player) {
        stack.shrink(1);
        SoundHelper.fire(level, player, player.blockPosition(), SoundHelper.Sounds.BREAK);
    }

}
