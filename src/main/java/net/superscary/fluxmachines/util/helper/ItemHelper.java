package net.superscary.fluxmachines.util.helper;

import net.minecraft.world.item.ItemStack;

public class ItemHelper {

    public static void damageStack (ItemStack stack) {
        damageStack(stack, 1);
    }

    public static void damageStack (ItemStack stack, int amount) {
        var item = stack.getItem();
        if (item.isDamageable(stack)) {
            item.setDamage(stack, item.getDamage(stack) + amount);
        }
    }

}
