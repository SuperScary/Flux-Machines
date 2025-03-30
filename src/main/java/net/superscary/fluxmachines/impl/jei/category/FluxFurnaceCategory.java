package net.superscary.fluxmachines.impl.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.recipe.FluxSmeltingRecipe;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.impl.jei.BaseCategory;
import net.superscary.fluxmachines.impl.jei.util.JTexture;
import org.jetbrains.annotations.NotNull;

public class FluxFurnaceCategory extends BaseCategory<FluxSmeltingRecipe> {

    public static final RecipeType<FluxSmeltingRecipe> TYPE = new RecipeType<>(FluxMachines.getResource("fluxsmelting"), FluxSmeltingRecipe.class);

    public FluxFurnaceCategory (IGuiHelper helper) {
        super(helper, FMBlocks.FLUX_FURNACE.item(), TYPE);
    }

    @Override
    public void draw (@NotNull FluxSmeltingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        getDisplayBackground().draw(guiGraphics);
    }

    @Override
    public @NotNull Component getTitle () {
        return Component.translatable("block.fluxmachines.flux_furnace");
    }

    @Override
    public void setRecipe (IRecipeLayoutBuilder builder, FluxSmeltingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 35).addIngredients(recipe.getIngredients().getFirst());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35).addItemStack(recipe.output());
    }

    @Override
    public ResourceLocation getBackgroundTexture () {
        return new JTexture.Appended(FluxMachines.MODID, "flux_furnace").getLocation();
    }

}
