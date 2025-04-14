package net.superscary.fluxmachines.gui.screen.base;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.superscary.fluxmachines.api.blockentity.Crafter;
import net.superscary.fluxmachines.api.energy.FMEnergyStorage;
import net.superscary.fluxmachines.api.energy.PoweredBlock;
import net.superscary.fluxmachines.api.gui.GuiFluid;
import net.superscary.fluxmachines.api.gui.GuiPower;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.util.helper.MouseUtil;
import net.superscary.fluxmachines.gui.EnergyDisplayTooltipArea;
import net.superscary.fluxmachines.gui.menu.base.BaseMenu;
import net.superscary.fluxmachines.gui.FluidTankRenderer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

public abstract class BaseScreen<T extends BaseMenu<?, ?>> extends AbstractContainerScreen<T> {

    private final int guiOffset;

    private final int energyLeft;
    private final int energyWidth;
    private final int energyTop;
    private final int energyHeight;
    private boolean isSideTabOpen;

    private boolean settingsPanelOpen;
    public static int settingsPanelX;
    public static int settingsPanelY;
    public static int settingsPanelXHalf;

    private EnergyDisplayTooltipArea energyInfoArea;

    private final ResourceLocation sideTabClosed = FluxMachines.getResource("textures/gui/elements/side_tab_closed.png");
    private final ResourceLocation sideTabSelected = FluxMachines.getResource("textures/gui/elements/side_tab_selected.png");
    private final ResourceLocation sideTabOpen = FluxMachines.getResource("textures/gui/elements/side_tab_open.png");
    private final ResourceLocation upgradeSlotGui = FluxMachines.getResource("textures/gui/elements/upgrade_slot.png");

    protected int imageWidth;

    public BaseScreen (T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = menu.isUpgradeable() ? 203 : 176;
        this.guiOffset = menu.isUpgradeable() ? -27 : 0;
        this.energyLeft = 158 - guiOffset / 2;
        this.energyWidth = 8;
        this.energyTop = 9;
        this.energyHeight = 64;
        this.isSideTabOpen = false;
        this.settingsPanelOpen = false;
    }

    @Override
    protected void init () {
        super.init();
        if (isPoweredMenu()) {
            assignEnergyInfoArea();
        }

        settingsPanelX = ((width - imageWidth) / 2) + imageWidth - 14;
        settingsPanelY = ((height - imageHeight) / 2) + imageHeight - 84;
        settingsPanelXHalf = settingsPanelX + (settingsPanelX / 2);
    }

    /**
     * Returns the gui texture to render.
     * @return {@link ResourceLocation} of a gui texture.
     */
    public abstract ResourceLocation getGuiTexture ();

    /**
     * Renders the main background for the screen.
     * @param guiGraphics {@link GuiGraphics}
     * @param v {@link Float}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     */
    @Override
    protected void renderBg (@NotNull GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, getGuiTexture());
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (Minecraft.getInstance().screen == this) {
            if (isMouseAboveArea(mouseX, mouseY, x + imageWidth + guiOffset, y + 82, 0, 0, 12, 84) && !isSideTabOpen) {
                guiGraphics.blit(sideTabSelected, x , y, 0, 0, 256, 256);
            } else if (!isSideTabOpen) {
                guiGraphics.blit(sideTabClosed, x, y, 0, 0, 256, 256);
            }
        }

        toggleSideTab(guiGraphics, mouseX, mouseY, x, y);

        // render main texture after the side tab. Doesn't really matter the order it is rendered in.
        guiGraphics.blit(getGuiTexture(), x, y, 0, 0, imageWidth, imageHeight);

        renderArrow(guiGraphics, x, y);
        renderEnergyArea(guiGraphics);
        addAdditionalScreenElements(guiGraphics, v, mouseX, mouseY);
    }

    /**
     * Handles rendering the labels for objects.
     * @param graphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     */
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

    /**
     * Main render method.
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param delta Delta movement.
     */
    @Override
    public void render (@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    /**
     * Checks for mouse clicks for opening the side tab.
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param button Mouse button pressed
     * @return true if the mouse is clicked in a defined area or the super method.
     */
    @Override
    public boolean mouseClicked (double mouseX, double mouseY, int button) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        if (isMouseAboveArea((int) mouseX, (int) mouseY, x + imageWidth + guiOffset, y, 0, 0, 12, imageHeight) && !isSideTabOpen && button == GLFW.GLFW_MOUSE_BUTTON_1) {
            isSideTabOpen = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * Use for mouse release on the gui
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param button Mouse button pressed
     * @return true if the mouse button is released.
     */
    @Deprecated(forRemoval = true)
    @Override
    public boolean mouseReleased (double mouseX, double mouseY, int button) {
        if (super.mouseReleased(mouseX, mouseY, button)) {
            return true;
        }
        return false;
    }

    /**
     * Toggles the side settings tab menu.
     * @param graphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x Position X
     * @param y Position Y
     */
    public void toggleSideTab (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        if (!isSideTabOpen) return;
        settingsPanelOpen = true;
        menu.blockEntity.setSettingsPanelOpen(true);
        graphics.blit(sideTabOpen, x, y, 0, 0, 256, 256);

        addTabElements(graphics, mouseX, mouseY, x, y);

        if (isMouseAboveArea(mouseX, mouseY, x + imageWidth + guiOffset, y, 0, 0, 256, 256)) {
            // TODO: DO SOMETHING
        } else {
            isSideTabOpen = false;
            settingsPanelOpen = false;
            menu.blockEntity.setSettingsPanelOpen(false);
        }
    }

    /**
     * Internal for rendering the settings tabs elements.
     * @param graphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x Position X
     * @param y Position Y
     */
    private void addTabElements (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {
        graphics.drawCenteredString(this.font, Component.translatable("gui.fluxmachines.gui.settings"), settingsPanelX + 38, settingsPanelY + 6, 0xFFFFFF);
        addAdditionalTabElements(graphics, mouseX, mouseY, x, y);
    }

    /**
     * Currently for rendering custom objects onto the settings pane
     * @param graphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x X position
     * @param y Y position
     */
    public abstract void addAdditionalTabElements(GuiGraphics graphics, int mouseX, int mouseY, int x, int y);

    /**
     * Render additional elements to the screen without overriding the parent.
     * @param guiGraphics {@link GuiGraphics}
     * @param v {@link Float}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     */
    public void addAdditionalScreenElements (@NotNull GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {

    }

    /**
     * Renders the energy info onto the screen.
     * @param guiGraphics {@link GuiGraphics}
     */
    public void renderEnergyArea (GuiGraphics guiGraphics) {
        if (isPoweredMenu()) {
            int left = leftPos + energyLeft + guiOffset;
            int top = topPos + energyTop;
            energyInfoArea.render(guiGraphics, left, top);
        }
    }

    /**
     * Renders the progress arrow for progress crafting.
     * @param graphics {@link GuiGraphics}
     * @param posX X position of the arrow
     * @param posY Y position of the arrow
     */
    public void renderArrow (GuiGraphics graphics, int posX, int posY) {
        if (menu.blockEntity instanceof Crafter<?> entity) {
            if (entity.isCrafting()) {
                graphics.blit(getGuiTexture(), posX + 79, posY + 35, 203, 0, entity.getScaledProgress(), 17);
            }
        }
    }

    /**
     * For centering the text labels on the main gui window.
     * @return 15 offset if the image width is 203
     */
    protected int modifiedWidth () {
        return imageWidth == 203 ? 15 : 0;
    }

    /**
     * Creates the energy info area
     */
    private void assignEnergyInfoArea () {
        this.energyInfoArea = new EnergyDisplayTooltipArea(energyLeft, energyTop, getEnergyStorage(), energyWidth, energyHeight);
    }

    /**
     * Renders the settings area tooltip
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x X position
     * @param y Y position
     */
    private void renderOptionsAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x + imageWidth + guiOffset, y + 82, 0, 0, 12, 84)) {
            guiGraphics.renderTooltip(this.font, getOptionsTooltips(), Optional.empty(), mouseX - x + (guiOffset / 2), mouseY - y);
        }
    }

    /**
     * Renders the energy area tooltip
     * @param guiGraphics {@link GuiGraphics}
     * @param mouseX Mouse Position X
     * @param mouseY Mouse Position Y
     * @param x X position
     * @param y Y position
     */
    private void renderEnergyAreaTooltips (GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        if (isMouseAboveArea(mouseX, mouseY, x, y, energyLeft, energyTop, energyWidth, energyHeight)) {
            guiGraphics.renderTooltip(this.font, getEnergyTooltips(), Optional.empty(), mouseX - x, mouseY - y);
        }
    }

    /**
     * Renders the fluid area tooltip
     * @param guiGraphics Graphics renderer
     * @param pMouseX mouse position x
     * @param pMouseY mouse position y
     * @param x position x
     * @param y position y
     * @param stack {@link FluidStack}
     * @param offsetX offset x
     * @param offsetY offset y
     * @param renderer {@link FluidTankRenderer}
     */
    @SuppressWarnings("SameParameterValue")
    protected void renderFluidTooltipArea (GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y, FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if (isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer.getWidth(), renderer.getHeight())) {
            guiGraphics.renderTooltip(font, renderer.getTooltip(stack, TooltipFlag.NORMAL), Optional.empty(), pMouseX - x, pMouseY - y);
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

    public boolean isFluidMenu () {
        return menu instanceof GuiFluid;
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
