package net.superscary.fluxmachines.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class FMCommonConfig {

	public static final ModConfigSpec CONFIG_SPEC;

	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

		builder.comment("This is the common config for Flux Machines. This config only adjusts values for the shared values of Flux Machines and does not modify machine values.",
						"To modify machine values, go to fluxmachines-server.")
				.define("important", true);

		CONFIG_SPEC = builder.build();
	}

}
