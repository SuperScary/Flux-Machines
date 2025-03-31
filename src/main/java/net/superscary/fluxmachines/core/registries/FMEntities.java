package net.superscary.fluxmachines.core.registries;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.sound.FMSounds;
import net.superscary.fluxmachines.core.util.helper.SoundHelper;

public class FMEntities {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, FluxMachines.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, FluxMachines.MODID);

    public static final Holder<PoiType> ENGINEER_POI = POI_TYPES.register("engineer_poi", () -> new PoiType(ImmutableSet.copyOf(FMBlocks.MACHINE_CASING.block().getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> ENGINEER = VILLAGER_PROFESSIONS.register("engineer", () -> new VillagerProfession("engineer", holder -> holder.value() == ENGINEER_POI.value(), poiTypeHolder -> poiTypeHolder.value() == ENGINEER_POI.value(),
            ImmutableSet.of(), ImmutableSet.of(), FMSounds.RATCHET_1.get()));

    public static void register (IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }

}
