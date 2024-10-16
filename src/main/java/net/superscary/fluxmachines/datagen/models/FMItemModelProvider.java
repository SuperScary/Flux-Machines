package net.superscary.fluxmachines.datagen.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.util.item.ItemDefinition;

import static net.superscary.fluxmachines.registries.FMItems.*;

public class FMItemModelProvider extends ItemModelProvider implements IDataProvider {

    public FMItemModelProvider (PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, FluxMachines.MODID, existingFileHelper);
    }

    private static ResourceLocation makeId (String id) {
        return id.contains(":") ? ResourceLocation.parse(id) : FluxMachines.getResource(id);
    }

    @Override
    protected void registerModels () {
        flatSingleLayer(RAW_DURACITE, "item/duracite_raw");
        flatSingleLayer(DURACITE_DUST, "item/duracite_dust");
        flatSingleLayer(DURACITE_INGOT, "item/duracite_ingot");
        flatSingleLayer(DURACITE_NUGGET, "item/duracite_nugget");
    }

    private ItemModelBuilder flatSingleLayer (ItemDefinition<?> item, String texture) {
        String id = item.id().getPath();
        return singleTexture(id, mcLoc("item/generated"), "layer0", makeId(texture));
    }

    private ItemModelBuilder flatSingleLayer (ResourceLocation id, String texture) {
        return singleTexture(id.getPath(), mcLoc("item/generated"), "layer0", makeId(texture));
    }

    private ItemModelBuilder builtInItemModel (String name) {
        var model = getBuilder("item/" + name);
        return model;
    }

}
