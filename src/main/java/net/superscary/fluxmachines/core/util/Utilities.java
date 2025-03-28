package net.superscary.fluxmachines.core.util;

import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.loading.FMLEnvironment;

public class Utilities {

    public static boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }

    public static boolean alternateUseMode (Player player) {
        return player.isShiftKeyDown();
    }

}
