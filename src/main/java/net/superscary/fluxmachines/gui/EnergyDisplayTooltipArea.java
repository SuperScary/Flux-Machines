package net.superscary.fluxmachines.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.superscary.fluxmachines.api.energy.FMEnergyStorage;

import java.util.List;

public class EnergyDisplayTooltipArea {
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private final FMEnergyStorage energy;

    public EnergyDisplayTooltipArea (int xMin, int yMin, FMEnergyStorage energy) {
        this(xMin, yMin, energy, 8, 64);
    }

    public EnergyDisplayTooltipArea (int xMin, int yMin, FMEnergyStorage energy, int width, int height) {
        xPos = xMin;
        yPos = yMin;
        this.width = width;
        this.height = height;
        this.energy = energy;
    }

    public List<Component> getTooltips () {
        return List.of(Component.literal(energy.getEnergyStored() + " / " + energy.getMaxEnergyStored() + " FE"));
    }

    public void render (GuiGraphics guiGraphics) {
        int stored = (int) (height * (energy.getEnergyStored() / (float) energy.getMaxEnergyStored()));
        guiGraphics.fillGradient(xPos, yPos + (height - stored), xPos + width, yPos + height, 0xffb51500, 0xff600b00);
    }

    public void render (GuiGraphics guiGraphics, int x, int y) {
        int stored = (int) (height * (energy.getEnergyStored() / (float) energy.getMaxEnergyStored()));
        guiGraphics.fillGradient(x, y + (height - stored), x + width, y + height, 0xffb51500, 0xff600b00);
    }

}
