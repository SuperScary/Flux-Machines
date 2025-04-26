package net.superscary.fluxmachines.core.block.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.superscary.fluxmachines.core.block.reactor.ReactorCoreBlock;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.util.tags.FMTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Multiblock {

	public static class CokeOven {

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

	public static class ReactorHeatingLaser {

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

	public static class Reactor {

		private static final BlockPattern PATTERN = BlockPatternBuilder.start()
				.aisle("  S  ",
					   " SFS ",
					   "SFFFS",
					   " SFS ",
					   "  S  ")
				.aisle(" SFS ",
						"S   S",
						"F   F",
						"S   S",
						" SFS ")
				.aisle("SFFFS",
						"F   F",
						"F   F",
						"F   F",
						"SFFFS")
				.aisle(" SFS ",
						"S   S",
						"F   F",
						"S   S",
						" SFS ")
				.aisle("  S  ",
						" SFS ",
						"SFCFS",
						" SFS ",
						"  S  ")
				.where('S', BlockInWorld.hasState(s -> s.is(FMBlocks.REACTOR_FRAME.block()))) // frame only
				.where('F', BlockInWorld.hasState(s -> s.is(FMTag.Blocks.REACTOR_BLOCK) || s.is(FMTag.Blocks.REACTOR_PART))) // glass or ports
				.where('C', BlockInWorld.hasState(s -> s.is(FMBlocks.REACTOR_CORE.block()))) // core only
				.where(' ', BlockInWorld.hasState(Objects::nonNull))
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

		public static List<BlockPos> findBlocksInPattern (Level level, BlockPos centerPos, Predicate<BlockState> condition) {
			BlockPos origin = centerPos.offset(-2, -4, -2);
			var match = PATTERN.find(level, origin);

			if (match == null) return List.of();

			List<BlockPos> hits = new ArrayList<>();
			for (int x = 0; x < PATTERN.getWidth();  x++) {
				for (int y = 0; y < PATTERN.getHeight(); y++) {
					for (int z = 0; z < PATTERN.getDepth();  z++) {
						var inWorld = match.getBlock(x, y, z);
						if (condition.test(inWorld.getState())) {
							hits.add(inWorld.getPos());
						}
					}
				}
			}
			return hits;
		}

	}
}
