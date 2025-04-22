package net.superscary.fluxmachines.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.menu.ReactorMenu;
import net.superscary.fluxmachines.gui.screen.base.BaseScreen;

public class ReactorScreen extends BaseScreen<ReactorMenu> {

	public ReactorScreen (ReactorMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.imageHeight = 205;
	}

	@Override
	public ResourceLocation getGuiTexture () {
		return FluxMachines.getResource("textures/gui/reactor_gui.png");
	}

	@Override
	public void addAdditionalTabElements (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {

	}

	@Override
	public void renderTitles (GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(font, title, ((imageWidth / 2) - font.width(title) / 2) - modifiedWidth(), titleLabelY - 2, 4210752, false);
		//graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - modifiedWidth(), this.inventoryLabelY, 4210752, false);
	}

	@Override
	public boolean isConfigurable () {
		return false;
	}

}
