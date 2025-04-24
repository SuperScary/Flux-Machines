package net.superscary.fluxmachines.datagen.providers.models;

import com.google.gson.JsonObject;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.util.block.BlockDefinition;
import net.superscary.fluxmachines.model.CableModelLoader;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

import static net.superscary.fluxmachines.core.registries.FMBlocks.*;
import static net.superscary.fluxmachines.core.util.block.FMBlockStates.FLUID_PORT_INPUT;
import static net.superscary.fluxmachines.core.util.block.FMBlockStates.RESTONE_PORT_INPUT;

public class BlockModelProvider extends FMBlockStateProvider {

    public static final ResourceLocation MACHINE_BOTTOM = FluxMachines.getResource("block/machine_states/machine_bottom");
    public static final ResourceLocation MACHINE_TOP = FluxMachines.getResource("block/machine_states/machine_top");
    public static final ResourceLocation MACHINE_SIDE = FluxMachines.getResource("block/machine_states/machine_side");

    public static final ResourceLocation MACHINE_BOTTOM_ANY_IN = FluxMachines.getResource("block/machine_states/machine_bottom_any");
    public static final ResourceLocation MACHINE_TOP_ANY_IN = FluxMachines.getResource("block/machine_states/machine_top_any");
    public static final ResourceLocation MACHINE_SIDE_ANY_IN = FluxMachines.getResource("block/machine_states/machine_side_any");

    public static final ResourceLocation MACHINE_BOTTOM_ITEM_IN = FluxMachines.getResource("block/machine_states/machine_bottom_item_in");
    public static final ResourceLocation MACHINE_TOP_ITEM_IN = FluxMachines.getResource("block/machine_states/machine_top_item_in");
    public static final ResourceLocation MACHINE_SIDE_ITEM_IN = FluxMachines.getResource("block/machine_states/machine_side_item_in");

    public static final ResourceLocation MACHINE_BOTTOM_ITEM_OUT = FluxMachines.getResource("block/machine_states/machine_bottom_item_out");
    public static final ResourceLocation MACHINE_TOP_ITEM_OUT = FluxMachines.getResource("block/machine_states/machine_top_item_out");
    public static final ResourceLocation MACHINE_SIDE_ITEM_OUT = FluxMachines.getResource("block/machine_states/machine_side_item_out");

    public static final ResourceLocation MACHINE_BOTTOM_POWER_IN = FluxMachines.getResource("block/machine_states/machine_bottom_power_in");
    public static final ResourceLocation MACHINE_TOP_POWER_IN = FluxMachines.getResource("block/machine_states/machine_top_power_in");
    public static final ResourceLocation MACHINE_SIDE_POWER_IN = FluxMachines.getResource("block/machine_states/machine_side_power_in");

    public static final ResourceLocation MACHINE_BOTTOM_POWER_OUT = FluxMachines.getResource("block/machine_states/machine_bottom_power_out");
    public static final ResourceLocation MACHINE_TOP_POWER_OUT = FluxMachines.getResource("block/machine_states/machine_top_power_out");
    public static final ResourceLocation MACHINE_SIDE_POWER_OUT = FluxMachines.getResource("block/machine_states/machine_side_power_out");

    public BlockModelProvider (PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FluxMachines.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels () {
        blockWithItem(STEEL_BLOCK);

        blockWithItem(DURACITE_ORE);
        blockWithItem(DURACITE_DEEPSLATE_ORE);
        blockWithItem(DURACITE_NETHER_ORE);
        blockWithItem(DURACITE_BLOCK);
        blockWithItem(DURACITE_BLOCK_RAW);

        blockWithItem(URANIUM_ORE);

        blockWithItem(REFRACTORY_BRICK);
        blockWithItem(LIMESTONE);
        blockWithItem(LIMESTONE_BRICKS);
        blockWithItem(LIMESTONE_POLISHED);
        blockWithItemRenderType(MACHINE_CASING, "translucent");

        blockWithItem(REACTOR_FRAME);
        blockWithItemRenderType(REACTOR_GLASS, "translucent");
        blockWithItem(REACTOR_CORE);
        reactorFluidPort();
        reactorRedstonePort();

        blockWithItemRenderType(LASER_LENS, "translucent");
        blockWithItem(LASER_FRAME);

        blockWithItem(BATTERY_CASING);

        machine(FLUX_FURNACE, "flux_furnace");
        machine(COAL_GENERATOR, "coal_generator");
        fluidTank(FLUID_TANK);

        registerCable();
        registerFacade();

        stairsBlock(REFRACTORY_BRICK_STAIRS.block(), blockTexture(REFRACTORY_BRICK.block()));
        slabBlock(REFRACTORY_BRICK_SLAB.block(), blockTexture(REFRACTORY_BRICK.block()), blockTexture(REFRACTORY_BRICK.block()));
        wall(REFRACTORY_WALL, blockTexture(REFRACTORY_BRICK.block()));
        blockItem(REFRACTORY_BRICK_SLAB);
        blockItem(REFRACTORY_BRICK_STAIRS);

        stairsBlock(CALCITE_STAIRS.block(), blockTexture(Blocks.CALCITE));
        slabBlock(CALCITE_SLAB.block(), blockTexture(Blocks.CALCITE), blockTexture(Blocks.CALCITE));
        blockItem(CALCITE_STAIRS);
        blockItem(CALCITE_SLAB);

        stairsBlock(LIMESTONE_STAIRS.block(), blockTexture(LIMESTONE.block()));
        slabBlock(LIMESTONE_SLAB.block(), blockTexture(LIMESTONE.block()), blockTexture(LIMESTONE.block()));
        blockItem(LIMESTONE_STAIRS);
        blockItem(LIMESTONE_SLAB);

        stairsBlock(LIMESTONE_BRICK_STAIRS.block(), blockTexture(LIMESTONE_BRICKS.block()));
        slabBlock(LIMESTONE_BRICK_SLAB.block(), blockTexture(LIMESTONE_BRICKS.block()), blockTexture(LIMESTONE_BRICKS.block()));
        blockItem(LIMESTONE_BRICK_STAIRS);
        blockItem(LIMESTONE_BRICK_SLAB);

        stairsBlock(LIMESTONE_POLISHED_STAIRS.block(), blockTexture(LIMESTONE_POLISHED.block()));
        slabBlock(LIMESTONE_POLISHED_SLAB.block(), blockTexture(LIMESTONE_POLISHED.block()), blockTexture(LIMESTONE_POLISHED.block()));
        blockItem(LIMESTONE_POLISHED_STAIRS);
        blockItem(LIMESTONE_POLISHED_SLAB);

    }

    private void leavesBlock (BlockDefinition<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.block(),
                models().cubeAll(blockRegistryObject.id().getPath(), blockTexture(blockRegistryObject.block())).renderType("cutout"));
    }

    private void saplingBlock (BlockDefinition<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.block(),
                models().cross(blockRegistryObject.id().getPath(), blockTexture(blockRegistryObject.block())).renderType("cutout"));
    }

    private void blockItem (BlockDefinition<Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.block(), new ModelFile.UncheckedModelFile("fluxmachines:block/" + blockRegistryObject.id().getPath() + appendix));
    }

    private void blockItem (BlockDefinition<?> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.block(), new ModelFile.UncheckedModelFile("fluxmachines:block/" + blockRegistryObject.id().getPath()));
    }

    private void blockWithItem (BlockDefinition<?> blockRegistryObject) {
        err(List.of(blockRegistryObject.id()));
        simpleBlockWithItem(blockRegistryObject.block(), cubeAll(blockRegistryObject.block()));
    }

    private void blockWithItemRenderType (BlockDefinition<?> blockRegistryObject, String renderType) {
        simpleBlockWithItem(blockRegistryObject.block(), models().cubeAll(blockRegistryObject.id().getPath(), modLoc("block/" + blockRegistryObject.id().getPath())).renderType(renderType));
    }

    private void registerCable (BlockDefinition<?> block, String loader, ResourceLocation resourceLocation) {
        BlockModelBuilder model = models().getBuilder(loader)
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(resourceLocation, builder, helper, false))
                .end();
        simpleBlock(block.block(), model);
    }

    private void machineCasing (BlockDefinition<?> block) {
        var rs = FluxMachines.getResource("block/machine_casing");
        BlockModelBuilder model = models().cube("block/" + block.id().getPath(), rs, rs, rs, rs, rs, rs).texture("particle", rs).renderType("cutout");
        simpleBlockWithItem(block.block(), model);
    }

    private void crate (BlockDefinition<?> block, String name) {
        BlockModelBuilder model = models().cube("block/" + block.id().getPath(), modLoc("block/crate_top"), modLoc("block/crate_top"), modLoc("block/crate_side"), modLoc("block/crate_side"), modLoc("block/crate_side"), modLoc("block/crate_side")).texture("particle", "block/crate_side");
        directionBlock(block.block(), (state, builder) -> builder.modelFile(model));
    }

    private void machine (BlockDefinition<?> block, String name) {
        var on = modLoc("block/" + name + "/" + name + "_on");
        var off = modLoc("block/" + name + "/" + name + "_off");

        err(List.of(on, off));

        BlockModelBuilder modelOn = models().cube("block/" + block.id().getPath() + "/" + block.id().getPath() + "_on", MACHINE_BOTTOM, MACHINE_TOP, on, MACHINE_SIDE, MACHINE_SIDE, MACHINE_SIDE).texture("particle", MACHINE_SIDE);
        BlockModelBuilder modelOff = models().cube("block/" + block.id().getPath() + "/" + block.id().getPath() + "_off", MACHINE_BOTTOM, MACHINE_TOP, off, MACHINE_SIDE, MACHINE_SIDE, MACHINE_SIDE).texture("particle", MACHINE_SIDE);
        directionBlock(block.block(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff));
    }

    private void reactorFluidPort () {
        var in = modLoc("block/reactor_fluid_port_in");
        var out = modLoc("block/reactor_fluid_port_out");

        err(List.of(in, out));

        BlockModelBuilder modelIn = models().cube("block/" + REACTOR_FLUID_PORT.id().getPath() + "_in", in, in, in, in, in, in).texture("particle", in);
        BlockModelBuilder modelOut = models().cube("block/" + REACTOR_FLUID_PORT.id().getPath() + "_out", out, out, out, out, out, out).texture("particle", out);

        standard(REACTOR_FLUID_PORT.block(), ((blockState, builder) -> builder.modelFile(blockState.getValue(FLUID_PORT_INPUT) ? modelIn : modelOut)));
    }

    private void reactorRedstonePort () {
        var in = modLoc("block/reactor_redstone_port_in");
        var out = modLoc("block/reactor_redstone_port_out");

        err(List.of(in, out));

        BlockModelBuilder modelIn = models().cube("block/" + REACTOR_REDSTONE_PORT.id().getPath() + "_in", in, in, in, in, in, in).texture("particle", in);
        BlockModelBuilder modelOut = models().cube("block/" + REACTOR_REDSTONE_PORT.id().getPath() + "_out", out, out, out, out, out, out).texture("particle", out);

        standard(REACTOR_REDSTONE_PORT.block(), ((blockState, builder) -> builder.modelFile(blockState.getValue(RESTONE_PORT_INPUT) ? modelIn : modelOut)));
    }

    private void fluidTank (BlockDefinition<?> block) {
        var side = FluxMachines.getResource("block/fluid_tank_side");
        var top = FluxMachines.getResource("block/fluid_tank_top");
        var bottom = MACHINE_BOTTOM;

        err(List.of(side, top, bottom));

        var model = models().cube("block/" + block.id().getPath(), bottom, top, side, side, side, side).texture("particle", side).renderType("translucent");

        simpleBlockWithItem(block.block(), model);
    }

    private void solarPanel (BlockDefinition<?> block, String name) {
        BlockModelBuilder modelOn = models().cube("block/" + "solar_panel/" + name + "/on", MACHINE_BOTTOM, modLoc("block/" + block.id().getPath() + "_top_on"), MACHINE_SIDE, MACHINE_SIDE, MACHINE_SIDE, MACHINE_SIDE).texture("particle", MACHINE_SIDE);
        BlockModelBuilder modelOff = models().cube("block/" + "solar_panel/" + name + "/off", MACHINE_BOTTOM, modLoc("block/solar_panel_top_off"), MACHINE_SIDE, MACHINE_SIDE, MACHINE_SIDE, MACHINE_SIDE).texture("particle", MACHINE_SIDE);
        directionBlock(block.block(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff));
    }

    private VariantBlockStateBuilder standard (Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        var builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            var bld = ConfiguredModel.builder();
            model.accept(state, bld);
            return bld.build();
        });
        return builder;
    }

    /**
     * TODO: Modify for side states for input/output allowance
     */
    private VariantBlockStateBuilder directionBlock (Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            model.accept(state, bld);
            applyRotationBld(bld, state.getValue(BlockStateProperties.FACING));
            return bld.build();
        });
        return builder;
    }

    private void applyRotationBld (ConfiguredModel.Builder<?> builder, Direction direction) {
        switch (direction) {
            case DOWN -> builder.rotationX(90);
            case UP -> builder.rotationX(-90);
            case NORTH -> {
            }
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }

    private void registerCable() {
        BlockModelBuilder model = models().getBuilder("cable")
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(CableModelLoader.GENERATOR_LOADER, builder, helper, false))
                .end();
        simpleBlock(FMBlocks.CABLE.block(), model);
    }

    private void registerFacade() {
        BlockModelBuilder model = models().getBuilder("facade")
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(CableModelLoader.GENERATOR_LOADER, builder, helper, true))
                .end();
        simpleBlock(FMBlocks.FACADE.block(), model);
    }

    /**
     * Ignores missing textures so we can still build data without the texture present.
     * @param list a list of ResourceLocations that we know will exist but currently don't.
     */
    private void err (List<ResourceLocation> list) {
        for (var res : list) {
            existingFileHelper.trackGenerated(res, PackType.CLIENT_RESOURCES, ".png", "textures");
        }
    }

    public static class CableLoaderBuilder extends CustomLoaderBuilder<BlockModelBuilder> {
        private final boolean facade;

        public CableLoaderBuilder (ResourceLocation loader, BlockModelBuilder parent, ExistingFileHelper existingFileHelper, boolean facade) {
            super(loader, parent, existingFileHelper, false);
            this.facade = facade;
        }

        @Override
        public @NotNull JsonObject toJson (@NotNull JsonObject json) {
            JsonObject obj = super.toJson(json);
            obj.addProperty("facade", facade);
            return obj;
        }
    }

}
