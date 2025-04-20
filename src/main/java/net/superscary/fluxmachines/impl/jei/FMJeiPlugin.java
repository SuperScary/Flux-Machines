package net.superscary.fluxmachines.impl.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMRecipeManagers;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.util.Utilities;
import net.superscary.fluxmachines.gui.screen.FluxFurnaceScreen;
import net.superscary.fluxmachines.impl.jei.category.FluxFurnaceCategory;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
@SuppressWarnings("unused")
public class FMJeiPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return FluxMachines.getResource("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FluxFurnaceCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(FluxFurnaceCategory.TYPE, FMRecipeManagers.FLUX_SMELTING_RECIPE_MANAGER.get().asRecipeList());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FluxFurnaceScreen.class, 81 + Utilities.guiScaleOffset(), 36, 22, 15, FluxFurnaceCategory.TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(FMBlocks.FLUX_FURNACE), FluxFurnaceCategory.TYPE);
    }

    @Override
    public void registerRuntime (IRuntimeRegistration registration) {
        registration.getRecipeManager().addRecipes(FluxFurnaceCategory.TYPE, FMRecipeManagers.FLUX_SMELTING_RECIPE_MANAGER.get().asRecipeList());
    }

    @Override
    public void onRuntimeAvailable (IJeiRuntime jeiRuntime) {
        jeiRuntime.getRecipeManager().addRecipes(FluxFurnaceCategory.TYPE, FMRecipeManagers.FLUX_SMELTING_RECIPE_MANAGER.get().asRecipeList());
    }

}
