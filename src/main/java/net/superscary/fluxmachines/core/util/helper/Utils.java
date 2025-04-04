package net.superscary.fluxmachines.core.util.helper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.superscary.fluxmachines.core.FluxMachines;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

    public static <T> T assignIfNotNull(T value) {
        if (value != null) {
            return value;
        }

        FluxMachines.LOGGER.warn("Value of {} is null. Cannot be assigned,", value);
        return null;
    }

    public static <T> List<T> assignIfNotNull(List<T> value) {
        if (value != null) {
            return value;
        }

        FluxMachines.LOGGER.warn("Value of {} is null. Cannot be assigned,", value);
        return null;
    }

    public static ItemStack convertToItemStack (Ingredient from) {
        return Arrays.stream(from.getItems()).findFirst().get();
    }

    public static int randomInteger (int base, int max) {
        Random random = new Random();
        return random.nextInt(base, max);
    }

}
