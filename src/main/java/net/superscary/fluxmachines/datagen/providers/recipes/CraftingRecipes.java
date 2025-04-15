package net.superscary.fluxmachines.datagen.providers.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.registries.FMItems;
import net.superscary.fluxmachines.core.util.tags.FMTag;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.core.registries.FMBlocks.*;
import static net.superscary.fluxmachines.core.registries.FMItems.*;

public class CraftingRecipes extends FMRecipeProvider {

    public CraftingRecipes (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    public @NotNull String getName () {
        return "FluxMachines Crafting Recipes";
    }

    @Override
    protected void buildRecipes (@NotNull RecipeOutput consumer) {
        steelRecipes(consumer);
        machineParts(consumer);
        misc(consumer);
        machine(consumer);
    }

    protected void misc (RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, HONEY_BUN, 1)
                .pattern("G ")
                .pattern("HM")
                .define('G', Items.HONEY_BOTTLE)
                .define('H', Items.BREAD)
                .define('M', Tags.Items.BUCKETS_MILK)
                .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .save(consumer, FluxMachines.getResource("misc/honey_bun"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, WRENCH, 1)
                .pattern("D D")
                .pattern(" G ")
                .pattern(" D ")
                .define('D', STEEL_INGOT)
                .define('G', Items.GOLD_INGOT)
                .unlockedBy("has_steel", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("tool/wrench"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, REDSTONE_AND_STEEL, 1)
                .pattern("S ")
                .pattern(" R")
                .define('S', STEEL_INGOT)
                .define('R', Items.REDSTONE)
                .unlockedBy("has_steel", has(STEEL_INGOT))
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer, FluxMachines.getResource("tool/redstone_and_steel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DATA_LINK, 1)
                .pattern("SRS")
                .pattern("SGS")
                .pattern("SSS")
                .define('S', STEEL_INGOT)
                .define('G', Items.GOLD_INGOT)
                .define('R', Items.REDSTONE)
                .unlockedBy("has_steel", has(STEEL_INGOT))
                .unlockedBy("has_gold", has(Items.GOLD_INGOT))
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer, FluxMachines.getResource("tool/data_link"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, REFRACTORY_BRICK, 1)
                .pattern("CBC")
                .pattern("BCB")
                .pattern("CBC")
                .define('C', FLUX_POWDER)
                .define('B', Items.BRICK)
                .unlockedBy("has_flux_powder", has(FLUX_POWDER))
                .unlockedBy("has_brick", has(Items.BRICK))
                .save(consumer, FluxMachines.getResource("blocks/refractory_brick_from_flux_powder_and_bricks"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, REFRACTORY_BRICK, 1)
                .pattern("S")
                .pattern("S")
                .define('S', REFRACTORY_BRICK_SLAB)
                .unlockedBy("has_refractory_brick_slab", has(REFRACTORY_BRICK_SLAB))
                .save(consumer, FluxMachines.getResource("blocks/refractory_brick_from_refractory_brick_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, REFRACTORY_BRICK_SLAB, 6)
                .pattern("SSS")
                .define('S', REFRACTORY_BRICK)
                .unlockedBy("has_refractory_brick", has(REFRACTORY_BRICK))
                .save(consumer, FluxMachines.getResource("blocks/refractory_brick_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, REFRACTORY_BRICK_STAIRS, 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', REFRACTORY_BRICK)
                .unlockedBy("has_refractory_brick", has(REFRACTORY_BRICK))
                .save(consumer, FluxMachines.getResource("blocks/refractory_brick_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CALCITE, 1)
                .pattern("SS")
                .pattern("SS")
                .define('S', CALCITE_DUST)
                .unlockedBy("has_calcite_dust", has(CALCITE_DUST))
                .save(consumer, FluxMachines.getResource("blocks/calcite_block_from_dust"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CALCITE, 1)
                .pattern("S")
                .pattern("S")
                .define('S', CALCITE_SLAB)
                .unlockedBy("has_calcite_slab", has(CALCITE_SLAB))
                .save(consumer, FluxMachines.getResource("blocks/calcite_from_calcite_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CALCITE_SLAB, 6)
                .pattern("SSS")
                .define('S', Blocks.CALCITE)
                .unlockedBy("has_calcite", has(Blocks.CALCITE))
                .save(consumer, FluxMachines.getResource("blocks/calcite_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CALCITE_STAIRS, 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', Blocks.CALCITE)
                .unlockedBy("has_calcite", has(Blocks.CALCITE))
                .save(consumer, FluxMachines.getResource("blocks/calcite_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE, 1)
                .pattern("S")
                .pattern("S")
                .define('S', LIMESTONE_SLAB)
                .unlockedBy("has_limestone_slab", has(LIMESTONE_SLAB))
                .save(consumer, FluxMachines.getResource("blocks/limestone_from_limestone_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_SLAB, 6)
                .pattern("SSS")
                .define('S', LIMESTONE)
                .unlockedBy("has_limestone", has(LIMESTONE))
                .save(consumer, FluxMachines.getResource("blocks/limestone_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_STAIRS, 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', LIMESTONE)
                .unlockedBy("has_limestone", has(LIMESTONE))
                .save(consumer, FluxMachines.getResource("blocks/limestone_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_POLISHED, 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', LIMESTONE)
                .unlockedBy("has_limestone", has(LIMESTONE))
                .save(consumer, FluxMachines.getResource("blocks/limestone_polished_from_limestone"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_POLISHED, 1)
                .pattern("S")
                .pattern("S")
                .define('S', LIMESTONE_POLISHED_SLAB)
                .unlockedBy("has_limestone_polished_slab", has(LIMESTONE_POLISHED_SLAB))
                .save(consumer, FluxMachines.getResource("blocks/limestone_polished_from_limestone_polished_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_POLISHED_SLAB, 6)
                .pattern("SSS")
                .define('S', LIMESTONE_POLISHED)
                .unlockedBy("has_limestone_polished", has(LIMESTONE_POLISHED))
                .save(consumer, FluxMachines.getResource("blocks/limestone_polished_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_POLISHED_STAIRS, 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', LIMESTONE_POLISHED_STAIRS)
                .unlockedBy("has_limestone_polished", has(LIMESTONE_POLISHED))
                .save(consumer, FluxMachines.getResource("blocks/limestone_polished_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_BRICKS, 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', LIMESTONE_POLISHED)
                .unlockedBy("has_limestone_polished", has(LIMESTONE_POLISHED))
                .save(consumer, FluxMachines.getResource("blocks/limestone_brick_from_limestone_polished"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_BRICKS, 1)
                .pattern("S")
                .pattern("S")
                .define('S', LIMESTONE_BRICK_SLAB)
                .unlockedBy("has_limestone_brick_slab", has(LIMESTONE_BRICK_SLAB))
                .save(consumer, FluxMachines.getResource("blocks/limestone_brick_from_limestone_brick_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_BRICK_SLAB, 6)
                .pattern("SSS")
                .define('S', LIMESTONE_BRICKS)
                .unlockedBy("has_limestone_brick", has(LIMESTONE_BRICKS))
                .save(consumer, FluxMachines.getResource("blocks/limestone_brick_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LIMESTONE_BRICK_STAIRS, 4)
                .pattern("S  ")
                .pattern("SS ")
                .pattern("SSS")
                .define('S', LIMESTONE_BRICKS)
                .unlockedBy("has_limestone_brick", has(LIMESTONE_BRICKS))
                .save(consumer, FluxMachines.getResource("blocks/limestone_brick_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DURACITE_BLOCK_RAW, 1)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', RAW_DURACITE)
                .unlockedBy("has_raw_duracite", has(RAW_DURACITE))
                .save(consumer, FluxMachines.getResource("blocks/raw_duracite_block_from_raw_duracite"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DURACITE_BLOCK, 1)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', DURACITE_INGOT)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("blocks/duracite_block_from_duracite_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DURACITE_NUGGET, 9)
                .requires(DURACITE_INGOT)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("blocks/duracite_nugget_from_duracite_ingot"));

    }

    protected void machine (RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, FLUX_FURNACE, 1)
                .pattern("DRD")
                .pattern("DBD")
                .pattern("DFD")
                .define('D', STEEL_INGOT)
                .define('R', Items.REDSTONE_TORCH)
                .define('B', MACHINE_CASING)
                .define('F', COAL_GENERATOR)
                .unlockedBy("has_casing", has(MACHINE_CASING))
                .save(consumer, FluxMachines.getResource("machine/flux_furnace"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, COAL_GENERATOR, 1)
                .pattern("DRD")
                .pattern("DBD")
                .pattern("DFD")
                .define('D', STEEL_INGOT)
                .define('R', Items.REDSTONE_TORCH)
                .define('B', MACHINE_CASING)
                .define('F', Blocks.FURNACE)
                .unlockedBy("has_casing", has(MACHINE_CASING))
                .save(consumer, FluxMachines.getResource("machine/coal_generator"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, FLUID_TANK, 1)
                .pattern("DFD")
                .pattern("DBD")
                .pattern("DFD")
                .define('D', STEEL_INGOT)
                .define('B', MACHINE_CASING)
                .define('F', Tags.Items.BUCKETS_EMPTY)
                .unlockedBy("has_casing", has(MACHINE_CASING))
                .save(consumer, FluxMachines.getResource("machine/fluid_tank"));
    }

    /**
     * Machine Parts
     *
     * @param consumer output
     */
    protected void machineParts (RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MACHINE_CASING, 1)
                .pattern("DWD")
                .pattern("WGW")
                .pattern("DWD")
                .define('D', STEEL_INGOT)
                .define('W', Items.IRON_INGOT)
                .define('G', Blocks.BLACK_STAINED_GLASS)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("machine_part/machine_casing"));
    }

    /**
     * steel Recipes
     *
     * @param consumer output
     */
    protected void steelRecipes (RecipeOutput consumer) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STEEL_BLOCK, 1)
                .pattern("DDD")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("mat/steel_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STEEL_INGOT, 9)
                .requires(STEEL_BLOCK)
                .unlockedBy("has_steel_block", has(STEEL_BLOCK))
                .save(consumer, FluxMachines.getResource("mat/steel_from_block"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, STEEL_INGOT, 1)
                .pattern("DDD")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', STEEL_NUGGET)
                .unlockedBy("has_steel_nugget", has(STEEL_NUGGET))
                .save(consumer, FluxMachines.getResource("mat/steel_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, STEEL_NUGGET, 9)
                .requires(STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(STEEL_BLOCK))
                .save(consumer, FluxMachines.getResource("mat/steel_nugget_from_ingot"));

        /*
         * Armor
         */
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, STEEL_HELMET, 1)
                .pattern("DDD")
                .pattern("D D")
                .define('D', STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("armor/steel_helmet"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, STEEL_CHESTPLATE, 1)
                .pattern("D D")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("armor/steel_chestplate"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, STEEL_LEGGINGS, 1)
                .pattern("DDD")
                .pattern("D D")
                .pattern("D D")
                .define('D', STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("armor/steel_leggings"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, STEEL_BOOTS, 1)
                .pattern("D D")
                .pattern("D D")
                .define('D', STEEL_INGOT)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("armor/steel_boots"));

        /*
         * TOOLS
         */
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, STEEL_SWORD, 1)
                .pattern("D")
                .pattern("D")
                .pattern("S")
                .define('D', STEEL_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("tool/steel_sword"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STEEL_PICKAXE, 1)
                .pattern("DDD")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', STEEL_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("tool/steel_pickaxe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STEEL_SHOVEL, 1)
                .pattern("D")
                .pattern("S")
                .pattern("S")
                .define('D', STEEL_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("tool/steel_shovel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STEEL_AXE, 1)
                .pattern("DD")
                .pattern("DS")
                .pattern(" S")
                .define('D', STEEL_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("tool/steel_axe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STEEL_HOE, 1)
                .pattern("DD")
                .pattern(" S")
                .pattern(" S")
                .define('D', STEEL_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("tool/steel_hoe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STEEL_PAXEL, 1)
                .pattern("PAQ")
                .pattern(" S ")
                .pattern(" S ")
                .define('P', STEEL_PICKAXE)
                .define('A', STEEL_AXE)
                .define('Q', STEEL_SHOVEL)
                .define('S', Items.STICK)
                .unlockedBy("has_steel_pickaxe", has(STEEL_PICKAXE))
                .unlockedBy("has_steel_axe", has(STEEL_AXE))
                .unlockedBy("has_steel_shovel", has(STEEL_SHOVEL))
                .save(consumer, FluxMachines.getResource("tool/steel_paxel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STEEL_HAMMER, 1)
                .pattern("DdD")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', STEEL_BLOCK)
                .define('d', STEEL_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_steel_block", has(STEEL_BLOCK))
                .unlockedBy("has_steel_ingot", has(STEEL_INGOT))
                .save(consumer, FluxMachines.getResource("tool/steel_hammer"));
    }

}
