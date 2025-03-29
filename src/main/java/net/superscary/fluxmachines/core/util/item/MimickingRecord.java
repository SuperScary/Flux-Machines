package net.superscary.fluxmachines.core.util.item;

import net.minecraft.world.level.block.Block;

/**
 * Used for {@link net.superscary.fluxmachines.core.block.cable.CableBlock} to set data to the {@link net.minecraft.world.item.ItemStack}
 * @param isMimicking bool true or false
 * @param block the block to copy
 */
public record MimickingRecord(boolean isMimicking, Block block) {
}
