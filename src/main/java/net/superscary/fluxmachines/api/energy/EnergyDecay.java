package net.superscary.fluxmachines.api.energy;

import com.google.common.base.Preconditions;

/**
 * Interface for energy decay
 */
public interface EnergyDecay {

    /**
     * Decays energy
     * @param decay the amount of energy to decay
     * @param storage the energy storage
     */
    default void decayEnergy (int decay, FMEnergyStorage storage) {
        if (storage.getEnergyStored() > 0) {
            storage.setStored(storage.getEnergyStored() - decay);
        }
    }

    /**
     * Decays energy
     * @param decay the amount of energy to decay
     * @param storage the energy storage
     * @param maxDecay the maximum amount of energy to decay
     */
    default void decayEnergy (int decay, FMEnergyStorage storage, int maxDecay) {
        if (storage.getEnergyStored() > 0) {
            storage.setStored(storage.getEnergyStored() - Math.min(decay, maxDecay));
        }
    }

    /**
     * Decays energy
     * @param decay the amount of energy to decay
     * @param storage the energy storage
     * @param maxDecay the maximum amount of energy to decay
     * @param minDecay the minimum amount of energy to decay
     */
    default void decayEnergy (int decay, FMEnergyStorage storage, int maxDecay, int minDecay) {
        if (storage.getEnergyStored() > 0) {
            storage.setStored(storage.getEnergyStored() - Math.min(Math.max(decay, minDecay), maxDecay));
        }
    }

    /**
     * Decay percentage out of 100
     * @param chance the chance to decay. 0-100
     * @return true if the chance is met
     */
    default boolean decayChance (int chance) {
        Preconditions.checkArgument(chance >= 0 && chance <= 100, "Chance must be between 0 and 100");
        return Math.random() * 100 < chance;
    }

}
