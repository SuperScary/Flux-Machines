package net.superscary.fluxmachines.core.block.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static net.superscary.fluxmachines.core.util.block.FMBlockStates.FLUID_PORT_INPUT;

public class ReactorFluidPortBlock extends ReactorBlock {

	public ReactorFluidPortBlock (Properties properties) {
		super(properties);
	}

	public ReactorFluidPortBlock () {
		this(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.0F, 6.0F).requiresCorrectToolForDrops());
	}

	@Override
	protected @NotNull ItemInteractionResult useItemOn (@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,  @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (!level.isClientSide) {
			boolean newVal = !state.getValue(FLUID_PORT_INPUT);
			level.setBlock(pos, state.setValue(FLUID_PORT_INPUT, newVal), 3);
			level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 1.0f, 1.0f);
		}

		if (level.isClientSide) {
			boolean current = state.getValue(FLUID_PORT_INPUT);
			player.displayClientMessage(Component.literal("Fluid Port Input: " + !current), false);
		}

		return ItemInteractionResult.sidedSuccess(level.isClientSide);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement (BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FLUID_PORT_INPUT, false);
	}

	@Override
	protected void createBlockStateDefinition (StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FLUID_PORT_INPUT);
	}

}
