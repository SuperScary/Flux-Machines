package net.superscary.fluxmachines.core.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.block.base.BaseBlock;
import net.superscary.fluxmachines.core.block.base.DecorativeBlock;
import net.superscary.fluxmachines.core.block.reactor.ReactorBlock;
import net.superscary.fluxmachines.core.block.cable.CableBlock;
import net.superscary.fluxmachines.core.block.cable.FacadeBlock;
import net.superscary.fluxmachines.core.block.machine.CoalGeneratorBlock;
import net.superscary.fluxmachines.core.block.machine.FluxFurnaceBlock;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.Tab;
import net.superscary.fluxmachines.core.block.machine.MachineCasingBlock;
import net.superscary.fluxmachines.core.block.misc.CrucibleBlock;
import net.superscary.fluxmachines.core.block.misc.FluidTankBlock;
import net.superscary.fluxmachines.core.block.reactor.ReactorCoreBlock;
import net.superscary.fluxmachines.core.block.reactor.ReactorFluidPortBlock;
import net.superscary.fluxmachines.core.item.FacadeBlockItem;
import net.superscary.fluxmachines.core.item.base.BaseBlockItem;
import net.superscary.fluxmachines.core.util.block.BlockDefinition;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;
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

	public static final BlockDefinition<DropExperienceBlock> DURACITE_ORE = reg("duracite_ore", () -> new DropExperienceBlock(UniformInt.of(3, 6), Blocks.IRON_ORE.properties().requiresCorrectToolForDrops()));
	public static final BlockDefinition<DropExperienceBlock> DURACITE_DEEPSLATE_ORE = reg("duracite_deepslate_ore", () -> new DropExperienceBlock(UniformInt.of(3, 6), Blocks.DEEPSLATE_IRON_ORE.properties().requiresCorrectToolForDrops()));
	public static final BlockDefinition<DropExperienceBlock> DURACITE_NETHER_ORE = reg("duracite_nether_ore", () -> new DropExperienceBlock(UniformInt.of(3, 6), Blocks.NETHER_GOLD_ORE.properties().requiresCorrectToolForDrops()));
	public static final BlockDefinition<DecorativeBlock> DURACITE_BLOCK_RAW = reg("duracite_block_raw", () -> new DecorativeBlock(Blocks.IRON_BLOCK.properties()));
	public static final BlockDefinition<DecorativeBlock> DURACITE_BLOCK = reg("duracite_block", () -> new DecorativeBlock(Blocks.IRON_BLOCK.properties()));

	public static final BlockDefinition<DecorativeBlock> MACHINE_CASING = reg("machine_casing", MachineCasingBlock::new);

	public static final BlockDefinition<ReactorBlock> REACTOR_FRAME = reg("reactor_frame", ReactorBlock::new);
	public static final BlockDefinition<ReactorBlock> REACTOR_GLASS = reg("reactor_glass", ReactorBlock::new);
	public static final BlockDefinition<ReactorCoreBlock> REACTOR_CORE = reg("reactor_core", ReactorCoreBlock::new);
	public static final BlockDefinition<ReactorFluidPortBlock> REACTOR_FLUID_PORT = reg("reactor_fluid_port", ReactorFluidPortBlock::new);

	public static final BlockDefinition<DecorativeBlock> LIMESTONE = reg("limestone", DecorativeBlock::new);
	public static final BlockDefinition<SlabBlock> LIMESTONE_SLAB = reg("limestone_slab", () -> new SlabBlock(Blocks.BRICK_SLAB.properties()));
	public static final BlockDefinition<StairBlock> LIMESTONE_STAIRS = reg("limestone_stairs", () -> new StairBlock(LIMESTONE.block().defaultBlockState(), Blocks.BRICK_STAIRS.properties()));
	public static final BlockDefinition<DecorativeBlock> LIMESTONE_BRICKS = reg("limestone_bricks", DecorativeBlock::new);
	public static final BlockDefinition<SlabBlock> LIMESTONE_BRICK_SLAB = reg("limestone_bricks_slab", () -> new SlabBlock(Blocks.BRICK_SLAB.properties()));
	public static final BlockDefinition<StairBlock> LIMESTONE_BRICK_STAIRS = reg("limestone_brick_stairs", () -> new StairBlock(LIMESTONE.block().defaultBlockState(), Blocks.BRICK_STAIRS.properties()));
	public static final BlockDefinition<DecorativeBlock> LIMESTONE_POLISHED = reg("limestone_polished", DecorativeBlock::new);
	public static final BlockDefinition<SlabBlock> LIMESTONE_POLISHED_SLAB = reg("limestone_polished_slab", () -> new SlabBlock(Blocks.BRICK_SLAB.properties()));
	public static final BlockDefinition<StairBlock> LIMESTONE_POLISHED_STAIRS = reg("limestone_polished_stairs", () -> new StairBlock(LIMESTONE.block().defaultBlockState(), Blocks.BRICK_STAIRS.properties()));
	public static final BlockDefinition<DecorativeBlock> REFRACTORY_BRICK = reg("refractory_bricks", DecorativeBlock::new);
	public static final BlockDefinition<SlabBlock> REFRACTORY_BRICK_SLAB = reg("refractory_brick_slab", () -> new SlabBlock(Blocks.BRICK_SLAB.properties()));
	public static final BlockDefinition<StairBlock> REFRACTORY_BRICK_STAIRS = reg("refractory_brick_stairs", () -> new StairBlock(REFRACTORY_BRICK.block().defaultBlockState(), Blocks.BRICK_STAIRS.properties()));
	public static final BlockDefinition<WallBlock> REFRACTORY_WALL = reg("refractory_wall", () -> new WallBlock(REFRACTORY_BRICK.block().properties()));
	public static final BlockDefinition<SlabBlock> CALCITE_SLAB = reg("calcite_slab", () -> new SlabBlock(Blocks.CALCITE.properties()));
	public static final BlockDefinition<StairBlock> CALCITE_STAIRS = reg("calcite_stairs", () -> new StairBlock(Blocks.CALCITE.defaultBlockState(), Blocks.CALCITE.properties()));

	public static final BlockDefinition<FluxFurnaceBlock> FLUX_FURNACE = reg("flux_furnace", FluxFurnaceBlock::new);
	public static final BlockDefinition<CoalGeneratorBlock> COAL_GENERATOR = reg("coal_generator", CoalGeneratorBlock::new);
	public static final BlockDefinition<CrucibleBlock> CRUCIBLE = reg("crucible", CrucibleBlock::new);

	public static final BlockDefinition<FluidTankBlock> FLUID_TANK = reg("fluid_tank", FluidTankBlock::new);

	public static final BlockDefinition<CableBlock> CABLE = reg("cable", CableBlock::new);
	public static final BlockDefinition<FacadeBlock> FACADE = reg("facade", FluxMachines.getResource("facade"), FacadeBlock::new, FacadeBlockItem::new, true);

	public static List<BlockDefinition<?>> getBlocks () {
		return Collections.unmodifiableList(BLOCKS);
	}

	public static <T extends Block> BlockDefinition<T> reg (final String name, final Supplier<T> supplier) {
		return reg(name, FluxMachines.getResource(name), supplier, null, true);
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
		if (addToTab) Tab.add(itemDef);
		BlockDefinition<T> definition = new BlockDefinition<>(name, deferredBlock, itemDef);
		BLOCKS.add(definition);
		return definition;
	}

}