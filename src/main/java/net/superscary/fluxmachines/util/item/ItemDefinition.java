package net.superscary.fluxmachines.util.item;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemDefinition<T extends Item> implements ItemLike {

    private final ResourceLocation id;
    private final String name;
    private final T item;

    public ItemDefinition (String name, ResourceLocation id, T item) {
        Objects.requireNonNull(id, "ID cannot be null.");
        this.id = id;
        this.name = name;
        this.item = item;
    }

    public String getName () {
        return this.name;
    }

    public ResourceLocation id () {
        return this.id;
    }

    public ItemStack stack () {
        return stack(1);
    }

    public ItemStack stack (int size) {
        Preconditions.checkArgument(size > 0);
        return new ItemStack(this, size);
    }

    @Override
    public @NotNull T asItem () {
        return this.item;
    }

}
