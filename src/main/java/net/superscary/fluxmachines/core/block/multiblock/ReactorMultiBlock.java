package net.superscary.fluxmachines.core.block.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.superscary.fluxmachines.core.block.reactor.ReactorCoreBlock;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.util.tags.FMTag;

public class ReactorMultiBlock {

	private static final BlockPattern PATTERN = BlockPatternBuilder.start()
			.aisle("  S  ",
				   " SSS ",
			       "SSSSS",
				   " SSS ",
				   "  S  ")
			.aisle(" SSS ",
					"S   S",
					"S   S",
					"S   S",
					" SSS ")
			.aisle("SSSSS",
					"S   S",
					"S   S",
					"S   S",
					"SSSSS")
			.aisle(" SSS ",
					"S   S",
					"S   S",
					"S   S",
					" SSS ")
			.aisle("  S  ",
					" SSS ",
					"SSCSS",
					" SSS ",
					"  S  ")
			.where('S', BlockInWorld.hasState(s -> s.is(FMTag.Blocks.REACTOR_BLOCK)))
			.where('C', BlockInWorld.hasState(s -> s.is(FMBlocks.REACTOR_CORE.block())))
			.where(' ', BlockInWorld.hasState(BlockBehaviour.BlockStateBase::isAir))
			.build();

	public static boolean isValid (Level level, BlockPos pos) {
		return PATTERN.find(level, pos) != null;
	}

	public static boolean isValidAtCore (Level level, BlockPos corePos) {
		BlockPos origin = corePos.offset(-2, -4, -2);
		return PATTERN.find(level, origin) != null;
	}

	public static ReactorCoreBlock getCore (Level level, BlockPos centerPos) {
		BlockPos origin = centerPos.offset(-2, -4, -2);

		var match = PATTERN.find(level, origin);
		if (match == null) return null;

		BlockInWorld coreHit = match.getBlock(2, 4, 2);

		if (coreHit.getState().getBlock() instanceof ReactorCoreBlock core) {
			return core;
		}
		return null;
	}

	public static BlockPos getCorePos (Level level, BlockPos pos) {
		BlockPos origin = pos.offset(-2, -4, -2);

		var match = PATTERN.find(level, origin);
		if (match == null) return null;

		BlockInWorld coreMatch = match.getBlock(2, 2, 4);
		return coreMatch.getPos();
	}

}
