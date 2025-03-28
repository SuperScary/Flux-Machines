package net.superscary.fluxmachines.api.network;

public interface NetworkComponent {

    default boolean canBeAnchor () {
        return true;
    }

    default boolean powered () {
        return true;
    }

}
