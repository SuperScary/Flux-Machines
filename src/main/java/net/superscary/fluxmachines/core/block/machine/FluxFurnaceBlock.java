package net.superscary.fluxmachines.core.block.machine;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.superscary.fluxmachines.core.block.base.BaseBlock;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.blockentity.machine.FluxFurnaceBlockEntity;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluxFurnaceBlock extends FMBaseEntityBlock<FluxFurnaceBlockEntity> {

    public FluxFurnaceBlock () {
        super(FMBlocks.MACHINE_CASING.block().properties());
    }

    public FluxFurnaceBlock (Properties properties) {
        this();
    }

    @Override
    public MapCodec<BaseBlock> getCodec () {
        return simpleCodec(FluxFurnaceBlock::new);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn (ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof FluxFurnaceBlockEntity blockEntity) {
                player.openMenu(new SimpleMenuProvider(blockEntity, Component.translatable("block.fluxmachines.flux_furnace")), pos);
            } else {
                throw new IllegalStateException("Container provider is missing.");
            }
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void tick (@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.tick(state, level, pos, random);
        var entity = getBlockEntity(level, pos);
        if (entity != null) {
            entity.getEnergyStorage().receiveEnergy(1, false);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker (@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return ((level1, blockPos, blockState, t) -> {
            if (t instanceof FMBasePoweredBlockEntity)
                ((FMBasePoweredBlockEntity) t).tick(level1, blockPos, blockState);
        });
    }

    @Override
    public int getLightEmission (BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.getValue(BlockStateProperties.POWERED) ? 10 : 0;
    }

    @Override
    public boolean hasDynamicLightEmission (@NotNull BlockState state) {
        return true;
    }

    @Override
    protected boolean useShapeForLightOcclusion (@NotNull BlockState state) {
        return true;
    }

    @Override
    public void animateTick (BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.getValue(BlockStateProperties.POWERED)) {
            return;
        }

        double xPos = (double) pos.getX() + 0.5;
        double yPos = pos.getY();
        double zPos = (double) pos.getZ() + 0.5;

        if (random.nextDouble() < 0.1) {
            level.playLocalSound(xPos, yPos, zPos, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
        }

        Direction direction = state.getValue(BlockStateProperties.FACING);
        Direction.Axis axis = direction.getAxis();

        double defaultOffset = random.nextDouble() * 0.6 - 0.3;
        double xOffsets = axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52 : defaultOffset;
        double yOffsets = random.nextDouble() * 6.0 / 8.0;
        double zOffsets = axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52 : defaultOffset;

        if (level.getBlockEntity(pos) instanceof FluxFurnaceBlockEntity entity && !entity.getInventory().getStackInSlot(0).isEmpty()) {
            var stack = entity.getInventory().getStackInSlot(0);
            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), xPos + xOffsets, yPos + yOffsets, zPos + zOffsets, 0d, 0d, 0d);
        }

    }

}
