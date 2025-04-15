package net.superscary.fluxmachines.core.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.mixins.TemplatePoolAccess;
import net.superscary.fluxmachines.core.util.keys.Keys;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = FluxMachines.MODID, bus = EventBusSubscriber.Bus.GAME)
public class Villages {

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
	public static void addNewBuilding (final @NotNull TagsUpdatedEvent event) {
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
