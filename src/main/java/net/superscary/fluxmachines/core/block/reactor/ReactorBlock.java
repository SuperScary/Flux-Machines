package net.superscary.fluxmachines.core.block.reactor;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.superscary.fluxmachines.core.block.base.BaseBlock;

public class ReactorBlock extends BaseBlock {

	/**
	 * Constructor for ReactorBlock.
	 *
	 * @param properties The properties of the block.
	 */
	public ReactorBlock (Properties properties) {
		super(properties);
	}

	public ReactorBlock () {
		this(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.0F, 6.0F).requiresCorrectToolForDrops());
	}

}
