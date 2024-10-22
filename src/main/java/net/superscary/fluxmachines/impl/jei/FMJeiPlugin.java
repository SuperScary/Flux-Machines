package net.superscary.fluxmachines.impl.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IModInfoRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.screen.FluxFurnaceScreen;
import net.superscary.fluxmachines.impl.jei.category.FluxFurnaceCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
public class FMJeiPlugin implements IModPlugin {

    @Nullable
    private IRecipeCategory<RecipeHolder<SmeltingRecipe>> fluxFurnaceCategory;

    @Override
    public @NotNull ResourceLocation getPluginUid () {
        return FluxMachines.getResource("jei");
    }

    @Override
    public void registerCategories (IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(fluxFurnaceCategory = new FluxFurnaceCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes (IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<RecipeHolder<SmeltingRecipe>> smeltingRecipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING).stream().toList();
        registration.addRecipes(fluxFurnaceCategory.getRecipeType(), smeltingRecipes);
    }

    @Override
    public void registerGuiHandlers (IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FluxFurnaceScreen.class, 81, 36, 22, 15, fluxFurnaceCategory.getRecipeType());
    }

    @Override
    public void registerModInfo(IModInfoRegistration registration) {
        registration.addModAliases(FluxMachines.MODID, "fm");
    }

}
