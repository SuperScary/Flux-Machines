package net.superscary.fluxmachines.registries;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.Tab;
import net.superscary.fluxmachines.item.base.BaseFood;
import net.superscary.fluxmachines.item.base.BaseItem;
import net.superscary.fluxmachines.item.base.DuraciteArmorItem;
import net.superscary.fluxmachines.item.tool.DuraciteTool;
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

    public static final ItemDefinition<DuraciteArmorItem> DURACITE_HELMET = item("duracite_helmet", DuraciteArmorItem.DuraciteHelmet::new);
    public static final ItemDefinition<DuraciteArmorItem> DURACITE_CHESTPLATE = item("duracite_chestplate", DuraciteArmorItem.DuraciteChestplate::new);
    public static final ItemDefinition<DuraciteArmorItem> DURACITE_LEGGINGS = item("duracite_leggings", DuraciteArmorItem.DuraciteLeggings::new);
    public static final ItemDefinition<DuraciteArmorItem> DURACITE_BOOTS = item("duracite_boots", DuraciteArmorItem.DuraciteBoots::new);
    public static final ItemDefinition<DuraciteTool.Sword> DURACITE_SWORD = item("duracite_sword", DuraciteTool.Sword::new);
    public static final ItemDefinition<DuraciteTool.Pickaxe> DURACITE_PICKAXE = item("duracite_pickaxe", DuraciteTool.Pickaxe::new);
    public static final ItemDefinition<DuraciteTool.Axe> DURACITE_AXE = item("duracite_axe", DuraciteTool.Axe::new);
    public static final ItemDefinition<DuraciteTool.Shovel> DURACITE_SHOVEL = item("duracite_shovel", DuraciteTool.Shovel::new);
    public static final ItemDefinition<DuraciteTool.Hoe> DURACITE_HOE = item("duracite_hoe", DuraciteTool.Hoe::new);
    public static final ItemDefinition<DuraciteTool.Paxel> DURACITE_PAXEL = item("duracite_paxel", DuraciteTool.Paxel::new);
    public static final ItemDefinition<DuraciteTool.Hammer> DURACITE_HAMMER = item("duracite_hammer", DuraciteTool.Hammer::new);

    public static final ItemDefinition<BaseFood> HONEY_BUN = item("honey_bun", BaseFood.HoneyBun::new);
    public static final ItemDefinition<BaseFood> HARD_BOILED_EGG = item("hard_boiled_egg", BaseFood.HardBoiledEgg::new);

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
