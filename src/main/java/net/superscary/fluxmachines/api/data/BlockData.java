package net.superscary.fluxmachines.api.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public interface BlockData {

    void saveClientData (CompoundTag tag, HolderLookup.Provider registries);

    void loadClientData (CompoundTag tag, HolderLookup.Provider registries);

}
