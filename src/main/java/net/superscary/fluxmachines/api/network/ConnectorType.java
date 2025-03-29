package net.superscary.fluxmachines.api.network;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ConnectorType implements StringRepresentable {

    NONE,
    CABLE,
    BLOCK;

    public static final ConnectorType[] VALUES = values();

    @Override
    @NotNull
    public String getSerializedName() {
        return name().toLowerCase();
    }

}
