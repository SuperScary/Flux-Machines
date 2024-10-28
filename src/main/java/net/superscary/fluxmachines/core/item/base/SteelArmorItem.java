package net.superscary.fluxmachines.core.item.base;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.superscary.fluxmachines.core.item.material.FMArmorMaterials;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class SteelArmorItem extends ArmorItem {

    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>>())
                    .put(FMArmorMaterials.STEEL,
                            List.of(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 36_000, 1, false, false)))
                    .build();

    public SteelArmorItem (ArmorTypes armorTypes) {
        super(FMArmorMaterials.STEEL, armorTypes.getType(), armorTypes.getProperties());
    }

    @Override
    public void appendHoverText (@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(Component.translatable("armor.status.effect.tooltip"));
        tooltipComponents.add(Component.translatable("armor.status.effect.kb2.tooltip"));
    }

    @Override
    public void inventoryTick (@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player) {
            if (!pLevel.isClientSide() && hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffects(player);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


    private void evaluateArmorEffects (Player player) {
        for (Map.Entry<Holder<ArmorMaterial>, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            Holder<ArmorMaterial> mapArmorMaterial = entry.getKey();
            List<MobEffectInstance> mapEffect = entry.getValue();

            if (hasPlayerCorrectArmorOn(mapArmorMaterial, player)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    private void addEffectToPlayer (Player player, List<MobEffectInstance> mapEffect) {
        boolean hasPlayerEffect = mapEffect.stream().anyMatch(effect -> player.hasEffect(effect.getEffect()));

        if (!hasPlayerEffect) {
            for (MobEffectInstance effect : mapEffect) {
                player.addEffect(new MobEffectInstance(effect.getEffect(),
                        effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.isVisible()));
            }
        }
    }

    private boolean hasPlayerCorrectArmorOn (Holder<ArmorMaterial> mapArmorMaterial, Player player) {
        for (ItemStack armorStack : player.getArmorSlots()) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return boots.getMaterial() == mapArmorMaterial && leggings.getMaterial() == mapArmorMaterial
                && chestplate.getMaterial() == mapArmorMaterial && helmet.getMaterial() == mapArmorMaterial;
    }

    private boolean hasFullSuitOfArmorOn (Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }

    public static class SteelHelmet extends SteelArmorItem {
        public SteelHelmet (Properties properties) {
            super(ArmorTypes.HELMET);
        }
    }

    public static class SteelChestplate extends SteelArmorItem {
        public SteelChestplate (Properties properties) {
            super(ArmorTypes.CHESTPLATE);
        }
    }

    public static class SteelLeggings extends SteelArmorItem {
        public SteelLeggings (Properties properties) {
            super(ArmorTypes.LEGGINGS);
        }
    }

    public static class SteelBoots extends SteelArmorItem {
        public SteelBoots (Properties properties) {
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
