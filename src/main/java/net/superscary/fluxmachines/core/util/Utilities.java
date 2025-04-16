package net.superscary.fluxmachines.core.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.loading.FMLEnvironment;

public class Utilities {

    public static boolean isDevEnvironment() {
        return !FMLEnvironment.production;
    }

    public static boolean alternateUseMode (Player player) {
        return player.isShiftKeyDown();
    }

    public static int getGuiScale () {
        return (int) Minecraft.getInstance().getWindow().getGuiScale();
    }

    public static int guiScaleOffset () {
        return switch (getGuiScale()) {
            case 3 -> -27;
            default -> -27;
        };
    }

}
