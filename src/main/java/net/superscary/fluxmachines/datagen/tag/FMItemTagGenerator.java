package net.superscary.fluxmachines.datagen.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class FMItemTagGenerator extends ItemTagsProvider implements IDataProvider {

    public FMItemTagGenerator (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, FluxMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.@NotNull Provider provider) {

    }

    @Override
    public String getName () {
        return "Kinetic ItemTags";
    }

}
