package net.superscary.fluxmachines.datagen.providers.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.sound.FMSounds;
import org.jetbrains.annotations.NotNull;

public class SoundProvider extends SoundDefinitionsProvider implements IDataProvider {

    public SoundProvider (PackOutput output, ExistingFileHelper helper) {
        super(output, FluxMachines.MODID, helper);
    }

    @Override
    public @NotNull String getName () {
        return "Flux Machines Sounds";
    }

    @Override
    public void registerSounds () {
        add(FMSounds.FLUX_FURNACE_ON, SoundDefinition.definition()
                .with(sound("fluxmachines:flux_furnace_on", SoundDefinition.SoundType.SOUND)
                        .volume(0.75f).pitch(1.0f).weight(1).stream(true))
                .subtitle("sound.fluxmachines.flux_furnace_on").replace(true));

        add(FMSounds.RATCHET_1, SoundDefinition.definition()
                .with(sound("fluxmachines:ratchet_1", SoundDefinition.SoundType.SOUND)
                        .volume(1.f).pitch(1.f).weight(1).stream(true))
                .subtitle("sound.fluxmachines.ratchet").replace(true));

        add(FMSounds.RATCHET_2, SoundDefinition.definition()
                .with(sound("fluxmachines:ratchet_2", SoundDefinition.SoundType.SOUND)
                        .volume(1.f).pitch(1.f).weight(1).stream(true))
                .subtitle("sound.fluxmachines.ratchet").replace(true));
    }
}
