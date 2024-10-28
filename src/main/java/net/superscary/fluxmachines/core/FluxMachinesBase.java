package net.superscary.fluxmachines.core;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.superscary.fluxmachines.core.hook.BlockHook;
import net.superscary.fluxmachines.core.hook.HammerHook;
import net.superscary.fluxmachines.core.hook.RedstoneHook;
import net.superscary.fluxmachines.core.hook.WrenchHook;
import net.superscary.fluxmachines.gui.screen.base.BaseScreen;
import net.superscary.fluxmachines.impl.top.FMTopPlugin;
import net.superscary.fluxmachines.core.item.material.FMArmorMaterials;
import net.superscary.fluxmachines.core.registries.*;
import net.superscary.fluxmachines.core.sound.FMSounds;
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

        registerAll(modEventBus);

        modEventBus.addListener(Tab::initExternal);
        modEventBus.addListener(this::registerCapabilities);

        modEventBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey() == Registries.CREATIVE_MODE_TAB) {
                registerCreativeTabs();
            }
        });

        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.addListener(this::onServerAboutToStart);
        NeoForge.EVENT_BUS.addListener(this::serverStopped);
        NeoForge.EVENT_BUS.addListener(this::serverStopping);
        NeoForge.EVENT_BUS.addListener(WrenchHook::onPlayerUseBlockEvent);
        NeoForge.EVENT_BUS.addListener(HammerHook::onPlayerUseBlockEvent);
        NeoForge.EVENT_BUS.addListener(RedstoneHook::onPlayerUseBlockEvent);
        NeoForge.EVENT_BUS.addListener(BlockHook::place);

    }

    private void registerCreativeTabs () {
        Tab.init(BuiltInRegistries.CREATIVE_MODE_TAB);
    }

    private void registerAll (IEventBus modEventBus) {
        FMArmorMaterials.REGISTRY.register(modEventBus);
        FMDataAttachments.REGISTRY.register(modEventBus);
        FMDataComponents.REGISTRY.register(modEventBus);
        FMBlocks.REGISTRY.register(modEventBus);
        FMItems.REGISTRY.register(modEventBus);
        FMBlockEntities.REGISTRY.register(modEventBus);
        FMMenus.REGISTRY.register(modEventBus);
        FMSounds.REGISTRY.register(modEventBus);
    }

    private void commonSetup (FMLCommonSetupEvent event) {
        event.enqueueWork(this::postRegistrationInitialization).whenComplete((res, err) -> {
            if (err != null) {

            }
        });
        FMTopPlugin.register();
    }

    public void postRegistrationInitialization () {

    }

    private void onServerAboutToStart (final ServerAboutToStartEvent evt) {

    }

    private void serverStopping (final ServerStoppingEvent event) {

    }

    private void serverStopped (final ServerStoppedEvent event) {

    }

    private void registerCapabilities (RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, FMBlockEntities.FLUX_FURNACE.get(), (o, direction) -> o.getInventory());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, FMBlockEntities.FLUX_FURNACE.get(), (o, direction) -> o.getEnergyStorage());
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
