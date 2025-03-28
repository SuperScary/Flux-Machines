package net.superscary.fluxmachines.api.energy;

import net.neoforged.neoforge.energy.EnergyStorage;

/**
 * A custom energy storage class that extends {@link EnergyStorage}
 */
public class FMEnergyStorage extends EnergyStorage {

    /**
     * Default constructor
     */
    public FMEnergyStorage(int capacity) {
        this(capacity, capacity, capacity, 0);
    }

    /**
     * Constructor with capacity and max transfer
     */
    public FMEnergyStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    /**
     * Constructor with capacity, max receive, and max extract
     */
    public FMEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    /**
     * Constructor with capacity, max receive, max extract, and energy
     */
    public FMEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    /**
     * Sets the energy stored. Overrides any existing value.
     */
    public void setStored (int energy) {
        this.energy = energy;
    }

    /**
     * Sets the max energy that can be stored.
     */
    public void setMaxStorage (int capacity) {
        this.capacity = capacity;
    }

}
