package net.superscary.fluxmachines.core.blockentity.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.fluxmachines.api.inventory.MachineItemStackHandler;
import net.superscary.fluxmachines.attributes.Attribute;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.util.keys.Keys;
import org.jetbrains.annotations.NotNull;

public class ReactorPowerTapBlockEntity extends FMBasePoweredBlockEntity {

	public ReactorPowerTapBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState, Attribute.Builder.of(Keys.MAX_POWER, 20_000_000), Attribute.Builder.of(Keys.POWER, 0));
	}

	@Override
	public MachineItemStackHandler createInventory () {
		return MachineItemStackHandler.EMPTY;
	}

	@Override
	public AbstractContainerMenu menu (int id, Inventory playerInventory, Player player) {
		return null;
	}

	@Override
	public void tick (Level level, BlockPos pos, BlockState state) {

	}

	@Override
	public @NotNull Component getDisplayName () {
		return Component.translatable("block.fluxmachines.reactor_power_tap");
	}

}