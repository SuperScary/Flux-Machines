package net.superscary.fluxmachines.util.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.superscary.fluxmachines.util.item.ItemDefinition;

import java.util.Objects;

public class BlockDefinition<T extends Block> extends ItemDefinition<BlockItem> {

    private final T block;

    public BlockDefinition (String name, ResourceLocation id, T block, BlockItem item) {
        super(name, id, item);
        this.block = Objects.requireNonNull(block, "Block cannot be null.");
    }

    public final T block () {
        return this.block;
    }

    public final ItemStack stack () {
        return stack(1);
    }

}
