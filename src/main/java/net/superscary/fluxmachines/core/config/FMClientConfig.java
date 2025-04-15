package net.superscary.fluxmachines.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.*;

public class FMClientConfig {

	public static final ModConfigSpec CONFIG_SPEC;

	public static final BooleanValue renderCrucibleItems;

	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

		builder.comment("This is the client config for Flux Machines. This config only adjusts values for the client side of Flux Machines and does not modify machine values.",
		"To modify machine values, go to fluxmachines-server.")
				.define("important", true);

		renderCrucibleItems = builder.comment("Render items in the crucible.").define("renderCrucibleItems", true);

		CONFIG_SPEC = builder.build();
	}
}
