package net.superscary.fluxmachines.core.item.properties;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FoodProperties {

    public static final net.minecraft.world.food.FoodProperties HONEY_BUN = new net.minecraft.world.food.FoodProperties.Builder().nutrition(3).saturationModifier(0.25f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0), 0.30f).alwaysEdible().build();

    public static final net.minecraft.world.food.FoodProperties HARD_BOILED_EGG = new net.minecraft.world.food.FoodProperties.Builder().nutrition(1).saturationModifier(0f).alwaysEdible().build();

}
