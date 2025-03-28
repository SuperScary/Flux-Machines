package net.superscary.fluxmachines.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.util.block.FMBlockStates;
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
    public void addTabElements (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        super.addTabElements(graphics, mouseX, mouseY, x, y);
    }

    @Override
    public void addAdditionalElements (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        var block = menu.block;
        var pos = menu.blockEntity.getBlockPos();
        var state = menu.blockEntity.getBlockState();
        var level = menu.blockEntity.getLevel();

        var b = state.getValue(FMBlockStates.REDSTONE_ON);
        graphics.drawCenteredString(font, "Redstone On: " + b, SETTINGS_PANEL_X_HALF, y + 85, 0xFFFFFF);
        super.addAdditionalElements(graphics, mouseX, mouseY, x, y);
    }
}
