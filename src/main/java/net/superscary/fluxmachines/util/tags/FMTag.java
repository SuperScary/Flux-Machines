package net.superscary.fluxmachines.util.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.superscary.fluxmachines.core.FluxMachines;

public class FMTag {

    public static class Blocks {
        public static final TagKey<Block> INCORRECT_FOR_DURACITE_TOOL = createTag("incorrect_for_duracite_tool");
        public static final TagKey<Block> NEEDS_DURACITE_TOOL = createTag("needs_duracite_tool");

        public static final TagKey<Block> PAXEL_MINEABLE = createTag("mineable/paxel");

        private static TagKey<Block> createTag (String key) {
            return BlockTags.create(FluxMachines.getResource(key));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag (String key) {
            return ItemTags.create(FluxMachines.getResource(key));
        }
    }

}
