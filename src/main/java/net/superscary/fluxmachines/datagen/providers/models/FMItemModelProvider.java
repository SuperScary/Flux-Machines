package net.superscary.fluxmachines.datagen.providers.models;

import net.minecraft.core.registries.BuiltInRegistries;
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
import net.superscary.fluxmachines.core.registries.FMUpgrades;
import net.superscary.fluxmachines.core.util.block.BlockDefinition;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;

import java.util.LinkedHashMap;

import static net.superscary.fluxmachines.core.registries.FMBlocks.*;
import static net.superscary.fluxmachines.core.registries.FMItems.*;

public class FMItemModelProvider extends ItemModelProvider implements IDataProvider {

    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

    public FMItemModelProvider (PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, FluxMachines.MODID, existingFileHelper);
    }

    private static ResourceLocation makeId (String id) {
        return id.contains(":") ? ResourceLocation.parse(id) : FluxMachines.getResource(id);
    }

    @Override
    protected void registerModels () {
        handheldItem(STEEL_DUST);
        handheldItem(STEEL_INGOT);
        handheldItem(STEEL_NUGGET);

        handheldItem(RAW_DURACITE);
        handheldItem(DURACITE_DUST);
        handheldItem(DURACITE_INGOT);
        handheldItem(DURACITE_NUGGET);

        handheldItem(RAW_URANIUM);
        handheldItem(URANIUM_DUST);
        handheldItem(URANIUM_INGOT);
        handheldItem(URANIUM_NUGGET);
        handheldItem(REFINED_URANIUM);

        handheldItem(STEEL_SWORD);
        handheldItem(STEEL_PICKAXE);
        handheldItem(STEEL_SHOVEL);
        handheldItem(STEEL_AXE);
        handheldItem(STEEL_HOE);
        handheldItem(STEEL_PAXEL);
        handheldItem(STEEL_HAMMER);
        handheldItem(WRENCH);
        handheldItem(DATA_LINK);

        basicItem(REDSTONE_AND_STEEL);
        basicItem(HONEY_BUN);
        basicItem(HARD_BOILED_EGG);
        basicItem(RUBBER);
        basicItem(INDUSTRIAL_SLAG);
        basicItem(FERTILIZER);
        basicItem(FLUX_POWDER);
        basicItem(CALCITE_DUST);
        basicItem(COKE);

        basicItem(FMUpgrades.EMPTY);

        reactorFluidPortBlock();
        reactorRedstonePortBlock();

        wallItem(REFRACTORY_WALL, REFRACTORY_BRICK);

        blockOff(FLUX_FURNACE);
        blockOff(COAL_GENERATOR);

        simpleBlockItem(CABLE);
        simpleBlockItem(FACADE);
        simpleBlockItem(CRUCIBLE);

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

    private ItemModelBuilder reactorFluidPortBlock () {
        return withExistingParent(REACTOR_FLUID_PORT.id().getPath(), FluxMachines.getResource("block/reactor_fluid_port_out"));
    }

    private ItemModelBuilder reactorRedstonePortBlock () {
        return withExistingParent(REACTOR_REDSTONE_PORT.id().getPath(), FluxMachines.getResource("block/reactor_redstone_port_out"));
    }

    private ItemModelBuilder flatSingleLayer (ItemDefinition<?> item, String texture) {
        String id = item.id().getPath();
        return singleTexture(id, mcLoc("item/generated"), "layer0", makeId(texture));
    }

    private ItemModelBuilder flatSingleLayer (ResourceLocation id, String texture) {
        return singleTexture(id.getPath(), mcLoc("item/generated"), "layer0", makeId(texture));
    }

    private ItemModelBuilder builtInItemModel (String name) {
		return getBuilder("item/" + name);
    }

    public ItemModelBuilder handheldItem (ItemDefinition<?> item) {
        return handheldItem(item.asItem());
    }

    public ItemModelBuilder basicItem (ItemDefinition<?> item) {
        return basicItem(item.asItem());
    }

    private ItemModelBuilder simpleBlockItem (BlockDefinition<?> block) {
        return simpleBlockItem(block.block());
    }

    private void wallItem (BlockDefinition<?> block, BlockDefinition<?> base) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.block()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", FluxMachines.getResource("block/" + BuiltInRegistries.BLOCK.getKey(base.block()).getPath()));
    }

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

}
