package net.superscary.fluxmachines.datagen.providers.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.util.tags.FMTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.core.registries.FMBlocks.*;

public class FMBlockTagGenerator extends BlockTagsProvider implements IDataProvider {

    public FMBlockTagGenerator (PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FluxMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(MACHINE_CASING.block())
                .add(STEEL_BLOCK.block())
                .add(FLUX_FURNACE.block())
                .add(COAL_GENERATOR.block())
                .add(FLUID_TANK.block())
                .add(REFRACTORY_BRICK.block())
                .add(REFRACTORY_BRICK_SLAB.block())
                .add(REFRACTORY_BRICK_STAIRS.block())
                .add(REFRACTORY_WALL.block())
                .add(CALCITE_SLAB.block())
                .add(CALCITE_STAIRS.block())
                .add(LIMESTONE.block())
                .add(LIMESTONE_STAIRS.block())
                .add(LIMESTONE_SLAB.block())
                .add(LIMESTONE_BRICKS.block())
                .add(LIMESTONE_BRICK_STAIRS.block())
                .add(LIMESTONE_BRICK_SLAB.block())
                .add(LIMESTONE_POLISHED.block())
                .add(LIMESTONE_POLISHED_STAIRS.block())
                .add(LIMESTONE_POLISHED_SLAB.block())
                .add(DURACITE_ORE.block())
                .add(DURACITE_DEEPSLATE_ORE.block())
                .add(DURACITE_NETHER_ORE.block())
                .add(DURACITE_BLOCK.block())
                .add(DURACITE_BLOCK_RAW.block())
                .add(URANIUM_ORE.block())
                .add(REACTOR_FRAME.block())
                .add(REACTOR_GLASS.block())
                .add(REACTOR_CORE.block())
                .add(REACTOR_FLUID_PORT.block())
                .add(REACTOR_REDSTONE_PORT.block())
                .add(LASER_LENS.block())
                .add(LASER_FRAME.block())
                .add(BATTERY_CASING.block());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(MACHINE_CASING.block())
                .add(STEEL_BLOCK.block())
                .add(FLUX_FURNACE.block())
                .add(COAL_GENERATOR.block())
                .add(FLUID_TANK.block())
                .add(REFRACTORY_BRICK.block())
                .add(REFRACTORY_BRICK_SLAB.block())
                .add(REFRACTORY_BRICK_STAIRS.block())
                .add(REFRACTORY_WALL.block());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(DURACITE_ORE.block())
                .add(DURACITE_DEEPSLATE_ORE.block())
                .add(DURACITE_NETHER_ORE.block())
                .add(DURACITE_BLOCK.block())
                .add(DURACITE_BLOCK_RAW.block())
                .add(REACTOR_FRAME.block())
                .add(REACTOR_GLASS.block())
                .add(REACTOR_CORE.block())
                .add(REACTOR_FLUID_PORT.block())
                .add(REACTOR_REDSTONE_PORT.block())
                .add(LASER_LENS.block())
                .add(LASER_FRAME.block())
                .add(BATTERY_CASING.block());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(URANIUM_ORE.block());

        this.tag(FMTag.Blocks.REACTOR_BLOCK)
                .add(REACTOR_FRAME.block())
                .add(REACTOR_GLASS.block());

        this.tag(FMTag.Blocks.REACTOR_PART)
                .add(REACTOR_FLUID_PORT.block())
                .add(REACTOR_REDSTONE_PORT.block());

        this.tag(FMTag.Blocks.COKE_OVEN_BLOCK)
                .add(REFRACTORY_BRICK.block());

        this.tag(FMTag.Blocks.NEEDS_STEEL_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        this.tag(FMTag.Blocks.INCORRECT_FOR_STEEL_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_STONE_TOOL).remove(FMTag.Blocks.NEEDS_STEEL_TOOL);

        this.tag(FMTag.Blocks.PAXEL_MINEABLE)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL);

        this.tag(FMTag.Blocks.WRENCHABLE)
                .add(MACHINE_CASING.block())
                .add(FLUX_FURNACE.block())
                .add(COAL_GENERATOR.block())
                .add(FLUID_TANK.block());

        this.tag(FMTag.Blocks.STEEL)
                .add(STEEL_BLOCK.block());

        this.tag(BlockTags.WALLS)
                .add(REFRACTORY_WALL.block());

    }

}
