package net.superscary.fluxmachines.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.superscary.fluxmachines.block.BaseBlock;
import net.superscary.fluxmachines.block.DecorativeBlock;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.Tab;
import net.superscary.fluxmachines.item.BaseBlockItem;
import net.superscary.fluxmachines.util.block.BlockDefinition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class FMBlocks {

    public static final List<BlockDefinition<?>> BLOCKS = new ArrayList<>();

    public static final BlockDefinition<DropExperienceBlock> DURACITE_ORE = reg("duracite_ore", () -> new DropExperienceBlock(UniformInt.of(3, 6), Blocks.IRON_ORE.properties().requiresCorrectToolForDrops()));
    public static final BlockDefinition<DropExperienceBlock> DURACITE_DEEPSLATE_ORE = reg("duracite_deepslate_ore", () -> new DropExperienceBlock(UniformInt.of(3, 6), Blocks.DEEPSLATE_IRON_ORE.properties().requiresCorrectToolForDrops()));
    public static final BlockDefinition<DropExperienceBlock> DURACITE_NETHER_ORE = reg("duracite_nether_ore", () -> new DropExperienceBlock(UniformInt.of(3, 6), Blocks.NETHER_GOLD_ORE.properties().requiresCorrectToolForDrops()));
    public static final BlockDefinition<DecorativeBlock> DURACITE_BLOCK_RAW = reg("duracite_block_raw", () -> new DecorativeBlock(Blocks.IRON_BLOCK.properties()));
    public static final BlockDefinition<DecorativeBlock> DURACITE_BLOCK = reg("duracite_block", () -> new DecorativeBlock(Blocks.IRON_BLOCK.properties()));
    public static final BlockDefinition<DecorativeBlock> MACHINE_CASING = reg("machine_casing", () -> new DecorativeBlock(Blocks.IRON_BLOCK.properties()));

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
        T block = supplier.get();
        Item.Properties itemProperties = new Item.Properties();
        BlockItem item;
        if (itemFactory != null) {
            item = itemFactory.apply(block, itemProperties);
            if (item == null) throw new IllegalArgumentException("BlockItem factory for " + id + " returned null.");
        } else if (block instanceof BaseBlock) {
            item = new BaseBlockItem(block, itemProperties);
        } else {
            item = new BlockItem(block, itemProperties);
        }

        BlockDefinition<T> definition = new BlockDefinition<>(name, id, block, item);
        if (addToTab) Tab.add(definition);
        BLOCKS.add(definition);
        return definition;
    }

    public static void init () {

    }

}