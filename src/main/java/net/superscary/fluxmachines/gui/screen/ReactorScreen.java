package net.superscary.fluxmachines.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.gui.FluidTankRenderer;
import net.superscary.fluxmachines.gui.HeatBarRenderer;
import net.superscary.fluxmachines.gui.menu.ReactorMenu;
import net.superscary.fluxmachines.gui.screen.base.BaseScreen;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReactorScreen extends BaseScreen<ReactorMenu> {

	private Button startStopButton;
	private Button temperatureUnitButton;
	private Button dumpFuelTankButton;
	private Button dumpCoolantTankButton;
	private boolean isRunning;

	private HeatBarRenderer heatBarRenderer;

	public ReactorScreen (ReactorMenu menu, Inventory playerInventory, Component title) {
		super(menu, playerInventory, title);
		this.imageHeight = 205;
		this.isRunning = menu.blockEntity.isRunning();
		this.heatBarRenderer = new HeatBarRenderer(158, 13, 8, 64, menu.blockEntity.getHeat());
	}

	@Override
	public void init () {
		super.init();

		int guiLeft = (this.width - this.imageWidth) / 2;
		int guiTop = (this.height - this.imageHeight) / 2;

		this.startStopButton = this.addRenderableWidget(
				Button.builder(buttonLabel(), (button) -> {
					// flip locally for instant feedback
					isRunning = menu.blockEntity.toggle();
					button.setMessage(buttonLabel());
				}).bounds(guiLeft + 67, guiTop + 81, 42, 16).build());

		this.temperatureUnitButton = this.addRenderableWidget(
				Button.builder(Component.literal(menu.blockEntity.getTemperatureUnit().getSymbol(false)), (button) -> {
					menu.blockEntity.cycleTemperatureUnit();
					button.setMessage(Component.literal(menu.blockEntity.getTemperatureUnit().getSymbol(false)));
				}).bounds(guiLeft + 120, guiTop + 81, 16, 16).build());

		this.dumpFuelTankButton = this.addRenderableWidget(
				Button.builder(Component.literal("Dump"), (button) -> {
					menu.blockEntity.dumpTank(menu.blockEntity.getFuelTank());
					menu.blockEntity.dumpTank(menu.blockEntity.getCoolantTank());
				}).bounds(guiLeft + 67, guiTop + 60, 42, 16).build());

		this.dumpCoolantTankButton = this.addRenderableWidget(
				Button.builder(Component.literal("°"), (button) -> {
					menu.blockEntity.dumpTank(menu.blockEntity.getCoolantTank());
				}).bounds(guiLeft + 22, guiTop + 8, 4, 4).build());
	}

	private Component buttonLabel () {
		return Component.translatable(isRunning ? "gui.fluxmachines.reactor.stop" : "gui.fluxmachines.reactor.start");
	}

	@Override
	public ResourceLocation getGuiTexture () {
		return FluxMachines.getResource("textures/gui/reactor_gui.png");
	}

	@Override
	public void addAdditionalScreenElements (@NotNull GuiGraphics graphics, float v, int mouseX, int mouseY) {
		super.addAdditionalScreenElements(graphics, v, mouseX, mouseY);

		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;

		getFuelTankRenderer().render(graphics, x + 10, y + 13, menu.blockEntity.getFuelTank().getFluid());
		getCoolantTankRenderer().render(graphics, x + 20, y + 13, menu.blockEntity.getCoolantTank().getFluid());
		heatBarRenderer.render(graphics, x + 148, y + 13, menu.blockEntity.getHeat());

		renderFluidTooltipArea(graphics, mouseX, mouseY, x, y, menu.blockEntity.getFuelTank().getFluid(), 10, 13, getFuelTankRenderer(), TooltipType.FUEL);
		renderFluidTooltipArea(graphics, mouseX, mouseY, x, y, menu.blockEntity.getCoolantTank().getFluid(), 20, 13, getCoolantTankRenderer(), TooltipType.COOLANT);
		renderHeatTooltipArea(graphics, mouseX, mouseY, x, y, 148, 13, heatBarRenderer);

	}

	@Override
	public void renderTitles (GuiGraphics graphics, int mouseX, int mouseY) {
		graphics.drawString(font, title, ((imageWidth / 2) - font.width(title) / 2), titleLabelY - 2, 4210752, false);
		graphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, 113, 4210752, false);

		var eff = Component.translatable("multiblock.fluxmachines.reactor.efficiency", menu.blockEntity.getReactorEfficiency());

		graphics.drawString(font, eff, ((imageWidth / 2) - font.width(eff) / 2), titleLabelY + 10, 4210752, false);
		graphics.drawString(font, menu.blockEntity.getHeatDisplay(), ((imageWidth / 2) - font.width(menu.blockEntity.getHeatDisplay()) / 2), titleLabelY + 20, 4210752, false);
	}

	@Override
	public void addAdditionalTabElements (GuiGraphics graphics, int mouseX, int mouseY, int x, int y) {

	}

	@Override
	public boolean isConfigurable () {
		return false;
	}

	protected void renderFluidTooltipArea (GuiGraphics guiGraphics, int pMouseX, int pMouseY, int x, int y, FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer, TooltipType type) {
		var tooltip = new ArrayList<>(List.of(type.translate()));

		if (type == TooltipType.COOLANT || type == TooltipType.FUEL) {
			tooltip.addAll(renderer.getTooltip(stack, TooltipFlag.Default.NORMAL));
		}

		if (type == TooltipType.HEAT) {
			tooltip.add(menu.blockEntity.getHeatDisplay());
		}

		if (isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer.getWidth(), renderer.getHeight())) {
			guiGraphics.renderTooltip(font, tooltip, Optional.empty(), pMouseX, pMouseY);
		}
	}

	protected void renderHeatTooltipArea (GuiGraphics graphics, int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, HeatBarRenderer renderer) {
		var tooltip = new ArrayList<>(List.of(TooltipType.HEAT.translate()));
		tooltip.add(menu.blockEntity.getHeatDisplay());

		if (isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer.getWidth(), renderer.getHeight())) {
			graphics.renderTooltip(font, tooltip, Optional.empty(), pMouseX, pMouseY);
		}
	}

	public FluidTankRenderer getCoolantTankRenderer () {
		return new FluidTankRenderer(menu.blockEntity.getCoolantTank().getTankCapacity(0), true, 8, 64);
	}

	public FluidTankRenderer getFuelTankRenderer () {
		return new FluidTankRenderer(menu.blockEntity.getFuelTank().getTankCapacity(0), true, 8, 64);
	}

	protected enum TooltipType {
		FUEL("multiblock.fluxmachines.reactor.tooltip.fuel"),
		COOLANT("multiblock.fluxmachines.reactor.tooltip.coolant"),
		HEAT("multiblock.fluxmachines.reactor.tooltip.heat");

		private final String tooltip;

		TooltipType (String tooltip) {
			this.tooltip = tooltip;
		}

		public String getTooltip () {
			return tooltip;
		}

		public Component translate () {
			return Component.translatable(getTooltip());
		}
	}

}
