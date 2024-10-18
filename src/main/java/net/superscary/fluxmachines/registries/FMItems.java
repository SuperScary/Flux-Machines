package net.superscary.fluxmachines.registries;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.Tab;
import net.superscary.fluxmachines.item.BaseItem;
import net.superscary.fluxmachines.util.keys.Keys;
import net.superscary.fluxmachines.util.item.ItemDefinition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class FMItems {

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(FluxMachines.MODID);

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    public static final ItemDefinition<BaseItem> RAW_DURACITE = item("duracite_raw", BaseItem::new);
    public static final ItemDefinition<BaseItem> DURACITE_DUST = item("duracite_dust", BaseItem::new);
    public static final ItemDefinition<BaseItem> DURACITE_INGOT = item("duracite_ingot", BaseItem::new);
    public static final ItemDefinition<BaseItem> DURACITE_NUGGET = item("duracite_nugget", BaseItem::new);

    public static List<ItemDefinition<?>> getItems () {
        return Collections.unmodifiableList(ITEMS);
    }

    static <T extends Item> ItemDefinition<T> item (String name, Function<Item.Properties, T> factory) {
        return item(name, FluxMachines.getResource(name), factory, Keys.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory) {
        return item(name, id, factory, Keys.MAIN);
    }

    static <T extends Item> ItemDefinition<T> item (String name, ResourceLocation id, Function<Item.Properties, T> factory, @Nullable ResourceKey<CreativeModeTab> group) {
        Item.Properties p = new Item.Properties();
        Preconditions.checkArgument(id.getNamespace().equals(FluxMachines.MODID), "Can only register items in FluxMachines");
        var definition = new ItemDefinition<>(name, REGISTRY.registerItem(id.getPath(), factory));

        if (Objects.equals(group, Keys.MAIN)) {
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
