package net.superscary.fluxmachines.api.energy;

public interface EnergyDecay {

    default void decayEnergy (int decay, FMEnergyStorage storage) {
        if (storage.getEnergyStored() > 0) {
            storage.setStored(storage.getEnergyStored() - decay);
        }
    }

    default void decayEnergy (int decay, FMEnergyStorage storage, int maxDecay) {
        if (storage.getEnergyStored() > 0) {
            storage.setStored(storage.getEnergyStored() - Math.min(decay, maxDecay));
        }
    }

    default void decayEnergy (int decay, FMEnergyStorage storage, int maxDecay, int minDecay) {
        if (storage.getEnergyStored() > 0) {
            storage.setStored(storage.getEnergyStored() - Math.min(Math.max(decay, minDecay), maxDecay));
        }
    }

    default boolean decayChance (int chance) {
        return Math.random() * 100 < chance;
    }

}
