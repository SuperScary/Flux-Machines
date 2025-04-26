package net.superscary.fluxmachines.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class FMServerConfig {

	public static final ModConfigSpec CONFIG_SPEC;

	public static final ModConfigSpec.DoubleValue REACTOR_OPTIMAL_TEMP;
	public static final ModConfigSpec.DoubleValue REACTOR_OPTIMAL_COOLANT;
	public static final ModConfigSpec.DoubleValue REACTOR_OPTIMAL_FUEL;

	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

		builder.comment("This is the server config for Flux Machines. This config adjusts many values for the server side of Flux Machines.")
				.define("important", true);

		REACTOR_OPTIMAL_TEMP = builder.comment("The optimal temperature (in kelvin) for the reactor to run at its peak efficiency.").defineInRange("reactor_optimal_temp", 10_000_000.0, 0.0, Double.MAX_VALUE);
		REACTOR_OPTIMAL_COOLANT = builder.comment("The optimal cooling rate for the reactor to run at its peak efficiency.").defineInRange("reactor_optimal_coolant", 50.0, 0.0, Double.MAX_VALUE);
		REACTOR_OPTIMAL_FUEL = builder.comment("The optimal fuel efficiency for the reactor to run at its peak efficiency.").defineInRange("reactor_optimal_fuel", 75.0, 0.0, Double.MAX_VALUE);

		CONFIG_SPEC = builder.build();
	}

}
