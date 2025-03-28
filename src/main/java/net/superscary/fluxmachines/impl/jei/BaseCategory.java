package net.superscary.fluxmachines.impl.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseCategory<T extends Recipe<?>> implements IRecipeCategory<T> {

    private final IGuiHelper helper;
    private final ItemDefinition<?> item;
    private final IDrawable background;

    public BaseCategory (IGuiHelper helper, ItemDefinition<?> item) {
        this.helper = helper;
        this.item = item;
        this.background = helper.createDrawable(getBackgroundTexture(), 0, 0, 176, 80);
    }

    @Override
    public @Nullable IDrawable getIcon () {
        return helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, item.stack());
    }

    public IDrawable getDisplayBackground () {
        return background;
    }

    public abstract ResourceLocation getBackgroundTexture ();

}
