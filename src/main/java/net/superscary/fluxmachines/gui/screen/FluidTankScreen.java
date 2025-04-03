package net.superscary.fluxmachines.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.fluids.FluidStack;
import net.superscary.fluxmachines.api.gui.GuiFluid;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.FluidTankRenderer;
import net.superscary.fluxmachines.gui.menu.FluidTankMenu;
import net.superscary.fluxmachines.gui.screen.base.BaseScreen;
import org.jetbrains.annotations.NotNull;

public class FluidTankScreen  extends BaseScreen<FluidTankMenu> implements GuiFluid {

    public FluidTankScreen(FluidTankMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return FluxMachines.getResource("textures/gui/fluid_tank_gui.png");
    }

    @Override
    public void addAdditionalTabElements(GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {

    }

    @Override
    public void addAdditionalScreenElements (@NotNull GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        super.addAdditionalScreenElements(guiGraphics, v, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        getFluidTankRenderer().render(guiGraphics, x + 30, y + 16, getFluidStack());
    }

    @Override
    protected void renderLabels (GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (isFluidMenu()) {
            renderFluidTooltipArea(graphics, mouseX, mouseY, x, y, getFluidStack(), 30, 16, getFluidTankRenderer());
        }
    }

    @Override
    public FluidStack getFluidStack () {
        return menu.blockEntity.getFluid();
    }

    @Override
    public FluidTankRenderer getFluidTankRenderer () {
        return new FluidTankRenderer(menu.blockEntity.getTank(null).getTankCapacity(0), true, 116, 54);
    }
}