package net.superscary.fluxmachines.core.hooks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.superscary.fluxmachines.core.block.base.FMBaseEntityBlock;
import net.superscary.fluxmachines.core.block.misc.CrucibleBlock;
import net.superscary.fluxmachines.core.block.multiblock.CokeOvenMultiblock;
import net.superscary.fluxmachines.core.block.multiblock.ReactorMultiBlock;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.blockentity.misc.CrucibleBlockEntity;
import net.superscary.fluxmachines.core.blockentity.reactor.ReactorCoreBlockEntity;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.registries.FMItems;
import net.superscary.fluxmachines.core.util.helper.ItemHelper;
import net.superscary.fluxmachines.core.util.helper.Utils;
import net.superscary.fluxmachines.core.util.tags.FMTag;

import java.util.HashMap;
import java.util.Map;

public class BlockHooks {

    private static final Map<BlockPos, Integer> pendingFluxSpawn = new HashMap<>();

    public static void place (BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            var level = event.getLevel();
            var state = event.getPlacedBlock();
            var block = state.getBlock();
            place(level, player, block, event.getPos());
        }
    }

    private static void place (LevelAccessor level, Player player, Block block, BlockPos pos) {
        var heldstack = player.getMainHandItem();
        if (block instanceof FMBaseEntityBlock<?> baseEntityBlock) {
            if (baseEntityBlock.getBlockEntity(level, pos) instanceof FMBasePoweredBlockEntity entity) {
                entity.setData(heldstack);
            }
        }
    }

    /**
     * Makes 1-3 {@link FMItems#CALCITE_DUST} from {@link Items#CALCITE}
     */
    public static void pistonCrush(PistonEvent.Post event) {
        var level = event.getLevel();
        var pistonPos = event.getPos();
        var direction = event.getDirection();
        var frontPos = pistonPos.relative(direction);
        var checkArea = new AABB(frontPos).inflate(0.1);

        if (event.getPistonMoveType() == PistonEvent.PistonMoveType.EXTEND) {
            var items = level.getEntitiesOfClass(ItemEntity.class, checkArea);
            for (var item : items) {
                if (item.getItem().getItem() == Items.CALCITE) {
                    var crushedAgainst = frontPos.relative(direction);
                    var state = level.getBlockState(crushedAgainst);

                    if (!state.isAir() && !state.canBeReplaced() && state.getBlock() == Blocks.OBSIDIAN) {
                        var amount = item.getItem().getCount();
                        item.discard();
                        pendingFluxSpawn.put(frontPos.immutable(), amount);
                    }
                }
            }
        } else if (event.getPistonMoveType() == PistonEvent.PistonMoveType.RETRACT) {
            var amount = pendingFluxSpawn.remove(frontPos);
            if (amount != null && amount > 0) {
                var actualAmount = Utils.randomInteger(1, 3) * amount;
                var fluxPowder = new ItemEntity((Level) level,
                        frontPos.getX() + 0.5, frontPos.getY() + 0.5, frontPos.getZ() + 0.5,
                        new ItemStack(FMItems.CALCITE_DUST.get(), actualAmount));
                level.addFreshEntity(fluxPowder);
            }
        }
    }

    /**
     * Lets player throw {@link FMItems#CALCITE_DUST} into fire to create {@link FMItems#FLUX_POWDER}
     * @param event fired event
     */
    public static void calciteToFlux (EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof ItemEntity item)) return;
        if (item.getItem().getItem() != FMItems.CALCITE_DUST.asItem()) return;

        var level = item.level();
        var pos = item.blockPosition();
        var block = level.getBlockState(pos);

        if (block.is(Blocks.FIRE) || block.is(Blocks.SOUL_FIRE) || block.is(Blocks.CAMPFIRE) || block.is(Blocks.SOUL_CAMPFIRE)) {
            if (!level.isClientSide()) {
                item.discard();
                var flux = new ItemEntity(level, item.getX(), item.getY(), item.getZ(), new ItemStack(FMItems.FLUX_POWDER, item.getItem().getCount()));
                level.addFreshEntity(flux);

                level.playSound(flux, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.f, 1.f);
            }
        }
    }

    public static void leftClickCrucible (PlayerInteractEvent.LeftClickBlock event) {
        var pos = event.getPos();
        var level = event.getLevel();
        var state = level.getBlockState(pos);
        var area = 0;

        if (state.getBlock() == FMBlocks.CRUCIBLE.block()) {
            var facing = state.getValue(BlockStateProperties.FACING);
            if (facing == event.getFace() && level.getBlockEntity(pos) instanceof CrucibleBlockEntity be) {
                event.setCanceled(true);
                var hit = Utils.getClientMouseOver();
                var relative = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
                area = CrucibleBlock.getArea(facing, relative);
                var stack = be.getInventory().getStackInSlot(area);

                if (!level.isClientSide()) {
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        if (stack != ItemStack.EMPTY) {
                            //PacketDistributor.sendToServer(new CrucibleBlockHit(pos, area, be.getInventory().getStackInSlot(area)));
                            ItemHelper.giveOrDrop(level, event.getEntity(), stack);
                            be.getInventory().setStackInSlot(area, ItemStack.EMPTY);
                        }

                        level.sendBlockUpdated(pos, be.getBlockState(), be.getBlockState(), 3);
                    }
                }
            }
        }
    }

    /**
     * Allows opening the reactor GUI by right-clicking the reactor frame or glass
     * @param event the event
     */
    public static void rightClickReactorBlock (PlayerInteractEvent.RightClickBlock event) {
        var level = event.getLevel();
        var pos = event.getPos();
        var player = event.getEntity();
        var state = level.getBlockState(pos);

        if (level.isClientSide()) return;

        if (!(state.is(FMTag.Blocks.REACTOR_BLOCK)) || state.is(FMTag.Blocks.REACTOR_PART) || state.is(FMBlocks.REACTOR_CORE.block())) return;

        if (player.getMainHandItem().is(FMBlocks.REACTOR_FRAME.asItem()) || player.getMainHandItem().is(FMBlocks.REACTOR_GLASS.asItem())) {
            return;
        }

        var corePos = ReactorMultiBlock.getCorePos(level, pos);

        if (ReactorMultiBlock.isValidAtCore(level, pos) && corePos != null) {
            var entity = level.getBlockEntity(corePos);
            if (entity instanceof ReactorCoreBlockEntity reactorCore) {
                player.openMenu(new SimpleMenuProvider(reactorCore, Component.translatable("multiblock.fluxmachines.reactor")), corePos);
            } else {
                throw new IllegalStateException("Container provider is missing.");
            }
        } else {
            player.displayClientMessage(Component.literal("Reactor is invalid!"), true);
        }

        event.setCancellationResult(InteractionResult.SUCCESS);
    }

    public static void rightClickRefractoryBlock (PlayerInteractEvent.RightClickBlock event) {
        var level = event.getLevel();
        var pos = event.getPos();
        var player = event.getEntity();
        var state = level.getBlockState(pos);

        if (!(state.is(FMTag.Blocks.COKE_OVEN_BLOCK))) return;

        if (player.getMainHandItem().is(FMBlocks.REFRACTORY_BRICK.asItem())) {
            return;
        }

        if (CokeOvenMultiblock.isValid(level, pos)) {
            player.displayClientMessage(Component.literal("Oven is valid!"), true);
        } else {
            player.displayClientMessage(Component.literal("Oven is invalid!"), true);
        }
    }

}
