package net.superscary.fluxmachines.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.EnergyDisplayTooltipArea;
import net.superscary.fluxmachines.gui.menu.FluxFurnaceMenu;
import net.superscary.fluxmachines.core.util.helper.MouseUtil;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public class FluxFurnaceScreen extends AbstractContainerScreen<FluxFurnaceMenu> {

    private static final ResourceLocation GUI = FluxMachines.getResource("textures/gui/flux_furnace_gui.png");

    private static final int ENERGY_LEFT = 158;
    private static final int ENERGY_WIDTH = 8;
    private static final int ENERGY_TOP = 9;
    private static final int ENERGY_HEIGHT = 64;

    private EnergyDisplayTooltipArea energyInfoArea;

    public FluxFurnaceScreen (FluxFurnaceMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init () {
        super.init();
        assignEnergyInfoArea();
    }

    @Override
    protected void renderBg (GuiGraphics guiGraphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI, x, y, 0, 0, imageWidth, imageHeight);

        renderArrow(guiGraphics, x, y);

        int power = menu.getPower();
        int p = (int) ((power / (float) getEnergyStorage().getMaxEnergyStored()) * ENERGY_HEIGHT);
        int left = leftPos + ENERGY_LEFT;
        int top = topPos + ENERGY_TOP;
        int e_left = left + ENERGY_WIDTH;
        int e_top = top + ENERGY_HEIGHT;
        guiGraphics.fillGradient(e_left, e_top, left, e_top - p, 0xff000000, 0xffff0000);
        guiGraphics.fill(left, top, e_left, e_top - p, 0xff330000);
    }

    public IEnergyStorage getEnergyStorage () {
        return menu.blockEntity.getEnergyStorage();
    }

    @Override
    protected void renderLabels (GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, title, ((imageWidth / 2) - font.width(title) / 2) - modifiedWidth(), titleLabelY, 4210752, false);
        graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderEnergyAreaTooltips(graphics, mouseX, mouseY, x, y);
    }

    @Override
    public void render (@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    public void renderArrow (GuiGraphics graphics, int x, int y) {
        if (menu.blockEntity.isCrafting()) {
            graphics.blit(GUI, x + 80, y + 35, 203, 0, menu.blockEntity.getScaledProgress(), 17);
        }
    }

    protected int modifiedWidth () {
        return imageWidth == 203 ? 15 : 0;
    }

    private void assignEnergyInfoArea () {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.energyInfoArea = new EnergyDisplayTooltipArea(x + 10, y + 9, getEnergyStorage());
    }

    private void renderEnergyAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x, y, ENERGY_LEFT, ENERGY_TOP, ENERGY_WIDTH, ENERGY_HEIGHT)) {
            guiGraphics.renderTooltip(this.font, getEnergyTooltips(), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    private boolean isMouseAboveArea (int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    public List<Component> getEnergyTooltips () {
        DecimalFormat format = new DecimalFormat("#,###");
        return List.of(Component.literal(format.format(menu.getPower()) + " / " + format.format(getEnergyStorage().getMaxEnergyStored()) + " FE"));
    }

}
