package net.superscary.fluxmachines.core;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.superscary.fluxmachines.item.material.FMArmorMaterials;
import net.superscary.fluxmachines.registries.FMBlockEntities;
import net.superscary.fluxmachines.registries.FMBlocks;
import net.superscary.fluxmachines.registries.FMItems;
import net.superscary.fluxmachines.registries.FMMenus;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public abstract class FluxMachinesBase implements FluxMachines {

    static FluxMachinesBase INSTANCE;

    public FluxMachinesBase (IEventBus modEventBus) {
        if (INSTANCE != null) {
            throw new IllegalStateException();
        }
        INSTANCE = this;

        FMArmorMaterials.REGISTRY.register(modEventBus);
        FMBlocks.REGISTRY.register(modEventBus);
        FMItems.REGISTRY.register(modEventBus);
        FMBlockEntities.REGISTRY.register(modEventBus);
        FMMenus.REGISTRY.register(modEventBus);

        modEventBus.addListener(Tab::initExternal);

        modEventBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB) {
                registerCreativeTabs();
            }
        });

        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.addListener(this::onServerAboutToStart);
        NeoForge.EVENT_BUS.addListener(this::serverStopped);
        NeoForge.EVENT_BUS.addListener(this::serverStopping);

    }

    private void registerCreativeTabs () {
        Tab.init(BuiltInRegistries.CREATIVE_MODE_TAB);
    }

    private void commonSetup (FMLCommonSetupEvent event) {
        event.enqueueWork(this::postRegistrationInitialization).whenComplete((res, err) -> {
            if (err != null) {
                //Log.warn(err);
            }
        });
    }

    public void postRegistrationInitialization () {

    }

    private void onServerAboutToStart (final ServerAboutToStartEvent evt) {

    }

    private void serverStopping (final ServerStoppingEvent event) {

    }

    private void serverStopped (final ServerStoppedEvent event) {

    }

    @Override
    public Collection<ServerPlayer> getPlayers () {
        var server = getCurrentServer();

        if (server != null) {
            return server.getPlayerList().getPlayers();
        }

        return Collections.emptyList();
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer () {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
