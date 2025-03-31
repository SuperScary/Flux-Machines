package net.superscary.fluxmachines.core.hooks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.superscary.fluxmachines.core.registries.FMEntities;
import net.superscary.fluxmachines.core.registries.FMItems;

public class VillagerTrades {

    public static void addCustomTrades (VillagerTradesEvent event) {
        if (event.getType() == FMEntities.ENGINEER.value()) {
            var trades = event.getTrades();

            trades.get(1).add(((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.IRON_INGOT, 19),
                    new ItemStack(FMItems.STEEL_HAMMER, 1), 6, 3, 0.05f)
            ));

            trades.get(2).add(((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Blocks.IRON_BLOCK, 2),
                    new ItemStack(FMItems.STEEL_HAMMER, 1), 6, 5, 0.10f)
            ));
        }
    }

    public static void addWandererTrades (WandererTradesEvent event) {
        var generic = event.getGenericTrades();
        var rare = event.getRareTrades();

        generic.add(((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Items.IRON_INGOT, 15),
                new ItemStack(FMItems.STEEL_HAMMER, 1), 6, 3, 0.05f)
        ));

        generic.add(((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Blocks.IRON_BLOCK, 2),
                new ItemStack(FMItems.STEEL_HAMMER, 1), 6, 5, 0.10f)
        ));

        rare.add(((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Items.DIAMOND, 1),
                new ItemStack(FMItems.STEEL_HAMMER, 1), 1, 15, 1.f)
        ));
    }

}
