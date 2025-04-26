package net.superscary.fluxmachines.core.util.helper;

import net.superscary.fluxmachines.core.config.FMServerConfig;

public class ReactorEfficiency {

	// Optimal setpoints
	private static final double OPT_HEAT = FMServerConfig.REACTOR_OPTIMAL_TEMP.getAsDouble();
	private static final double OPT_COOLANT = FMServerConfig.REACTOR_OPTIMAL_TEMP.getAsDouble();
	private static final double OPT_FUEL = FMServerConfig.REACTOR_OPTIMAL_FUEL.getAsDouble();

	// Tolerances (standard deviations) for Gaussian fall-off
	private static final double SIGMA_HEAT = 20.0;
	private static final double SIGMA_COOLANT = 10.0;
	private static final double SIGMA_FUEL = 15.0;

	/**
	 * Compute the normalized closeness to an optimal value using a Gaussian curve:
	 * exp( - (x - x0)^2 / (2 * sigma^2) )
	 *
	 * @param value   actual reading
	 * @param optimum ideal reading
	 * @param sigma   how quickly score drops (larger = flatter curve)
	 * @return a score between 0.0 and 1.0
	 */
	private static double gaussianScore (double value, double optimum, double sigma) {
		double diff = value - optimum;
		return Math.exp(-(diff * diff) / (2 * sigma * sigma));
	}

	/**
	 * Calculates overall reactor efficiency in percent.
	 *
	 * @param heat    hydraulic heat (e.g., pressure heat)
	 * @param coolant coolant flow rate
	 * @param fuel    fuel feed rate
	 * @return efficiency as a percentage from 0.0 to 100.0
	 */
	public static double calculateEfficiency (double heat, double coolant, double fuel) {
		double heatScore = gaussianScore(heat, OPT_HEAT, SIGMA_HEAT);
		double coolantScore = gaussianScore(coolant, OPT_COOLANT, SIGMA_COOLANT);
		double fuelScore = gaussianScore(fuel, OPT_FUEL, SIGMA_FUEL);

		// Combined score between 0.0 and 1.0
		double combinedScore = heatScore * coolantScore * fuelScore;

		// Scale to percentage
		return combinedScore * 100.0;
	}

}
