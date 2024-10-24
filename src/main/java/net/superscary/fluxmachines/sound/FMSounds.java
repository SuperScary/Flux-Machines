package net.superscary.fluxmachines.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;

import java.util.function.Supplier;

public class FMSounds {

    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, FluxMachines.MODID);

    public static final Supplier<SoundEvent> FLUX_FURNACE_ON = register("flux_furnace_on");

    private static Supplier<SoundEvent> register (String name) {
        ResourceLocation id = FluxMachines.getResource(name);
        return REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

}