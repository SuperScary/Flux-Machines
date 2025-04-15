package net.superscary.fluxmachines.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class FMServerConfig {

	public static final ModConfigSpec CONFIG_SPEC;

	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

		builder.comment("This is the server config for Flux Machines. This config adjusts many values for the server side of Flux Machines.")
				.define("important", true);

		CONFIG_SPEC = builder.build();
	}

}
