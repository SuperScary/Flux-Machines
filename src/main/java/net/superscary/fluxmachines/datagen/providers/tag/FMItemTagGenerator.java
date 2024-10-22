package net.superscary.fluxmachines.datagen.providers.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.registries.FMItems;
import net.superscary.fluxmachines.util.tags.FMTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.registries.FMItems.WRENCH;

public class FMItemTagGenerator extends ItemTagsProvider implements IDataProvider {

    public FMItemTagGenerator (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, FluxMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.@NotNull Provider provider) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(FMItems.DURACITE_HELMET.asItem())
                .add(FMItems.DURACITE_CHESTPLATE.asItem())
                .add(FMItems.DURACITE_LEGGINGS.asItem())
                .add(FMItems.DURACITE_BOOTS.asItem());

        this.tag(ItemTags.CHICKEN_FOOD)
                .add(FMItems.HARD_BOILED_EGG.asItem());

        this.tag(Tags.Items.FOODS)
                .add(FMItems.HARD_BOILED_EGG.asItem())
                .add(FMItems.HONEY_BUN.asItem());

        this.tag(FMTag.Items.WRENCH)
                .add(WRENCH.asItem());
    }

    @Override
    public String getName () {
        return "Kinetic ItemTags";
    }

}
