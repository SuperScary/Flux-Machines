package net.superscary.fluxmachines.impl.jei.category;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.impl.jei.BaseCategory;
import net.superscary.fluxmachines.impl.jei.util.JTexture;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import org.jetbrains.annotations.NotNull;

public class FluxFurnaceCategory extends BaseCategory<SmeltingRecipe> {

    public FluxFurnaceCategory (IGuiHelper helper) {
        super(helper, FMBlocks.FLUX_FURNACE.item());
    }

    @Override
    public void draw (@NotNull RecipeHolder<SmeltingRecipe> recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        //IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        getDisplayBackground().draw(guiGraphics);
    }

    @Override
    public @NotNull Component getTitle () {
        return Component.translatable("block.fluxmachines.flux_furnace");
    }

    @Override
    public void setRecipe (IRecipeLayoutBuilder builder, RecipeHolder<SmeltingRecipe> recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(54, 34).addIngredients(recipe.value().getIngredients().getFirst());
        builder.addOutputSlot(116, 34).addItemStack(recipe.value().getResultItem(null));
    }

    @Override
    public ResourceLocation getBackgroundTexture () {
        return new JTexture.Appended(FluxMachines.MODID,"flux_furnace").getLocation();
    }

    @Override
    public RecipeType<RecipeHolder<SmeltingRecipe>> type () {
        return RecipeType.create("minecraft", "smelting", RecipeType.createFromVanilla(net.minecraft.world.item.crafting.RecipeType.SMELTING).getRecipeClass());
    }
}
