package net.superscary.fluxmachines.core.block.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.superscary.fluxmachines.core.registries.FMBlocks;

import java.util.Objects;

public class ReactorHeatingLaserMultiblock {

	private static final BlockPattern PATTERN = BlockPatternBuilder.start()
			.aisle("S S",
					"SSS",
					"SSS",
					"SSS",
					"SSS",
					"SSS")
			.aisle("   ",
					"SLS",
					"L L",
					"L L",
					"L L",
					"SSS")
			.aisle("   ",
					" S ",
					" S ",
					" S ",
					" S ",
					" S ")
			.where('S', BlockInWorld.hasState(s -> s.is(FMBlocks.LASER_FRAME.block())))
			.where('L', BlockInWorld.hasState(s -> s.is(FMBlocks.LASER_LENS.block())))
			.where(' ', BlockInWorld.hasState(Objects::nonNull))
			.build();

	public static boolean isValid (Level level, BlockPos pos) {
		return PATTERN.find(level, pos) != null;
	}

}
