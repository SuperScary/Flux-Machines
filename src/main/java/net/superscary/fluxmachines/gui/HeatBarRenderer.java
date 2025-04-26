package net.superscary.fluxmachines.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.superscary.fluxmachines.core.blockentity.reactor.ReactorCoreBlockEntity;

public class HeatBarRenderer extends BarRenderer {

	private double amount;
	private double maxAmount = ReactorCoreBlockEntity.MAX_HEAT;

	public HeatBarRenderer (int xPos, int yPos, int width, int height, double amount) {
		super(xPos, yPos, width, height);
		this.amount = amount;
	}

	@Override
	public void render (GuiGraphics guiGraphics) {
		int stored = (int) (getHeight() * (amount / (float) maxAmount));
		guiGraphics.fillGradient(getXPos(), getYPos() + (getHeight() - stored), getXPos() + getWidth(), getYPos() + getHeight(), Color.ORANGE.getArgb(), Color.BRIGHT_RED.getArgb());
	}

	public void render (GuiGraphics guiGraphics, int x, int y, double amount) {
		int stored = (int) (getHeight() * (amount / (float) maxAmount));
		guiGraphics.fillGradient(x, y + (getHeight() - stored), x + getWidth(), y + getHeight(), Color.ORANGE.getArgb(), Color.BRIGHT_RED.getArgb());
	}
}
