package net.superscary.fluxmachines.core.block.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.superscary.fluxmachines.core.registries.FMBlocks;

public class CokeOvenMultiblock {

	public static final BlockPattern PATTERN = BlockPatternBuilder.start()
			.aisle("SSS",
					"SSS",
					"SSS")
			.aisle("SSS",
					"SSS",
					"SSS")
			.aisle("SSS",
					"SSS",
					"SSS")
			.where('S', BlockInWorld.hasState(s -> s.getBlock() == FMBlocks.REFRACTORY_BRICK.block()))
			.build();

	public static boolean isValid (Level level, BlockPos pos) {
		return PATTERN.find(level, pos) != null;
	}

}
