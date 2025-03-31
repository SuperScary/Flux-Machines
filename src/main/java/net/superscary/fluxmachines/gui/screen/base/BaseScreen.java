package net.superscary.fluxmachines.gui.screen.base;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.superscary.fluxmachines.api.blockentity.Crafter;
import net.superscary.fluxmachines.api.data.DataLinkInteract;
import net.superscary.fluxmachines.api.energy.FMEnergyStorage;
import net.superscary.fluxmachines.api.energy.PoweredBlock;
import net.superscary.fluxmachines.api.gui.GuiPower;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.util.helper.MouseUtil;
import net.superscary.fluxmachines.gui.EnergyDisplayTooltipArea;
import net.superscary.fluxmachines.gui.menu.base.BaseMenu;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public abstract class BaseScreen<T extends BaseMenu<?, ?>> extends AbstractContainerScreen<T> {

    private final int ENERGY_LEFT = 158;
    private final int ENERGY_WIDTH = 8;
    private final int ENERGY_TOP = 9;
    private final int ENERGY_HEIGHT = 64;
    private boolean isSideTabOpen = false;

    private boolean settingsPanelOpen = false;
    public static final int SETTINGS_PANEL_WIDTH = 256;
    public static final int SETTINGS_PANEL_HEIGHT = 84;
    public static int SETTINGS_PANEL_X;
    public static int SETTINGS_PANEL_Y;
    public static int SETTINGS_PANEL_X_HALF;
    public static int X;
    public static int Y;

    private EnergyDisplayTooltipArea energyInfoArea;

    private final ResourceLocation sideTabClosed = FluxMachines.getResource("textures/gui/elements/side_tab_closed.png");
    private final ResourceLocation sideTabSelected = FluxMachines.getResource("textures/gui/elements/side_tab_selected.png");
    private final ResourceLocation sideTabOpen = FluxMachines.getResource("textures/gui/elements/side_tab_open.png");
    private final ResourceLocation upgradeSlotGui = FluxMachines.getResource("textures/gui/elements/upgrade_slot.png");

    protected int imageWidth;

    public BaseScreen (T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 203;
    }

    @Override
    protected void init () {
        super.init();
        if (isPoweredMenu()) {
            assignEnergyInfoArea();
        }

        this.imageWidth = 203;
        SETTINGS_PANEL_X = ((width - imageWidth) / 2) + imageWidth - 14;
        SETTINGS_PANEL_Y = ((height - imageHeight) / 2) + imageHeight - 84;
        SETTINGS_PANEL_X_HALF = SETTINGS_PANEL_X + (SETTINGS_PANEL_X / 2);
    }

    public abstract ResourceLocation getGuiTexture ();

    @Override
    protected void renderBg (@NotNull GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, getGuiTexture());
        int x = X = (width - imageWidth) / 2;
        int y = Y = (height - imageHeight) / 2;

        int tabHeight = 84;
        int offset = 14;

        if (Minecraft.getInstance().screen == this) {
            if (isMouseAboveArea(mouseX, mouseY, x + imageWidth - offset, y + 82, 0, 0, 12, tabHeight) && !isSideTabOpen) {
                guiGraphics.blit(sideTabSelected, x + offset, y, 0, 0, 256, 256);
            } else if (!isSideTabOpen) {
                guiGraphics.blit(sideTabClosed, x + offset, y, 0, 0, 256, 256);
            }
        }

        toggleSideTab(guiGraphics, mouseX, mouseY, x, y);

        // render main texture
        guiGraphics.blit(getGuiTexture(), x + offset, y, 0, 0, imageWidth, imageHeight);

        renderArrow(guiGraphics, x - offset, y);
        renderEnergyArea(guiGraphics);
    }

    @Override
    protected void renderLabels (GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, title, ((imageWidth / 2) - font.width(title) / 2) - modifiedWidth(), titleLabelY, 4210752, false);
        graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (!isSideTabOpen) {
            renderOptionsAreaTooltips(graphics, mouseX, mouseY, x, y);
        }

        if (isPoweredMenu()) {
            renderEnergyAreaTooltips(graphics, mouseX, mouseY, x, y);
        }
    }

    @Override
    public void render (@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked (double mouseX, double mouseY, int button) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if (isMouseAboveArea((int) mouseX, (int) mouseY, x + imageWidth - 14, y, 0, 0, 12, imageHeight) && !isSideTabOpen && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            isSideTabOpen = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased (double mouseX, double mouseY, int button) {
        if (super.mouseReleased(mouseX, mouseY, button)) {
            return true;
        }
        return false;
    }

    public void toggleSideTab (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        if (!isSideTabOpen) return;
        settingsPanelOpen = true;
        menu.blockEntity.setSettingsPanelOpen(true);
        graphics.blit(sideTabOpen, x + 14, y, 0, 0, 256, 256);

        addTabElements(graphics, mouseX, mouseY, x, y);

        if (isMouseAboveArea(mouseX, mouseY, x + imageWidth - 14, y, 0, 0, 256, 256)) {
            // DO SOMETHING
        } else {
            isSideTabOpen = false;
            settingsPanelOpen = false;
            menu.blockEntity.setSettingsPanelOpen(false);
        }
    }

    private void addTabElements (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        graphics.drawCenteredString(this.font, Component.translatable("gui.fluxmachines.gui.settings"), SETTINGS_PANEL_X + 38, SETTINGS_PANEL_Y + 6, 0xFFFFFF);
        addAdditionalElements(graphics, mouseX, mouseY, x, y);
    }

    /**
     * Currently for rendering custom objects onto the settings pane
     * @param graphics
     * @param mouseX
     * @param mouseY
     * @param x
     * @param y
     */
    public abstract void addAdditionalElements (GuiGraphics graphics, int mouseX, int mouseY, int x, int y);

    public void renderEnergyArea (GuiGraphics guiGraphics) {
        if (isPoweredMenu()) {
            int left = leftPos + ENERGY_LEFT;
            int top = topPos + ENERGY_TOP;
            energyInfoArea.render(guiGraphics, left, top);
        }
    }

    public void renderArrow (GuiGraphics graphics, int posX, int posY) {
        if (menu.blockEntity instanceof Crafter<?> entity) {
            if (entity.isCrafting()) {
                graphics.blit(getGuiTexture(), posX + 79 + 27, posY + 35, 203, 0, entity.getScaledProgress(), 17);
            }
        }
    }

    protected int modifiedWidth () {
        return imageWidth == 203 ? 15 : 0;
    }

    private void assignEnergyInfoArea () {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.energyInfoArea = new EnergyDisplayTooltipArea(ENERGY_LEFT + 14, ENERGY_TOP, getEnergyStorage(), ENERGY_WIDTH, ENERGY_HEIGHT);
    }

    private void renderOptionsAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x + imageWidth - 14, y + 82, 0, 0, 12, 84)) {
            guiGraphics.renderTooltip(this.font, getOptionsTooltips(), Optional.empty(), mouseX - x - 14, mouseY - y);
        }
    }

    private void renderEnergyAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x, y, ENERGY_LEFT + 14, ENERGY_TOP, ENERGY_WIDTH, ENERGY_HEIGHT)) {
            guiGraphics.renderTooltip(this.font, getEnergyTooltips(), Optional.empty(), mouseX - x + 14, mouseY - y);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private boolean isMouseAboveArea (int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    public List<Component> getEnergyTooltips () {
        GuiPower power = (GuiPower) menu;
        DecimalFormat format = new DecimalFormat("#,###");
        return List.of(Component.literal(format.format(power.getPower()) + " / " + format.format(getEnergyStorage().getMaxEnergyStored()) + " FE"));
    }

    public List<Component> getOptionsTooltips () {
        return List.of(Component.translatable("gui.fluxmachines.gui.settings.left"));
    }

    public FMEnergyStorage getEnergyStorage () {
        if (menu.blockEntity instanceof PoweredBlock entity) {
            return entity.getEnergyStorage();
        }
        return null;
    }

    public boolean isPoweredMenu () {
        return menu instanceof GuiPower;
    }

    @SuppressWarnings("unused")
    public EnergyDisplayTooltipArea getEnergyInfoArea () {
        return energyInfoArea;
    }

    public ResourceLocation getUpgradeSlotGui () {
        return upgradeSlotGui;
    }

    public boolean isSettingsPanelOpen () {
        return settingsPanelOpen;
    }

}
