package net.superscary.fluxmachines.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.menu.FluxFurnaceMenu;
import net.superscary.fluxmachines.gui.screen.base.BaseScreen;

public class FluxFurnaceScreen extends BaseScreen<FluxFurnaceMenu> {

    public FluxFurnaceScreen (FluxFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public ResourceLocation getGuiTexture () {
        return FluxMachines.getResource("textures/gui/flux_furnace_gui.png");
    }

    @Override
    public void addAdditionalTabElements(GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {

    }

}
