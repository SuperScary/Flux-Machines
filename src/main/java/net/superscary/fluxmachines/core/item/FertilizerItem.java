package net.superscary.fluxmachines.core.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.EventHooks;
import net.superscary.fluxmachines.core.item.base.BaseItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FertilizerItem extends BaseItem {

    public FertilizerItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var blockpos = context.getClickedPos();
        var blockpos1 = blockpos.relative(context.getClickedFace());
        if (applyBonemeal(context.getItemInHand(), level, blockpos, context.getPlayer())) {
            if (!level.isClientSide) {
                context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                level.levelEvent(1505, blockpos, 15);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            var blockstate = level.getBlockState(blockpos);
            var flag = blockstate.isFaceSturdy(level, blockpos, context.getClickedFace());
            if (flag && growWaterPlant(context.getItemInHand(), level, blockpos1, context.getClickedFace())) {
                if (!level.isClientSide) {
                    context.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                    level.levelEvent(1505, blockpos1, 15);
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static boolean applyBonemeal(ItemStack stack, Level level, BlockPos pos, @Nullable Player player) {
        var blockstate = level.getBlockState(pos);
        var event = EventHooks.fireBonemealEvent(player, level, pos, blockstate, stack);
        if (event.isCanceled()) {
            return event.isSuccessful();
        } else {
            var block = blockstate.getBlock();
            if (block instanceof BonemealableBlock bonemealableblock) {
                if (bonemealableblock.isValidBonemealTarget(level, pos, blockstate)) {
                    if (level instanceof ServerLevel) {
                        if (bonemealableblock.isBonemealSuccess(level, level.random, pos, blockstate)) {
                            bonemealableblock.performBonemeal((ServerLevel)level, level.random, pos, blockstate);
                        }

                        stack.shrink(1);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean growWaterPlant(ItemStack stack, Level level, BlockPos pos, @Nullable Direction clickedSide) {
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
            if (level instanceof ServerLevel) {
                var randomsource = level.getRandom();

                label77:
                for (int i = 0; i < 128; ++i) {
                    BlockPos blockpos = pos;
                    BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

                    for (int j = 0; j < i / 16; ++j) {
                        blockpos = blockpos.offset(randomsource.nextInt(3) - 1, (randomsource.nextInt(3) - 1) * randomsource.nextInt(3) / 2, randomsource.nextInt(3) - 1);
                        if (level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                            continue label77;
                        }
                    }

                    Holder<Biome> holder = level.getBiome(blockpos);
                    if (holder.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
                        if (i == 0 && clickedSide != null && clickedSide.getAxis().isHorizontal()) {
                            blockstate = BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.WALL_CORALS, level.random).map((h) -> h.value().defaultBlockState()).orElse(blockstate);
                            if (blockstate.hasProperty(BaseCoralWallFanBlock.FACING)) {
                                blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, clickedSide);
                            }
                        } else if (randomsource.nextInt(4) == 0) {
                            blockstate = BuiltInRegistries.BLOCK.getRandomElementOf(BlockTags.UNDERWATER_BONEMEALS, level.random).map((h) -> h.value().defaultBlockState()).orElse(blockstate);
                        }
                    }

                    if (blockstate.is(BlockTags.WALL_CORALS, (p_204093_) -> p_204093_.hasProperty(BaseCoralWallFanBlock.FACING))) {
                        for (int k = 0; !blockstate.canSurvive(level, blockpos) && k < 4; ++k) {
                            blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomsource));
                        }
                    }

                    if (blockstate.canSurvive(level, blockpos)) {
                        var blockstate1 = level.getBlockState(blockpos);
                        if (blockstate1.is(Blocks.WATER) && level.getFluidState(blockpos).getAmount() == 8) {
                            level.setBlock(blockpos, blockstate, 3);
                        } else if (blockstate1.is(Blocks.SEAGRASS) && randomsource.nextInt(10) == 0) {
                            ((BonemealableBlock) Blocks.SEAGRASS).performBonemeal((ServerLevel) level, randomsource, blockpos, blockstate1);
                        }
                    }
                }

                stack.shrink(1);
            }
            return true;
        } else {
            return false;
        }
    }

    public static void addGrowthParticles(LevelAccessor level, BlockPos pos, int data) {
        var blockstate = level.getBlockState(pos);
        var blockpos = blockstate.getBlock();
        if (blockpos instanceof BonemealableBlock bonemealableblock) {
            var particlePos = bonemealableblock.getParticlePos(pos);
            switch (bonemealableblock.getType()) {
                case NEIGHBOR_SPREADER -> ParticleUtils.spawnParticles(level, particlePos, data * 3, 3.0F, 1.0F, false, ParticleTypes.HAPPY_VILLAGER);
                case GROWER -> ParticleUtils.spawnParticleInBlock(level, particlePos, data, ParticleTypes.HAPPY_VILLAGER);
            }
        } else if (blockstate.is(Blocks.WATER)) {
            ParticleUtils.spawnParticles(level, pos, data * 3, 3.0F, 1.0F, false, ParticleTypes.HAPPY_VILLAGER);
        }

    }

}
