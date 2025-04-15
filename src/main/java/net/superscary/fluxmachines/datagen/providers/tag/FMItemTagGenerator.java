package net.superscary.fluxmachines.datagen.providers.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMItems;
import net.superscary.fluxmachines.core.util.tags.FMTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

import static net.superscary.fluxmachines.core.registries.FMItems.*;

public class FMItemTagGenerator extends ItemTagsProvider implements IDataProvider {

    public FMItemTagGenerator (PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, FluxMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags (HolderLookup.@NotNull Provider provider) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(FMItems.STEEL_HELMET.asItem())
                .add(FMItems.STEEL_CHESTPLATE.asItem())
                .add(FMItems.STEEL_LEGGINGS.asItem())
                .add(FMItems.STEEL_BOOTS.asItem());

        this.tag(ItemTags.CHICKEN_FOOD)
                .add(FMItems.HARD_BOILED_EGG.asItem());

        this.tag(Tags.Items.FOODS)
                .add(FMItems.HARD_BOILED_EGG.asItem())
                .add(FMItems.HONEY_BUN.asItem());

        this.tag(FMTag.Items.WRENCH)
                .add(WRENCH.asItem());

        this.tag(FMTag.Items.HAMMER)
                .add(STEEL_HAMMER.asItem());

        this.tag(Tags.Items.INGOTS)
                .add(STEEL_INGOT.asItem())
                .add(DURACITE_INGOT.asItem());

        this.tag(FMTag.Items.DUST)
                .add(STEEL_DUST.asItem())
                .add(DURACITE_DUST.asItem());

        this.tag(Tags.Items.NUGGETS)
                .add(STEEL_NUGGET.asItem())
                .add(DURACITE_NUGGET.asItem());
    }

    @Override
    public @NotNull String getName () {
        return "Flux Machines ItemTags";
    }

}
