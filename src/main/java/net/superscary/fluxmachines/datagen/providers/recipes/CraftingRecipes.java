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
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.registries.FMBlocks.*;
import static net.superscary.fluxmachines.registries.FMItems.*;

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
        duraciteRecipes(consumer);
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
                .pattern("I I")
                .pattern(" G ")
                .pattern(" D ")
                .define('D', DURACITE_INGOT)
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GOLD_INGOT)
                .unlockedBy("has_duracite", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("tool/wrench"));
    }

    protected void machine (RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, FLUX_FURNACE, 1)
                .pattern("DRD")
                .pattern("DBD")
                .pattern("DFD")
                .define('D', DURACITE_INGOT)
                .define('R', Items.REDSTONE_TORCH)
                .define('B', MACHINE_CASING)
                .define('F', Blocks.FURNACE)
                .unlockedBy("has_casing", has(MACHINE_CASING))
                .save(consumer, FluxMachines.getResource("machine/flux_furnace"));
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
                .define('D', DURACITE_INGOT)
                .define('W', ItemTags.PLANKS)
                .define('G', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("machine_part/machine_casing"));
    }

    /**
     * Duracite Recipes
     *
     * @param consumer output
     */
    protected void duraciteRecipes (RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DURACITE_BLOCK_RAW, 1)
                .pattern("DDD")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', RAW_DURACITE)
                .unlockedBy("has_duracite_raw", has(RAW_DURACITE))
                .save(consumer, FluxMachines.getResource("mat/raw_duracite_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RAW_DURACITE, 9)
                .requires(DURACITE_BLOCK_RAW)
                .unlockedBy("has_raw_duracite_block", has(DURACITE_BLOCK_RAW))
                .save(consumer, FluxMachines.getResource("mat/raw_duracite_from_block"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DURACITE_BLOCK, 1)
                .pattern("DDD")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', DURACITE_INGOT)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("mat/duracite_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DURACITE_INGOT, 9)
                .requires(DURACITE_BLOCK)
                .unlockedBy("has_duracite_block", has(DURACITE_BLOCK))
                .save(consumer, FluxMachines.getResource("mat/duracite_from_block"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DURACITE_INGOT, 1)
                .pattern("DDD")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', DURACITE_NUGGET)
                .unlockedBy("has_duracite_nugget", has(DURACITE_NUGGET))
                .save(consumer, FluxMachines.getResource("mat/duracite_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DURACITE_NUGGET, 9)
                .requires(DURACITE_INGOT)
                .unlockedBy("has_duracite_ingot", has(DURACITE_BLOCK))
                .save(consumer, FluxMachines.getResource("mat/duracite_nugget_from_ingot"));

        /*
         * Armor
         */
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DURACITE_HELMET, 1)
                .pattern("DDD")
                .pattern("D D")
                .define('D', DURACITE_INGOT)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("armor/duracite_helmet"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DURACITE_CHESTPLATE, 1)
                .pattern("D D")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', DURACITE_INGOT)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("armor/duracite_chestplate"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DURACITE_LEGGINGS, 1)
                .pattern("DDD")
                .pattern("D D")
                .pattern("D D")
                .define('D', DURACITE_INGOT)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("armor/duracite_leggings"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DURACITE_BOOTS, 1)
                .pattern("D D")
                .pattern("D D")
                .define('D', DURACITE_INGOT)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("armor/duracite_boots"));

        /*
         * TOOLS
         */
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, DURACITE_SWORD, 1)
                .pattern("D")
                .pattern("D")
                .pattern("S")
                .define('D', DURACITE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("tool/duracite_sword"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DURACITE_PICKAXE, 1)
                .pattern("DDD")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', DURACITE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("tool/duracite_pickaxe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DURACITE_SHOVEL, 1)
                .pattern("D")
                .pattern("S")
                .pattern("S")
                .define('D', DURACITE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("tool/duracite_shovel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DURACITE_AXE, 1)
                .pattern("DD")
                .pattern("DS")
                .pattern(" S")
                .define('D', DURACITE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("tool/duracite_axe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DURACITE_HOE, 1)
                .pattern("DD")
                .pattern(" S")
                .pattern(" S")
                .define('D', DURACITE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("tool/duracite_hoe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DURACITE_PAXEL, 1)
                .pattern("PAQ")
                .pattern(" S ")
                .pattern(" S ")
                .define('P', DURACITE_PICKAXE)
                .define('A', DURACITE_AXE)
                .define('Q', DURACITE_SHOVEL)
                .define('S', Items.STICK)
                .unlockedBy("has_duracite_pickaxe", has(DURACITE_PICKAXE))
                .unlockedBy("has_duracite_axe", has(DURACITE_AXE))
                .unlockedBy("has_duracite_shovel", has(DURACITE_SHOVEL))
                .save(consumer, FluxMachines.getResource("tool/duracite_paxel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DURACITE_HAMMER, 1)
                .pattern("DdD")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', DURACITE_BLOCK)
                .define('d', DURACITE_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_duracite_block", has(DURACITE_BLOCK))
                .unlockedBy("has_duracite_ingot", has(DURACITE_INGOT))
                .save(consumer, FluxMachines.getResource("tool/duracite_hammer"));
    }

}
