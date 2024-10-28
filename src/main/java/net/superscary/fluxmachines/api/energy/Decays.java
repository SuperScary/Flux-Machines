package net.superscary.fluxmachines.api.energy;

/**
 * Represents an object that can decay energy.
 */
public interface Decays {

    /**
     * The percentage chance that the energy will decay. Represented as an integer.
     */
    int decayPercentageChance ();

    /**
     * The amount of energy that will be decayed.
     */
    int decayAmount ();

}
