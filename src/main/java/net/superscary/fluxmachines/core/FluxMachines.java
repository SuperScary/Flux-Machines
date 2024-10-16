package net.superscary.fluxmachines.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.Collection;

public interface FluxMachines {

    String MODID = "fluxmachines";
    String NAME = "Flux Machines";

    static FluxMachines instance () {
        return FluxMachinesBase.INSTANCE;
    }

    static ResourceLocation getResource (String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

    static ResourceLocation getMinecraftResource (String name) {
        return ResourceLocation.withDefaultNamespace(name);
    }

    Collection<ServerPlayer> getPlayers ();

    Level getClientLevel ();

    MinecraftServer getCurrentServer ();

}
