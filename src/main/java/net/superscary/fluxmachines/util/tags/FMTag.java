package net.superscary.fluxmachines.util.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.superscary.fluxmachines.core.FluxMachines;

public class FMTag {

    public static class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_STEEL_TOOL = createTag("incorrect_for_steel_tool");
        public static final TagKey<Block> NEEDS_STEEL_TOOL = createTag("needs_steel_tool");

        public static final TagKey<Block> STEEL = createTag("steel_block");

        public static final TagKey<Block> PAXEL_MINEABLE = createTag("mineable/paxel");
        public static final TagKey<Block> WRENCHABLE = createTag("wrenchable");

        private static TagKey<Block> createTag (String key) {
            return BlockTags.create(FluxMachines.getResource(key));
        }
    }

    public static class Items {

        public static final TagKey<Item> WRENCH = createTag("wrench");
        public static final TagKey<Item> HAMMER = createTag("hammer");

        public static final TagKey<Item> STEEL = createTag("steel");
        public static final TagKey<Item> DUST = createTag("dust");

        private static TagKey<Item> createTag (String key) {
            return ItemTags.create(FluxMachines.getResource(key));
        }
    }

}
