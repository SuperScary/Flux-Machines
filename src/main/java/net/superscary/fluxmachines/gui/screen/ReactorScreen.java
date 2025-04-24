package net.superscary.fluxmachines.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.menu.ReactorMenu;
import net.superscary.fluxmachines.gui.screen.base.BaseScreen;

public class ReactorScreen extends BaseScreen<ReactorMenu> {

	private Button startStopButton;
	private boolean isRunning = false;

	public ReactorScreen (ReactorMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.imageHeight = 205;
	}

	@Override
	public void init () {
		super.init();

		int guiLeft = (this.width - this.imageWidth) / 2;
		int guiTop = (this.height - this.imageHeight) / 2;

		this.startStopButton = this.addRenderableWidget(
				Button.builder(
								buttonLabel(),
								(button) -> {
									if (isRunning) {
										System.out.println("Stop Reactor");
									} else {
										System.out.println("Start Reactor");
									}

									// flip locally for instant feedback
									isRunning = !isRunning;
									button.setMessage(buttonLabel());
								}
						)
						.bounds(guiLeft + 67, guiTop + 81, 42, 16)
						.build()
		);
	}

	private Component buttonLabel () {
		return Component.translatable(isRunning ? "gui.fluxmachines.reactor.stop" : "gui.fluxmachines.reactor.start");
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
		graphics.drawString(font, title, ((imageWidth / 2) - font.width(title) / 2), titleLabelY - 2, 4210752, false);
		graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, 113, 4210752, false);
	}

	@Override
	public boolean isConfigurable () {
		return false;
	}

}
