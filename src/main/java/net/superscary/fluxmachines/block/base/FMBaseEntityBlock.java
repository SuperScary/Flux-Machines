package net.superscary.fluxmachines.block.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.superscary.fluxmachines.blockentity.base.FMBaseBlockEntity;
import net.superscary.fluxmachines.core.registries.FMDataComponents;
import net.superscary.fluxmachines.core.registries.FMItems;
import net.superscary.fluxmachines.core.util.FMProperties;
import net.superscary.fluxmachines.core.util.tags.FMTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.List;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.CRAFTING;
import static net.superscary.fluxmachines.core.util.FMProperties.DESTROYED_WITH_WRENCH;

public abstract class FMBaseEntityBlock<T extends FMBaseBlockEntity> extends BaseBlock implements EntityBlock {

    private Class<T> blockEntityClass;
    private BlockEntityType<T> blockEntityType;

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    private boolean disassemble = false;

    private final MapCodec<BaseBlock> codec = getCodec();

    public FMBaseEntityBlock (Properties properties) {
        super(properties);
    }

    public void setBlockEntity (Class<T> blockEntityClass, BlockEntityType<T> blockEntityType) {
        this.blockEntityClass = blockEntityClass;
        this.blockEntityType = blockEntityType;
    }

    @Nullable
    public T getBlockEntity (BlockGetter level, int x, int y, int z) {
        return this.getBlockEntity(level, new BlockPos(x, y, z));
    }

    @Nullable
    public T getBlockEntity (BlockGetter level, BlockPos pos) {
        final BlockEntity te = level.getBlockEntity(pos);
        if (this.blockEntityClass != null && this.blockEntityClass.isInstance(te)) {
            return this.blockEntityClass.cast(te);
        }

        return null;
    }

    public abstract MapCodec<BaseBlock> getCodec ();

    public BlockEntityType<T> getBlockEntityType () {
        return blockEntityType;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity (@NotNull BlockPos pos, @NotNull BlockState state) {
        return blockEntityType.create(pos, state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.POWERED, false)
                .setValue(FACING, context.getNearestLookingDirection().getOpposite())
                .setValue(CRAFTING, false);
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.POWERED, FACING, CRAFTING);
    }

    @Override
    protected @NotNull RenderShape getRenderShape (@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove (BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean movedByPiston) {
        remove(state, level, pos, newState, movedByPiston, getDisassembled());
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    public void remove (BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean movedByPiston, boolean wrenched) {
        if (state.getBlock() != newState.getBlock()) {
            var blockentity = this.getBlockEntity(level, pos);
            if (blockentity != null) {
                if (!wrenched) {
                    blockentity.drops(blockentity.getInventory());
                }
            }
        }
    }

    /**
     * Called when the block is to be destroyed by a wrench
     */
    public void destroyBlockByWrench (Player player, BlockState state, @NotNull Level level, @NotNull BlockPos pos, boolean movedByPiston) {
        disassemble = true;
        playerWillDestroy(level, pos, state, player);
        remove(state, level, pos, state, false, disassemble);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3, 512);
        disassemble = false;
    }

    @Override
    public void appendHoverText (ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            if (stack.has(FMDataComponents.ENERGY_STORED) && stack.has(FMDataComponents.ENERGY_MAX)) {
                DecimalFormat format = new DecimalFormat("#,###");
                tooltipComponents.add(Component.literal("Stored: " + format.format(stack.get(FMDataComponents.ENERGY_STORED)) + "/" + format.format(stack.get(FMDataComponents.ENERGY_MAX)) + " FE"));
            }

            if (stack.has(FMDataComponents.INVENTORY)) {
                var inventory = stack.get(FMDataComponents.INVENTORY);
                for (int i = 0; i < inventory.inventory().size(); i++) {
                    if (!inventory.inventory().get(i).is(FMItems.EMPTY.asItem()) && !inventory.inventory().get(i).is(Items.AIR)) {
                        var itemstack = inventory.inventory().get(i);
                        tooltipComponents.add(Component.translatable("gui.fluxmachines.itemlist", i+1, itemstack.getCount(), itemstack.getDisplayName()));
                    }
                }
            }
        } else {
            tooltipComponents.add(Component.translatable("gui.fluxmachines.press_shift"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec () {
        return codec;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn (ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(FMTag.Items.WRENCH)) {
            return ItemInteractionResult.FAIL;
        }
        return ItemInteractionResult.SUCCESS;
    }

    public boolean getDisassembled () {
        return disassemble;
    }

}
