package com.reasure.practice.capability;

import net.minecraftforge.energy.EnergyStorage;

/**
 * A custom energy storage implementation that has a few more options and also
 * allows for notifying the parent BE when changes occur
 */
public abstract class CustomEnergyStorage extends EnergyStorage {
    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, 0);
    }

    // Override this to (for example) call setChanged() on your block entity
    protected abstract void onEnergyChanged();

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receive = super.receiveEnergy(maxReceive, simulate);
        if (receive > 0 && !simulate) {
            onEnergyChanged();
        }
        return receive;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extract = super.extractEnergy(maxExtract, simulate);
        if (extract > 0 && !simulate) {
            onEnergyChanged();
        }
        return extract;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        onEnergyChanged();
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > capacity) {
            this.energy = capacity;
        }
        onEnergyChanged();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
        onEnergyChanged();
    }
}
