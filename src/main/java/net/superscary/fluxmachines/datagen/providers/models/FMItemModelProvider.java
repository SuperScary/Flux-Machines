package net.superscary.fluxmachines.datagen.providers.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.api.data.IDataProvider;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.util.block.BlockDefinition;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;

import java.util.LinkedHashMap;

import static net.superscary.fluxmachines.core.registries.FMBlocks.*;
import static net.superscary.fluxmachines.core.registries.FMItems.*;

public class FMItemModelProvider extends ItemModelProvider implements IDataProvider {

    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public FMItemModelProvider (PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, FluxMachines.MODID, existingFileHelper);
    }

    private static ResourceLocation makeId (String id) {
        return id.contains(":") ? ResourceLocation.parse(id) : FluxMachines.getResource(id);
    }

    @Override
    protected void registerModels () {
        handheldItem(STEEL_DUST.asItem());
        handheldItem(STEEL_INGOT.asItem());
        handheldItem(STEEL_NUGGET.asItem());
        handheldItem(STEEL_SWORD.asItem());
        handheldItem(STEEL_PICKAXE.asItem());
        handheldItem(STEEL_SHOVEL.asItem());
        handheldItem(STEEL_AXE.asItem());
        handheldItem(STEEL_HOE.asItem());
        handheldItem(STEEL_PAXEL.asItem());
        handheldItem(STEEL_HAMMER.asItem());
        handheldItem(WRENCH.asItem());
        handheldItem(DATA_LINK.asItem());

        basicItem(REDSTONE_AND_STEEL.asItem());
        basicItem(HONEY_BUN.asItem());
        basicItem(HARD_BOILED_EGG.asItem());
        basicItem(RUBBER.asItem());
        basicItem(INDUSTRIAL_SLAG.asItem());
        basicItem(FERTILIZER.asItem());
        basicItem(FLUX_POWDER.asItem());
        basicItem(CALCITE_DUST.asItem());
        basicItem(COKE.asItem());

        blockOff(FLUX_FURNACE);
        blockOff(COAL_GENERATOR);

        simpleBlockItem(CABLE.block());
        simpleBlockItem(FACADE.block());

        trimmedArmorItem(STEEL_HELMET);
        trimmedArmorItem(STEEL_CHESTPLATE);
        trimmedArmorItem(STEEL_LEGGINGS);
        trimmedArmorItem(STEEL_BOOTS);

    }

    private void trimmedArmorItem (ItemDefinition<?> itemDeferredItem) {
        final String MOD_ID = FluxMachines.MODID;

        if (itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.id().getPath(), mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0", ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/" + itemDeferredItem.id().getPath()));
            });
        }
    }

    private ItemModelBuilder blockOff (BlockDefinition<?> block) {
        return withExistingParent(block.id().getPath(), FluxMachines.getResource("block/" + block.id().getPath() + "/" + block.id().getPath() + "_off"));
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
