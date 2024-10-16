package net.superscary.fluxmachines.registries;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.Tab;
import net.superscary.fluxmachines.item.BaseItem;
import net.superscary.fluxmachines.util.CreativeKeys;
import net.superscary.fluxmachines.util.item.ItemDefinition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class FMItems {

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    public static final ItemDefinition<BaseItem> RAW_DURACITE = item("duracite_raw", BaseItem::new);
    public static final ItemDefinition<BaseItem> DURACITE_DUST = item("duracite_dust", BaseItem::new);
    public static final ItemDefinition<BaseItem> DURACITE_INGOT = item("duracite_ingot", BaseItem::new);
    public static final ItemDefinition<BaseItem> DURACITE_NUGGET = item("duracite_nugget", BaseItem::new);

    public static List<ItemDefinition<?>> getItems () {
        return Collections.unmodifiableList(ITEMS);
    }

    static <T extends Item> ItemDefinition<T> item (String name, Function<Item.Properties, T> factory) {
        return item(name, FluxMachines.getResource(name), factory, CreativeKeys.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory) {
        return item(name, id, factory, CreativeKeys.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {

        Item.Properties p = new Item.Properties();

        T item = factory.apply(p);

        ItemDefinition<T> definition = new ItemDefinition<>(name, id, item);

        if (Objects.equals(group, CreativeKeys.MAIN)) {
            Tab.add(definition);
        } else if (group != null) {
            Tab.addExternal(group, definition);
        }

        ITEMS.add(definition);

        return definition;
    }

    public static void init () {

    }

}
