package net.superscary.fluxmachines.datagen.providers.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.core.FluxMachines;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FMPoiTagGenerator extends PoiTypeTagsProvider {

    public FMPoiTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, FluxMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(FluxMachines.getResource("engineer_poi"));
    }

}
