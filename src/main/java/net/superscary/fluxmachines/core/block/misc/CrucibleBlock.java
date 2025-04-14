package net.superscary.fluxmachines.core.block.misc;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.superscary.fluxmachines.core.block.base.BaseBlock;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.blockentity.misc.CrucibleBlockEntity;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.util.helper.ItemHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrucibleBlock extends FMBaseEntityBlock<CrucibleBlockEntity> {

	public CrucibleBlock () {
		super(FMBlocks.MACHINE_CASING.block().properties());
	}

	public CrucibleBlock (Properties properties) {
		this();
	}

	@Override
	public MapCodec<BaseBlock> getCodec () {
		return simpleCodec(CrucibleBlock::new);
	}

	@Override
	protected @NotNull VoxelShape getShape (@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return makeShape();
	}

	@Override
	protected @NotNull ItemInteractionResult useItemOn (ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
		var entity = level.getBlockEntity(pos);
		if (entity instanceof CrucibleBlockEntity blockEntity && hitResult.getDirection() == Direction.UP) {
			if (player.getItemInHand(hand).getCapability(Capabilities.FluidHandler.ITEM) != null) {
				return fillTank(blockEntity, player, hand);
			} else {
				return putItemInCrucible(blockEntity, player, hand);
			}
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	private ItemInteractionResult putItemInCrucible (CrucibleBlockEntity entity, Player player, InteractionHand hand) {
		var stack = player.getItemInHand(hand);
		if (!player.isCrouching()) {
			for (int i = 0; i < entity.getInventory().getSlots() - 1; i++) {
				if (entity.getInventory().canInsert(i, stack)) {
					handleItemInput(entity, player, stack, i);
					return ItemInteractionResult.SUCCESS;
				}
			}
		} else {
			for (int i = 0; i < entity.getInventory().getSlots(); i++) {
				entity.getInventory().extractItem(i, entity.getInventory().getStackInSlot(i).getCount(), false);
			}
			return ItemInteractionResult.SUCCESS;
		}

		return ItemInteractionResult.CONSUME;
	}

	private void handleItemInput (CrucibleBlockEntity entity, Player player, ItemStack stack, int slot) {
		ItemStack s0 = entity.getInventory().getStackInSlot(slot);

		int amount = 0;
		int remainder;
		int math = maxAdd(s0, stack, s0.getMaxStackSize());
		if (math <= -1) {
			amount = 64;
			remainder = math * -1;
			player.getInventory().setItem(player.getInventory().selected, new ItemStack(stack.getItem(), remainder));
		}

		if (math == 0) {
			amount = 64;
		}

		if (math >= 1) {
			amount = math;
		}

		entity.getInventory().setStackInSlot(slot, new ItemStack(stack.getItem(), amount));
		if (!player.isCreative()) player.getInventory().setItem(player.getInventory().selected, ItemStack.EMPTY);
	}

	/**
	 * Returns either the combined size of the two stacks, or the difference from maxCount.
	 *
	 * @param s0       first stack
	 * @param s1       second stack
	 * @param maxCount max stack count
	 * @return max amount to add
	 */
	private int maxAdd (ItemStack s0, ItemStack s1, int maxCount) {
		int v = s0.getCount();
		int h = s1.getCount();
		if (h + v <= maxCount) return v + h;
		else {
			return maxCount - (h + v);
		}
	}

	/**
	 * Fills the fluid tank or pulls fluid from the tank and puts it in a compatible fluid handler item on right click.
	 * TODO: This is a very ugly method.
	 *
	 * @return result.
	 */
	private ItemInteractionResult fillTank (CrucibleBlockEntity entity, Player player, InteractionHand hand) {
		var heldStack = player.getItemInHand(hand);
		var optional = heldStack.getCapability(Capabilities.FluidHandler.ITEM);

		if (optional != null) {
			var tank = entity.getTank(null);
			var contained = optional.drain(1000, IFluidHandler.FluidAction.SIMULATE);

			if (!contained.isEmpty()) {
				if (tank.isEmpty() || tank.getFluid().getFluid().isSame(contained.getFluid())) {
					var fillable = tank.fill(contained, IFluidHandler.FluidAction.SIMULATE);

					if (fillable >= contained.getAmount()) {
						FluidUtil.tryFluidTransfer(tank, optional, contained.getAmount(), true);

						if (player.isCreative()) return ItemInteractionResult.SUCCESS;
						player.setItemInHand(hand, optional.getContainer());
						return ItemInteractionResult.SUCCESS;
					}
				}
			} else {
				var tankFluid = tank.drain(1000, IFluidHandler.FluidAction.SIMULATE);

				if (!tankFluid.isEmpty()) {
					if (tank.getFluid().getAmount() >= optional.getFluidInTank(0).getAmount()) {
						optional.fill(tankFluid, IFluidHandler.FluidAction.EXECUTE);
						tankFluid = tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);

						var fluidType = tankFluid.getFluidType();
						ItemHelper.giveOrDropBucket(player, fluidType.getBucket(tankFluid));

						return ItemInteractionResult.SUCCESS;
					}
				}
			}
		}

		return ItemInteractionResult.CONSUME;
	}

	@Override
	protected void tick (@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(state, level, pos, random);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker (@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
		return ((lvl, pos, blockState, type) -> {
			if (type instanceof CrucibleBlockEntity)
				((CrucibleBlockEntity) type).tick(lvl, pos, blockState);
		});
	}

	@Override
	protected int getSignal (@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
		return 0;
	}

	@Override
	protected int getDirectSignal (@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
		return 0;
	}

	@Override
	protected boolean isSignalSource (@NotNull BlockState state) {
		return false;
	}

	public VoxelShape makeShape () {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.125, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.875, 0, 0, 1, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0, 0.875, 1, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.875, 0.875, 1, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.125, 0.875), BooleanOp.OR);

		return shape;
	}

	public static int getArea (Direction facing, Vec3 hit) {
		var x = getXFromHit(facing, hit);
		var y = getYFromHit(facing, hit);

		int area = 0;
		if (x < .5 && y > .5) {
			area = 1;
		} else if (x > .5 && y < .5) {
			area = 2;
		} else if (x > .5 && y > .5) {
			area = 3;
		}
		return area;
	}

	private static double getYFromHit (Direction facing, Vec3 hit) {
		return switch (facing) {
			case UP, DOWN, NORTH -> 1 - hit.x;
			case SOUTH -> hit.x;
			case WEST -> hit.z;
			case EAST -> 1 - hit.z;
		};
	}

	private static double getXFromHit (Direction facing, Vec3 hit) {
		return switch (facing) {
			case UP -> hit.z;
			case DOWN -> 1 - hit.z;
			case NORTH, WEST, EAST, SOUTH -> hit.y;
		};
	}

}
