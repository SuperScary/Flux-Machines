package net.superscary.fluxmachines.registries;

import com.google.common.base.Preconditions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.Tab;
import net.superscary.fluxmachines.item.base.BaseFood;
import net.superscary.fluxmachines.item.base.BaseItem;
import net.superscary.fluxmachines.item.tool.SteelTool;
import net.superscary.fluxmachines.item.tool.Wrench;
import net.superscary.fluxmachines.util.item.ItemDefinition;
import net.superscary.fluxmachines.util.keys.Keys;
import org.jetbrains.annotations.Nullable;
import net.superscary.fluxmachines.item.base.SteelArmorItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class FMItems {

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(FluxMachines.MODID);

    private static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    public static final ItemDefinition<BaseItem> STEEL_DUST = item("steel_dust", BaseItem::new);
    public static final ItemDefinition<BaseItem> STEEL_INGOT = item("steel_ingot", BaseItem::new);
    public static final ItemDefinition<BaseItem> STEEL_NUGGET = item("steel_nugget", BaseItem::new);

    public static final ItemDefinition<SteelArmorItem> STEEL_HELMET = item("steel_helmet", SteelArmorItem.SteelHelmet::new);
    public static final ItemDefinition<SteelArmorItem> STEEL_CHESTPLATE = item("steel_chestplate", SteelArmorItem.SteelChestplate::new);
    public static final ItemDefinition<SteelArmorItem> STEEL_LEGGINGS = item("steel_leggings", SteelArmorItem.SteelLeggings::new);
    public static final ItemDefinition<SteelArmorItem> STEEL_BOOTS = item("steel_boots", SteelArmorItem.SteelBoots::new);
    public static final ItemDefinition<SteelTool.Sword> STEEL_SWORD = item("steel_sword", SteelTool.Sword::new);
    public static final ItemDefinition<SteelTool.Pickaxe> STEEL_PICKAXE = item("steel_pickaxe", SteelTool.Pickaxe::new);
    public static final ItemDefinition<SteelTool.Axe> STEEL_AXE = item("steel_axe", SteelTool.Axe::new);
    public static final ItemDefinition<SteelTool.Shovel> STEEL_SHOVEL = item("steel_shovel", SteelTool.Shovel::new);
    public static final ItemDefinition<SteelTool.Hoe> STEEL_HOE = item("steel_hoe", SteelTool.Hoe::new);
    public static final ItemDefinition<SteelTool.Paxel> STEEL_PAXEL = item("steel_paxel", SteelTool.Paxel::new);
    public static final ItemDefinition<SteelTool.Hammer> STEEL_HAMMER = item("steel_hammer", SteelTool.Hammer::new);

    public static final ItemDefinition<Wrench> WRENCH = item("wrench", Wrench::new);

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
