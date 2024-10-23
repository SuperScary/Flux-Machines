package net.superscary.fluxmachines.item.tool;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.SimpleTier;
import net.superscary.fluxmachines.item.base.BaseItem;
import net.superscary.fluxmachines.registries.FMItems;
import net.superscary.fluxmachines.util.tags.FMTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SteelTool extends BaseItem {

    /**
     * Stronger but heavier than iron ref {@link Tiers#IRON}
     * Attack +1 | Speed -0.3F
     */
    public static final Tier STEEL = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 675, 6f, 2f, 16, () -> Ingredient.of(FMItems.STEEL_INGOT));

    public SteelTool (Properties properties) {
        super(properties);
    }

    /**
     * {@link Items#IRON_SWORD}
     */
    public static class Sword extends SwordItem {
        public Sword (Properties properties) {
            super(STEEL, properties.attributes(SwordItem.createAttributes(STEEL, 4, -2.7f)));
        }
    }

    /**
     * {@link Items#IRON_PICKAXE}
     */
    public static class Pickaxe extends PickaxeItem {
        public Pickaxe (Properties properties) {
            super(STEEL, properties.attributes(PickaxeItem.createAttributes(STEEL, 2, -3.1f)));
        }
    }

    /**
     * {@link Items#IRON_SHOVEL}
     */
    public static class Shovel extends ShovelItem {
        public Shovel (Properties properties) {
            super(STEEL, properties.attributes(ShovelItem.createAttributes(STEEL, 2.5f, -3.3f)));
        }
    }

    /**
     * {@link Items#IRON_AXE}
     */
    public static class Axe extends AxeItem {
        public Axe (Properties properties) {
            super(STEEL, properties.attributes(AxeItem.createAttributes(STEEL, 7, -3.4f)));
        }
    }

    /**
     * {@link Items#IRON_HOE}
     */
    public static class Hoe extends HoeItem {
        public Hoe (Properties properties) {
            super(STEEL, properties.attributes(HoeItem.createAttributes(STEEL, -1, -1.3f)));
        }
    }

    /**
     * Custom tool to mine with pickaxe, axe, & shovel
     */
    @SuppressWarnings("unchecked")
    public static class Paxel extends DiggerItem {

        protected static final Map<Block, Block> STRIPPABLES;
        protected static final Map<Block, BlockState> FLATTENABLES;

        public Paxel (Properties properties) {
            super(STEEL, FMTag.Blocks.PAXEL_MINEABLE, properties.attributes(DiggerItem.createAttributes(STEEL, 7, -2.9f)));
        }

        public @NotNull InteractionResult useOn (UseOnContext context) {
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            Player player = context.getPlayer();
            if (playerHasShieldUseIntent(context)) {
                return InteractionResult.PASS;
            } else {
                Optional<BlockState> optional = this.evaluateNewBlockState(level, blockpos, player, level.getBlockState(blockpos), context);
                if (optional.isEmpty()) {
                    BlockState blockstate = level.getBlockState(blockpos);
                    if (context.getClickedFace() == Direction.DOWN) {
                        return InteractionResult.PASS;
                    } else {
                        BlockState blockstate1 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
                        BlockState blockstate2 = null;
                        if (blockstate1 != null && level.getBlockState(blockpos.above()).isAir()) {
                            level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                            blockstate2 = blockstate1;
                        } else if ((blockstate2 = blockstate.getToolModifiedState(context, ItemAbilities.SHOVEL_DOUSE, false)) != null && !level.isClientSide()) {
                            level.levelEvent((Player)null, 1009, blockpos, 0);
                        }

                        if (blockstate2 != null) {
                            if (!level.isClientSide) {
                                level.setBlock(blockpos, blockstate2, 11);
                                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
                                if (player != null) {
                                    context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                                }
                            }

                            return InteractionResult.sidedSuccess(level.isClientSide);
                        } else {
                            return InteractionResult.PASS;
                        }
                    }
                } else {
                    ItemStack itemstack = context.getItemInHand();
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                    }

                    level.setBlock(blockpos, (BlockState)optional.get(), 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, (BlockState)optional.get()));
                    if (player != null) {
                        itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }

        private Optional<BlockState> evaluateNewBlockState (Level level, BlockPos pos, @Nullable Player player, BlockState state, UseOnContext context) {
            Optional<BlockState> optional = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false));
            if (optional.isPresent()) {
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                return optional;
            } else {
                Optional<BlockState> optional1 = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false));
                if (optional1.isPresent()) {
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.levelEvent(player, 3005, pos, 0);
                    return optional1;
                } else {
                    Optional<BlockState> optional2 = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false));
                    if (optional2.isPresent()) {
                        level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.levelEvent(player, 3004, pos, 0);
                        return optional2;
                    } else {
                        return Optional.empty();
                    }
                }
            }
        }

        private static boolean playerHasShieldUseIntent (UseOnContext context) {
            Player player = context.getPlayer();
            if (!context.getHand().equals(InteractionHand.MAIN_HAND)) return false;
            assert player != null;
            return player.getOffhandItem().is(Items.SHIELD) && !player.isSecondaryUseActive();
        }

        private Optional<BlockState> getStripped (BlockState unstrippedState) {
            return Optional.ofNullable((Block)STRIPPABLES.get(unstrippedState.getBlock())).map((state) -> {
                return (BlockState)state.defaultBlockState().setValue(RotatedPillarBlock.AXIS, (Direction.Axis)unstrippedState.getValue(RotatedPillarBlock.AXIS));
            });
        }

        public boolean canPerformAction (@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
            return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility) || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility);
        }

        static {
            STRIPPABLES = (new ImmutableMap.Builder()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD).put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE).put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE).put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD).put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG).put(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK).build();
            FLATTENABLES = Maps.newHashMap((new ImmutableMap.Builder()).put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState()).put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.defaultBlockState()).build());
        }

    }

    /**
     * Custom tool to break a 3x3
     */
    public static class Hammer extends DiggerItem {

        protected final Holder<MobEffect> effect;

        public Hammer (Properties properties) {
            super(STEEL, BlockTags.MINEABLE_WITH_PICKAXE, properties.attributes(PickaxeItem.createAttributes(STEEL, 7, -3.4f)));
            this.effect = MobEffects.MOVEMENT_SLOWDOWN;
        }

        @Override
        public boolean onLeftClickEntity (@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity entity) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(effect, 200), player);
            }

            return super.onLeftClickEntity(stack, player, entity);
        }

        public static List<BlockPos> getBlocksToBeDestroyed (int range, BlockPos initalBlockPos, ServerPlayer player) {
            List<BlockPos> positions = new ArrayList<>();
            BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                    (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            if (traceResult.getType() == HitResult.Type.MISS) {
                return positions;
            }
            if (traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY(), initalBlockPos.getZ() + y));
                    }
                }
            }
            if (traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ()));
                    }
                }
            }
            if (traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
                for (int x = -range; x <= range; x++) {
                    for (int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                    }
                }
            }
            return positions;
        }

    }

}
