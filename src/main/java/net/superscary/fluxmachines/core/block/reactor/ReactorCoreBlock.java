package net.superscary.fluxmachines.core.block.reactor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.block.base.BaseBlock;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.block.multiblock.ReactorMultiBlock;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.blockentity.reactor.ReactorCoreBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReactorCoreBlock extends FMBaseEntityBlock<ReactorCoreBlockEntity> {

	public ReactorCoreBlock () {
		super(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.0F, 6.0F).requiresCorrectToolForDrops());
	}

	public ReactorCoreBlock (Properties properties) {
		this();
	}

	@Override
	public MapCodec<BaseBlock> getCodec () {
		return simpleCodec(ReactorCoreBlock::new);
	}

	@Override
	protected @NotNull ItemInteractionResult useItemOn (ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
		if (!level.isClientSide()) {
			if (ReactorMultiBlock.isValidAtCore(level, pos)) {
				BlockEntity entity = level.getBlockEntity(pos);
				if (entity instanceof ReactorCoreBlockEntity blockEntity) {
					player.openMenu(new SimpleMenuProvider(blockEntity, Component.translatable("multiblock.fluxmachines.reactor")), pos);
				} else {
					throw new IllegalStateException("Container provider is missing.");
				}
			} else {
				FluxMachines.LOGGER.warn("Invalid reactor multiblock at {}", pos);
				player.sendSystemMessage(Component.translatable("multiblock.fluxmachines.reactor.invalid"));
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected void tick (@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(state, level, pos, random);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker (@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
		return ((lvl, pos, blockState, type) -> {
			if (type instanceof FMBasePoweredBlockEntity)
				((FMBasePoweredBlockEntity) type).tick(lvl, pos, blockState);
		});
	}

}
