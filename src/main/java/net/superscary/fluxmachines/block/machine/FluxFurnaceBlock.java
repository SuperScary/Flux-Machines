package net.superscary.fluxmachines.block.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.superscary.fluxmachines.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.blockentity.machine.FluxFurnaceBlockEntity;
import net.superscary.fluxmachines.registries.FMBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluxFurnaceBlock extends FMBaseEntityBlock<FluxFurnaceBlockEntity> {

    public FluxFurnaceBlock () {
        super(FMBlocks.MACHINE_CASING.block().properties());
    }

    @Override
    protected ItemInteractionResult useItemOn (ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof FluxFurnaceBlockEntity blockEntity) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(blockEntity, Component.translatable("block.fluxmachines.flux_furnace")), pos);
            } else {
                throw new IllegalStateException("Container provider is missing.");
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void tick (BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        var entity = getBlockEntity(level, pos);
        if (entity != null) {
            entity.getEnergyStorage().receiveEnergy(1, false);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return ((level1, blockPos, blockState, t) -> {
           if (t instanceof FMBasePoweredBlockEntity) ((FMBasePoweredBlockEntity) t).tick(level1, blockPos, blockState);
        });
    }

    @Override
    public void animateTick (BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource randomSource) {
        if (state.getValue(BlockStateProperties.POWERED)) {
            level.playLocalSound(pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1f, 1f, false);
        }
        super.animateTick(state, level, pos, randomSource);
    }

}
