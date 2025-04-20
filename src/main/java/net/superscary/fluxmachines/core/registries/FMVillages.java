package net.superscary.fluxmachines.core.registries;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.mixins.TemplatePoolAccess;
import net.superscary.fluxmachines.core.sound.FMSounds;
import net.superscary.fluxmachines.core.util.keys.Keys;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FMVillages {

	@EventBusSubscriber(modid = FluxMachines.MODID, bus = EventBusSubscriber.Bus.GAME)
	public static class Structures {

		@SuppressWarnings("SameParameterValue")
		private static void addBuildingToPool (@NotNull Registry<StructureTemplatePool> templatePoolRegistry, @NotNull Registry<StructureProcessorList> processorListRegistry, ResourceLocation poolRL, String nbtPieceRL, int weight) {
			Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(Keys.EMPTY_PROCESSOR_LIST_KEY);

			StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
			if (pool == null) return;

			SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);
			TemplatePoolAccess templatePoolAccess = (TemplatePoolAccess) pool;

			for (int i = 0; i < weight; i++) {
				templatePoolAccess.getTemplates().add(piece);
			}

			List<Pair<StructurePoolElement, Integer>> list = new ArrayList<>(templatePoolAccess.getRawTemplates());
			list.add(new Pair<>(piece, weight));
			templatePoolAccess.setRawTemplates(list);
		}

		@SubscribeEvent
		public static void addNewBuildings (final @NotNull TagsUpdatedEvent event) {
			if (event.getUpdateCause() != TagsUpdatedEvent.UpdateCause.SERVER_DATA_LOAD) return;

			var tempPoolReg = event.getRegistryAccess().registryOrThrow(Registries.TEMPLATE_POOL);
			var procListReg = event.getRegistryAccess().registryOrThrow(Registries.PROCESSOR_LIST);

			for (String biome : new String[]{"plains", "snowy", "savanna", "desert", "taiga"}) {
				for (String type : new String[]{"engineers_house"}) {
					FluxMachines.LOGGER.info("Adding {} to {} village pools.", FluxMachines.getResource("village/houses/" + biome + "_" + type), biome);
					addBuildingToPool(tempPoolReg, procListReg, FluxMachines.getMinecraftResource("village/" + biome + "/houses"), FluxMachines.getResource("village/houses/" + biome + "_" + type).toString(), 250);
				}
			}
		}

	}

	public static class Villagers {

		public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, FluxMachines.MODID);
		public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, FluxMachines.MODID);

		public static final Holder<PoiType> ENGINEER_POI = POI_TYPES.register("engineer_poi", () -> new PoiType(ImmutableSet.copyOf(FMBlocks.CRUCIBLE.block().getStateDefinition().getPossibleStates()), 1, 1));

		public static final Holder<VillagerProfession> ENGINEER = VILLAGER_PROFESSIONS.register("engineer", () -> new VillagerProfession("engineer", holder -> holder.value() == ENGINEER_POI.value(), poiTypeHolder -> poiTypeHolder.value() == ENGINEER_POI.value(),
				ImmutableSet.of(), ImmutableSet.of(), FMSounds.RATCHET_1.get()));

		public static void register (IEventBus eventBus) {
			POI_TYPES.register(eventBus);
			VILLAGER_PROFESSIONS.register(eventBus);
		}
	}

	public static class Trades {

		public static void addCustomTrades (VillagerTradesEvent event) {
			if (event.getType() == Villagers.ENGINEER.value()) {
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

}
