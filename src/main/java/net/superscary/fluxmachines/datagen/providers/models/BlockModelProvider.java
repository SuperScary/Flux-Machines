package net.superscary.fluxmachines.datagen.providers.models;

import com.google.gson.JsonObject;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.util.block.BlockDefinition;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

import static net.superscary.fluxmachines.registries.FMBlocks.*;

public class BlockModelProvider extends FMBlockStateProvider {

    public static final ResourceLocation BOTTOM = FluxMachines.getResource("block/machine_bottom");
    public static final ResourceLocation TOP = FluxMachines.getResource("block/machine_top");
    public static final ResourceLocation SIDE = FluxMachines.getResource("block/machine_side");

    public BlockModelProvider (PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FluxMachines.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels () {
        blockWithItem(DURACITE_ORE);
        blockWithItem(DURACITE_DEEPSLATE_ORE);
        blockWithItem(DURACITE_NETHER_ORE);
        blockWithItem(DURACITE_BLOCK_RAW);
        blockWithItem(DURACITE_BLOCK);
        blockWithItem(MACHINE_CASING);

        //stairsBlock(KineticBlocks.BRICK_STAIRS.block(), blockTexture(KineticBlocks.BRICK.block()));
        //slabBlock(KineticBlocks.BRICK_SLAB.block(), blockTexture(KineticBlocks.BRICK.block()), blockTexture(KineticBlocks.BRICK.block()));
        //blockItem(KineticBlocks.BRICK_STAIRS);
        //blockItem(KineticBlocks.BRICK_SLAB);

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
        simpleBlockWithItem(blockRegistryObject.block(), cubeAll(blockRegistryObject.block()));
    }

    private void registerCable (BlockDefinition<?> block, String loader, ResourceLocation resourceLocation) {
        BlockModelBuilder model = models().getBuilder(loader)
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(resourceLocation, builder, helper, false))
                .end();
        simpleBlock(block.block(), model);
    }

    private void crate (BlockDefinition<?> block, String name) {
        BlockModelBuilder model = models().cube("block/" + block.id().getPath(), modLoc("block/crate_top"), modLoc("block/crate_top"), modLoc("block/crate_side"), modLoc("block/crate_side"), modLoc("block/crate_side"), modLoc("block/crate_side")).texture("particle", "block/crate_side");
        directionBlock(block.block(), (state, builder) -> builder.modelFile(model));
    }

    private void machine (BlockDefinition<?> block, String name) {
        BlockModelBuilder modelOn = models().cube("block/" + block.id().getPath() + "/" + block.id().getPath() + "_on", BOTTOM, TOP, modLoc("block/" + name + "/" + name + "_on"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().cube("block/" + block.id().getPath() + "/" + block.id().getPath() + "_off", BOTTOM, TOP, modLoc("block/" + name + "/" + name + "_off"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        directionBlock(block.block(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff));
    }

    private void solarPanel (BlockDefinition<?> block, String name) {
        BlockModelBuilder modelOn = models().cube("block/" + "solar_panel/" + name + "/on", BOTTOM, modLoc("block/" + block.id().getPath() + "_top_on"), SIDE, SIDE, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().cube("block/" + "solar_panel/" + name + "/off", BOTTOM, modLoc("block/solar_panel_top_off"), SIDE, SIDE, SIDE, SIDE).texture("particle", SIDE);
        directionBlock(block.block(), (state, builder) -> builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff));
    }

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
