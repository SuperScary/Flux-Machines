package net.superscary.fluxmachines.core.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.fluxmachines.core.block.cable.FacadeBlock;
import net.superscary.fluxmachines.core.blockentity.cable.FacadeBlockEntity;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.registries.FMDataComponents;
import net.superscary.fluxmachines.core.util.helper.ReplaceBlockItemUseContext;
import net.superscary.fluxmachines.core.util.item.MimickingRecord;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

import static net.superscary.fluxmachines.core.block.cable.CableBlock.*;

public class FacadeBlockItem extends BlockItem {

    public FacadeBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    private static String getMimickingString(ItemStack stack) {
        if (stack.has(FMDataComponents.MIMICKING)) {
            var value = stack.get(FMDataComponents.MIMICKING).block();
            var s = new ItemStack(value, 1);;
            return s.getHoverName().getString();
        }
        return "<unset>";
    }

    private static void userSetMimicBlock(@Nonnull ItemStack item, BlockState mimicBlock, UseOnContext context) {
        var world = context.getLevel();
        var player = context.getPlayer();
        setMimicBlock(item, mimicBlock);
        if (world.isClientSide) {
            player.displayClientMessage(Component.translatable(item.get(FMDataComponents.MIMICKING).block().toString(), mimicBlock.getBlock().getDescriptionId()), false);
        }
    }

    public static void setMimicBlock(@Nonnull ItemStack item, BlockState mimicBlock) {
        var mimicking = new MimickingRecord(true, mimicBlock.getBlock());
        item.set(FMDataComponents.MIMICKING, mimicking);
    }

    public static BlockState getMimicBlock(Level level, @Nonnull ItemStack stack) {
        if (stack.has(FMDataComponents.MIMICKING)) {
            return stack.get(FMDataComponents.MIMICKING).block().defaultBlockState();
        }

        return null;
    }

    @Override
    protected boolean canPlace(@Nonnull BlockPlaceContext context, @Nonnull BlockState state) {
        return true;
    }

    // This function is called when our block item is right-clicked on something. When this happens
    // we want to either set the mimic block or place the facade block
    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext context) {
        var world = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        var state = world.getBlockState(pos);
        var block = state.getBlock();

        var itemstack = context.getItemInHand();

        if (!itemstack.isEmpty()) {

            if (block == FMBlocks.CABLE.block()) {
                // We are hitting a cable block. We want to replace it with a facade block
                var facadeBlock = (FacadeBlock) this.getBlock();
                var blockContext = new ReplaceBlockItemUseContext(context);
                var placementState = facadeBlock.getStateForPlacement(blockContext)
                        .setValue(NORTH, state.getValue(NORTH))
                        .setValue(SOUTH, state.getValue(SOUTH))
                        .setValue(WEST, state.getValue(WEST))
                        .setValue(EAST, state.getValue(EAST))
                        .setValue(UP, state.getValue(UP))
                        .setValue(DOWN, state.getValue(DOWN));

                if (placeBlock(blockContext, placementState)) {
                    var soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
                    world.playSound(player, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    var te = world.getBlockEntity(pos);
                    if (te instanceof FacadeBlockEntity) {
                        ((FacadeBlockEntity) te).setMimicBlock(getMimicBlock(world, itemstack));
                    }
                    int amount = -1;
                    itemstack.grow(amount);
                }
            } else if (block == FMBlocks.FACADE.block()) {
                // We are hitting a facade block. We want to copy the block it is mimicing
                var te = world.getBlockEntity(pos);
                if (!(te instanceof FacadeBlockEntity facade)) {
                    return InteractionResult.FAIL;
                }
                if (facade.getMimicBlock() == null) {
                    return InteractionResult.FAIL;
                }
                userSetMimicBlock(itemstack, facade.getMimicBlock(), context);
            } else {
                // We are hitting something else. We want to set that block as what we are going to mimic
                userSetMimicBlock(itemstack, state, context);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (stack.has(FMDataComponents.MIMICKING)) {
            tooltipComponents.add(Component.translatable(stack.get(FMDataComponents.MIMICKING).block().toString(), getMimickingString(stack)));
        }
    }

}
