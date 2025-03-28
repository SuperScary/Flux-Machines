package net.superscary.fluxmachines.core.util.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class PlayerHelper {

    public static Vec3 clipWithDistance (Player player, Level level, double clipDistance) {
        double vecX = Math.sin(-player.getYRot() * (Math.PI / 180)) * Math.cos(-player.getXRot() * (Math.PI / 180));
        double vecY = Math.sin(-player.getXRot() * (Math.PI / 180));
        double vecZ = Math.cos(-player.getYRot() * (Math.PI / 180)) * Math.cos(-player.getXRot() * (Math.PI / 180));
        return level.clip(new ClipContext(player.getEyePosition(1.0F), player.getEyePosition(1.0F).add(vecX * clipDistance, vecY * clipDistance, vecZ * clipDistance), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player)).getLocation();
    }

    public static BlockPos clip (Player player, Level level) {
        var result = clipWithDistance(player, level, 5);
        return BlockPos.containing(result);
    }

    public static Block getBlockLookingAt (Player player, Level level) {
        var result = clipWithDistance(player, level, 5);
        return level.getBlockState(BlockPos.containing(result)).getBlock();
    }

}
