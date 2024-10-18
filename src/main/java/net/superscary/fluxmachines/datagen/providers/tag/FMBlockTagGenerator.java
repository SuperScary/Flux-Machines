package net.superscary.fluxmachines.datagen.providers.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.registries.FMBlocks.*;

public class FMBlockTagGenerator extends BlockTagsProvider implements IDataProvider {

    public FMBlockTagGenerator (PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FluxMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(MACHINE_CASING.block())
                .add(DURACITE_ORE.block())
                .add(DURACITE_NETHER_ORE.block())
                .add(DURACITE_DEEPSLATE_ORE.block())
                .add(DURACITE_BLOCK_RAW.block())
                .add(DURACITE_BLOCK.block());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(MACHINE_CASING.block())
                .add(DURACITE_ORE.block())
                .add(DURACITE_NETHER_ORE.block())
                .add(DURACITE_DEEPSLATE_ORE.block())
                .add(DURACITE_BLOCK_RAW.block())
                .add(DURACITE_BLOCK.block());
    }

}
