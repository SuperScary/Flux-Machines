package net.superscary.fluxmachines.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public interface FluxMachines {

    String MODID = "fluxmachines";
    String NAME = "Flux Machines";

    static Logger LOGGER = LoggerFactory.getLogger(NAME);

    static FluxMachines instance () {
        return FluxMachinesBase.INSTANCE;
    }

    static ResourceLocation getResource (String name) {
        return custom(MODID, name);
    }

    static ResourceLocation getMinecraftResource (String name) {
        return ResourceLocation.withDefaultNamespace(name);
    }

    static ResourceLocation custom (String id, String name) {
        return ResourceLocation.fromNamespaceAndPath(id, name);
    }

    Collection<ServerPlayer> getPlayers ();

    Level getClientLevel ();

    MinecraftServer getCurrentServer ();

}
