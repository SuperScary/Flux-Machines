package net.superscary.fluxmachines.datagen.providers.loot;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.superscary.fluxmachines.core.FluxMachines;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static net.superscary.fluxmachines.registries.FMBlocks.*;
import static net.superscary.fluxmachines.registries.FMItems.*;

public class DropProvider extends BlockLootSubProvider {

    private final Map<Block, Function<Block, LootTable.Builder>> overrides = createOverrides();

    @NotNull
    private ImmutableMap<Block, Function<Block, LootTable.Builder>> createOverrides () {
        return ImmutableMap.<Block, Function<Block, LootTable.Builder>>builder()
                .put(DURACITE_ORE.block(), oreBlock(DURACITE_ORE.block(), RAW_DURACITE.asItem()))
                .put(DURACITE_DEEPSLATE_ORE.block(), oreBlock(DURACITE_DEEPSLATE_ORE.block(), RAW_DURACITE.asItem()))
                .put(DURACITE_NETHER_ORE.block(), oreBlock(DURACITE_NETHER_ORE.block(), DURACITE_NUGGET.asItem()))
                .build();
    }

    public DropProvider (HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream().filter(entry -> entry.getLootTable().location().getNamespace().equals(FluxMachines.MODID))
                .toList();
    }

    @Override
    public void generate () {
        for (var block : getKnownBlocks()) {
            add(block, overrides.getOrDefault(block, this::defaultBuilder).apply(block));
        }
    }

    private LootTable.Builder defaultBuilder (Block block) {
        LootPoolSingletonContainer.Builder<?> entry = LootItem.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1f)).add(entry).when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(pool);
    }

    private Function<Block, LootTable.Builder> oreBlock (Block block, Item itemDropped) {
        return b -> createSilkTouchDispatchTable(block,
                LootItem.lootTableItem(itemDropped)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        .apply(ApplyBonusCount.addUniformBonusCount(getEnchantment(Enchantments.FORTUNE)))
                        .apply(ApplyExplosionDecay.explosionDecay()));
    }

    protected final Holder<Enchantment> getEnchantment (ResourceKey<Enchantment> key) {
        return registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
    }

}
