package net.superscary.fluxmachines.api.energy;

import net.neoforged.neoforge.energy.EnergyStorage;

public class FMEnergyStorage extends EnergyStorage {

    public FMEnergyStorage(int capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public FMEnergyStorage(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public FMEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public FMEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setStored (int energy) {
        this.energy = energy;
    }

    public void setMaxStorage (int capacity) {
        this.capacity = capacity;
    }

}
