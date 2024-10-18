package net.superscary.fluxmachines.core;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.superscary.fluxmachines.block.base.BaseBlock;
import net.superscary.fluxmachines.item.base.BaseBlockItem;
import net.superscary.fluxmachines.item.base.BaseItem;
import net.superscary.fluxmachines.registries.FMItems;
import net.superscary.fluxmachines.util.keys.Keys;
import net.superscary.fluxmachines.util.item.ItemDefinition;

import java.util.ArrayList;
import java.util.List;

public class Tab {

    private static final Multimap<ResourceKey<CreativeModeTab>, ItemDefinition<?>> externalItemDefs = HashMultimap.create();
    private static final List<ItemDefinition<?>> itemDefs = new ArrayList<>();

    public static void init (Registry<CreativeModeTab> registry) {
        var tab = CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.fluxmachines"))
                .icon(FMItems.DURACITE_INGOT::stack)
                .displayItems(Tab::buildDisplayItems)
                .build();
        Registry.register(registry, Keys.MAIN, tab);
    }

    public static void initExternal (BuildCreativeModeTabContentsEvent contents) {
        for (var itemDefinition : externalItemDefs.get(contents.getTabKey())) {
            contents.accept(itemDefinition);
        }
    }

    public static void add (ItemDefinition<?> itemDef) {
        itemDefs.add(itemDef);
    }

    public static void addExternal (ResourceKey<CreativeModeTab> tab, ItemDefinition<?> itemDef) {
        externalItemDefs.put(tab, itemDef);
    }

    private static void buildDisplayItems (CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        for (var itemDef : itemDefs) {
            var item = itemDef.asItem();
            if (item instanceof BaseBlockItem baseItem && baseItem.getBlock() instanceof BaseBlock baseBlock) {
                baseBlock.addToCreativeTab(output);
            } else if (item instanceof BaseItem baseItem) {
                baseItem.addToCreativeTab(output);
            } else {
                output.accept(itemDef);
            }
        }
    }

}
