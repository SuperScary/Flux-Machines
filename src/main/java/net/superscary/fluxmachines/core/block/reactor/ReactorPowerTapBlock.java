package net.superscary.fluxmachines.core.block.reactor;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.superscary.fluxmachines.core.block.base.BaseBlock;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.blockentity.reactor.ReactorPowerTapBlockEntity;

public class ReactorPowerTapBlock extends FMBaseEntityBlock<ReactorPowerTapBlockEntity> {

	public ReactorPowerTapBlock (BlockBehaviour.Properties properties) {
		super(properties);
	}

	public ReactorPowerTapBlock () {
		this(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.0F, 6.0F).requiresCorrectToolForDrops());
	}

	@Override
	public MapCodec<BaseBlock> getCodec () {
		return simpleCodec(ReactorPowerTapBlock::new);
	}

}
