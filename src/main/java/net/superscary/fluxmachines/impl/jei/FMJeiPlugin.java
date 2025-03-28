package net.superscary.fluxmachines.impl.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.screen.FluxFurnaceScreen;
import net.superscary.fluxmachines.impl.jei.category.FluxFurnaceCategory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
public class FMJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid () {
        return FluxMachines.getResource("jei_plugin");
    }

    @Override
    public void registerCategories (IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FluxFurnaceCategory(registration.getJeiHelpers().getGuiHelper()));
        IModPlugin.super.registerCategories(registration);
    }

    @Override
    public void registerRecipes (IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<SmeltingRecipe> smeltingRecipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING).stream().map(RecipeHolder::value).toList();
        //var smeltingRecipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING);
        registration.addRecipes(FluxFurnaceCategory.TYPE, smeltingRecipes);
    }

    @Override
    public void registerGuiHandlers (IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FluxFurnaceScreen.class, 81, 36, 22, 15, FluxFurnaceCategory.TYPE);
    }

}
