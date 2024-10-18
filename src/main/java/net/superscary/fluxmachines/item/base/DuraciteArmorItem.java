package net.superscary.fluxmachines.item.base;

import net.minecraft.world.item.ArmorItem;
import net.superscary.fluxmachines.item.material.FMArmorMaterials;

public class DuraciteArmorItem extends ArmorItem {

    public DuraciteArmorItem (ArmorTypes armorTypes) {
        super(FMArmorMaterials.DURACITE, armorTypes.getType(), armorTypes.getProperties());
    }

    public static class DuraciteHelmet extends DuraciteArmorItem {
        public DuraciteHelmet (Properties properties) {
            super(ArmorTypes.HELMET);
        }
    }

    public static class DuraciteChestplate extends DuraciteArmorItem {
        public DuraciteChestplate (Properties properties) {
            super(ArmorTypes.CHESTPLATE);
        }
    }

    public static class DuraciteLeggings extends DuraciteArmorItem {
        public DuraciteLeggings (Properties properties) {
            super(ArmorTypes.LEGGINGS);
        }
    }

    public static class DuraciteBoots extends DuraciteArmorItem {
        public DuraciteBoots (Properties properties) {
            super(ArmorTypes.BOOTS);
        }
    }

    public enum ArmorTypes {
        HELMET(Type.HELMET, new Properties().durability(Type.HELMET.getDurability(28))),
        CHESTPLATE(Type.CHESTPLATE, new Properties().durability(Type.CHESTPLATE.getDurability(28))),
        LEGGINGS(Type.LEGGINGS, new Properties().durability(Type.LEGGINGS.getDurability(28))),
        BOOTS(Type.BOOTS, new Properties().durability(Type.BOOTS.getDurability(28)));

        final Type type;
        final Properties properties;

        ArmorTypes (Type type, Properties properties) {
            this.type = type;
            this.properties = properties;
        }

        public Type getType () {
            return type;
        }

        public Properties getProperties () {
            return properties;
        }
    }

}
