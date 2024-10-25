package net.superscary.fluxmachines.item.material;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMItems;

import java.util.EnumMap;
import java.util.List;

public class FMArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> REGISTRY = DeferredRegister.create(Registries.ARMOR_MATERIAL, FluxMachines.MODID);

    public static final Holder<ArmorMaterial> STEEL = REGISTRY.register("steel", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 7);
                map.put(ArmorItem.Type.HELMET, 2);
                //map.put(ArmorItem.Type.BODY, 4); im guessing this is the shield?
            }), 10, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(FMItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(FluxMachines.getResource("steel"))), 0.5f, .05f));

}
