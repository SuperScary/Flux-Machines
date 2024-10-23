package net.superscary.fluxmachines.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.block.base.BaseBlock;
import net.superscary.fluxmachines.block.base.DecorativeBlock;
import net.superscary.fluxmachines.block.machine.FluxFurnaceBlock;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.Tab;
import net.superscary.fluxmachines.item.base.BaseBlockItem;
import net.superscary.fluxmachines.util.block.BlockDefinition;
import net.superscary.fluxmachines.util.item.ItemDefinition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class FMBlocks {

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(FluxMachines.MODID);

    public static final List<BlockDefinition<?>> BLOCKS = new ArrayList<>();

    public static final BlockDefinition<DecorativeBlock> STEEL_BLOCK = reg("steel_block", DecorativeBlock::new);
    public static final BlockDefinition<DecorativeBlock> MACHINE_CASING = reg("machine_casing", DecorativeBlock::new);

    public static final BlockDefinition<FluxFurnaceBlock> FLUX_FURNACE = reg("flux_furnace", FluxFurnaceBlock::new);

    public static List<BlockDefinition<?>> getBlocks () {
        return Collections.unmodifiableList(BLOCKS);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, final Supplier<T> supplier) {
        return reg(name, FluxMachines.getResource(name), supplier, null, true);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, ResourceLocation id, final Supplier<T> supplier, boolean addToTab) {
        return reg(name, id, supplier, null, addToTab);
    }

    public static <T extends Block> BlockDefinition<T> reg (final String name, ResourceLocation id, final Supplier<T> supplier, @Nullable BiFunction<Block, Item.Properties, BlockItem> itemFactory, boolean addToTab) {
        var deferredBlock = REGISTRY.register(id.getPath(), supplier);
        var deferredItem = FMItems.REGISTRY.register(id.getPath(), () -> {
            var block = deferredBlock.get();
            var itemProperties = new Item.Properties();
            if (itemFactory != null) {
                var item = itemFactory.apply(block, itemProperties);
                if (item == null) {
                    throw new IllegalArgumentException("BlockItem factory for " + id + " returned null.");
                }
                return item;
            } else if (block instanceof BaseBlock) {
                return new BaseBlockItem(block, itemProperties);
            } else {
                return new BlockItem(block, itemProperties);
            }
        });
        var itemDef = new ItemDefinition<>(name, deferredItem);
        Tab.add(itemDef);
        BlockDefinition<T> definition = new BlockDefinition<>(name, deferredBlock, itemDef);
        BLOCKS.add(definition);
        return definition;
    }

    public static void init () {

    }

}