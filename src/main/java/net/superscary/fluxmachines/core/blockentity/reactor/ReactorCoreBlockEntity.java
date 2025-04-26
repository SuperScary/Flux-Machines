package net.superscary.fluxmachines.core.blockentity.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.superscary.fluxmachines.api.inventory.MachineItemStackHandler;
import net.superscary.fluxmachines.attributes.Attribute;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.block.multiblock.Multiblock;
import net.superscary.fluxmachines.core.blockentity.base.FMBasePoweredBlockEntity;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.util.helper.ReactorEfficiency;
import net.superscary.fluxmachines.core.util.keys.Keys;
import net.superscary.fluxmachines.core.util.tags.FMTag;
import net.superscary.fluxmachines.gui.menu.ReactorMenu;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class ReactorCoreBlockEntity extends FMBasePoweredBlockEntity {

	public static final double MAX_HEAT = 20_000_000;

	private double heat;
	private FluidTank coolantTank;
	private FluidTank fuelTank;
	private int tempUnitIndex = 0;
	private TemperatureUnit temperatureUnit;

	private boolean isRunning = false;

	public ReactorCoreBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		this(type, pos, blockState, 20_000_000, Integer.MAX_VALUE);
	}

	public ReactorCoreBlockEntity (BlockEntityType<?> type, BlockPos pos, BlockState blockState, int capacity, int maxReceive) {
		super(type, pos, blockState, Attribute.Builder.of(Keys.MAX_POWER, capacity), Attribute.Builder.of(Keys.MAX_DRAIN, maxReceive));
		assignTanks();
		this.temperatureUnit = TemperatureUnit.getUnit(tempUnitIndex);
	}

	@Override
	public void saveClientData (CompoundTag tag, HolderLookup.Provider registries) {
		tag = coolantTank.writeToNBT(registries, tag);
		tag = fuelTank.writeToNBT(registries, tag);
		tag.putDouble("heat", getHeat());
		tag.putBoolean("running", isRunning());
		super.saveClientData(tag, registries);
	}

	@Override
	public void loadClientData (CompoundTag tag, HolderLookup.Provider registries) {
		super.loadClientData(tag, registries);
		coolantTank.readFromNBT(registries, tag);
		fuelTank.readFromNBT(registries, tag);
		heat = tag.getDouble("heat");
		isRunning = tag.getBoolean("running");
	}

	@Override
	public void tick (Level level, BlockPos pos, BlockState state) {
		testFill();
	}

	// TODO: Purely for testing purposes, remove later
	int counter = 0;
	private void testFill () {
		if (getCoolantTank().isEmpty()) getCoolantTank().setFluid(new FluidStack(Fluids.WATER, 0));

		if (getCoolantTank().getSpace() > 0) {
			getCoolantTank().setFluid(new FluidStack(Fluids.WATER, getCoolantTank().getFluid().getAmount() + 1));
		}

		if (isRunning() && getHeat() < MAX_HEAT) {
			heat += 5_000;
		} else if (!isRunning() && getHeat() > 0) {
			heat -= 500;
		}

		if (getLevel().isClientSide()) {
			if (counter >= 100) {
				FluxMachines.LOGGER.info("Fluid Ports: {}", getFluidPorts().size());
				FluxMachines.LOGGER.info("Redstone Ports: {}", getRedstonePorts().size());
				FluxMachines.LOGGER.info("Power Taps: {}", getPowerTaps().size());
				counter = 0;
			} else {
				counter++;
			}
		}
	}

	@Override
	public MachineItemStackHandler createInventory () {
		return new MachineItemStackHandler(2) {
			@Override
			protected void onContentsChanged (int slot) {
				setChanged();
				assert level != null;
				if (!level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}
		};
	}

	@Override
	public AbstractContainerMenu menu (int id, Inventory playerInventory, Player player) {
		return new ReactorMenu(id, playerInventory, this);
	}

	@Override
	public @NotNull Component getDisplayName () {
		return Component.translatable("multiblock.fluxmachines.reactor");
	}

	public FluidTank getCoolantTank () {
		return this.coolantTank;
	}

	public FluidTank getFuelTank () {
		return this.fuelTank;
	}

	public double getHeat () {
		return this.heat;
	}

	public double getCoolant () {
		return this.getCoolantTank().getFluidAmount();
	}

	public double getFuel () {
		return this.getFuelTank().getFluidAmount();
	}

	public TemperatureUnit getTemperatureUnit () {
		return this.temperatureUnit;
	}

	public Component getHeatDisplay () {
		return switch (temperatureUnit) {
			case FAHRENHEIT -> Component.translatable("multiblock.fluxmachines.reactor.temperature.fahrenheit", Convert.kelvinToFahrenheit(getHeat()));
			case CELSIUS -> Component.translatable("multiblock.fluxmachines.reactor.temperature.celsius", Convert.kelvinToCelsius(getHeat()));
			default -> Component.translatable("multiblock.fluxmachines.reactor.temperature.kelvin", Convert.formatNumber((long) getHeat()));
		};
	}

	public Component getReactorEfficiency () {
		return Component.literal("" + ReactorEfficiency.calculateEfficiency(getHeat(), getCoolant(), getFuel()));
	}

	public int getTempUnitIndex () {
		return this.tempUnitIndex;
	}

	public int setTempUnitIndex (int index) {
		if (index > 2) {
			index = 0;
		}

		if (index < 0) {
			index = 2;
		}


		this.tempUnitIndex = index;
		this.temperatureUnit = TemperatureUnit.getUnit(index);
		return this.tempUnitIndex;
	}

	public void cycleTemperatureUnit () {
		this.tempUnitIndex++;
		if (this.tempUnitIndex > 2) {
			this.tempUnitIndex = 0;
		}

		this.temperatureUnit = TemperatureUnit.getUnit(this.tempUnitIndex);
	}

	public void dumpTank (FluidTank tank) {
		if (tank != null) {
			tank.setFluid(FluidStack.EMPTY);
		}
	}

	protected void assignTanks () {
		this.coolantTank = new FluidTank(64_000) {
			@Override
			protected void onContentsChanged () {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}

			@Override
			public boolean isFluidValid (@NotNull FluidStack stack) {
				return stack.is(FMTag.Fluids.REACTOR_COOLANT);
			}
		};
		this.fuelTank = new FluidTank(64_000) {
			@Override
			protected void onContentsChanged () {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}

			@Override
			public boolean isFluidValid (@NotNull FluidStack stack) {
				return stack.is(FMTag.Fluids.REACTOR_FUEL);
			}
		};
	}

	public boolean toggle () {
		if (isRunning) {
			isRunning = false;
			return false;
		} else {
			isRunning = true;
			return true;
		}
	}

	public boolean isRunning () {
		return isRunning;
	}

	public List<BlockPos> getFluidPorts () {
		return Multiblock.Reactor.findBlocksInPattern(getLevel(), getBlockPos(), (s) -> s.is(FMBlocks.REACTOR_FLUID_PORT.block()));
	}

	public List<BlockPos> getRedstonePorts () {
		return Multiblock.Reactor.findBlocksInPattern(getLevel(), getBlockPos(), (s) -> s.is(FMBlocks.REACTOR_REDSTONE_PORT.block()));
	}

	public List<BlockPos> getPowerTaps () {
		return Multiblock.Reactor.findBlocksInPattern(getLevel(), getBlockPos(), (s) -> s.is(FMBlocks.REACTOR_POWER_TAP.block()));
	}

	public enum TemperatureUnit {
		KELVIN("kelvin"),
		CELSIUS("celsius"),
		FAHRENHEIT("fahrenheit");

		private final String translationKey;

		TemperatureUnit (String translationKey) {
			this.translationKey = translationKey;
		}

		public Component getTranslationKey (Object... args) {
			return Component.translatable("multiblock.fluxmachines.reactor.temperature." + translationKey, args);
		}

		public static TemperatureUnit getUnit (int index) {
			return switch (index) {
				case 1 -> CELSIUS;
				case 2 -> FAHRENHEIT;
				default -> KELVIN;
			};
		}

		public static String getAs (double value, TemperatureUnit from, TemperatureUnit to) {
			if (from == to) return String.valueOf(value);
			return switch (to) {
				case KELVIN -> Convert.kelvinToCelsius(value);
				case CELSIUS -> Convert.celsiusToKelvin(value);
				case FAHRENHEIT -> Convert.fahrenheitToKelvin(value);
			};
		}

		public String getName () {
			return translationKey;
		}

		public String getSymbol (boolean degreeMarker) {
			return switch (this) {
				case KELVIN -> degreeMarker ? "°K" : "K";
				case CELSIUS -> degreeMarker ? "°C" : "C";
				case FAHRENHEIT -> degreeMarker ? "°F" : "F";
			};
		}
	}

	public interface Convert {

		static String formatNumber (long number) {
			if (number < 1000) {
				return String.valueOf(number);
			} else if (number < 10_000) {
				DecimalFormat format = new DecimalFormat("#,###");
				return format.format(number);
			} else if (number < 1_000_000) {
				return String.format("%.1fK", number / 1000.0);
			} else if (number < 1_000_000_000) {
				return String.format("%.1fM", number / 1_000_000.0);
			} else {
				return String.format("%.1fB", number / 1_000_000_000.0);
			}
		}

		static String kelvinToCelsius (double kelvin) {
			return formatNumber((long) (kelvin - 273.15));
		}

		static String kelvinToFahrenheit (double kelvin) {
			return formatNumber((long) ((kelvin - 273.15) * 9 / 5 + 32));
		}

		static String celsiusToKelvin (double celsius) {
			return formatNumber((long) (celsius + 273.15));
		}

		static String celsiusToFahrenheit (double celsius) {
			return formatNumber((long) ((celsius * 9 / 5) + 32));
		}

		static String fahrenheitToKelvin (double fahrenheit) {
			return formatNumber((long) ((fahrenheit - 32) * 5 / 9 + 273.15));
		}

		static String fahrenheitToCelsius (double fahrenheit) {
			return formatNumber((long) ((fahrenheit - 32) * 5 / 9));
		}

	}

}
