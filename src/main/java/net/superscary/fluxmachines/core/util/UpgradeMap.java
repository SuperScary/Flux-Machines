package net.superscary.fluxmachines.core.util;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.superscary.fluxmachines.core.item.upgrade.UpgradeBase;
import net.superscary.fluxmachines.core.util.item.ItemDefinition;

public record UpgradeMap<T extends BlockEntity>(DeferredBlockEntityType<T> entity, ImmutableList<Pair<ItemDefinition<UpgradeBase>, Integer>> upgrades) {
}
