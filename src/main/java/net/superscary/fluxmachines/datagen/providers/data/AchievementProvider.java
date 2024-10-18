package net.superscary.fluxmachines.datagen.providers.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.registries.FMItems;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.superscary.fluxmachines.registries.FMItems.HARD_BOILED_EGG;

public class AchievementProvider extends AdvancementProvider {

    public AchievementProvider (PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new AdvancementGenerator()));
    }

    private static final class AdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

        static AdvancementHolder main;
        static AdvancementHolder parent;

        @Override
        public void generate (HolderLookup.@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> consumer, @NotNull ExistingFileHelper existingFileHelper) {
            buildParents(consumer, existingFileHelper);

            Advancement.Builder builder = Advancement.Builder.advancement();
            buildDontFeedThatToAChicken(parent, builder, consumer, existingFileHelper);
        }

        private static void buildParents (Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            main = Advancement.Builder.advancement()
                    .display(FMItems.DURACITE_INGOT, // TODO: Change to wrench when its actually made
                            Component.translatable("advancement.fluxmachines.title"),
                            Component.translatable("advancement.fluxmachines.desc"),
                            ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementType.TASK,
                            false,
                            false,
                            false
                    )
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("spawn", PlayerTrigger.TriggerInstance.tick())
                    .save(consumer, FluxMachines.getResource("advancements/root"), existingFileHelper);

            parent = Advancement.Builder.advancement()
                    .display(FMItems.DURACITE_INGOT,
                            Component.translatable("advancement.fluxmachines.newworld.title"),
                            Component.translatable("advancement.fluxmachines.newworld.desc"),
                            null,
                            AdvancementType.TASK,
                            false,
                            false,
                            false
                    )
                    .parent(main)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("spawn", PlayerTrigger.TriggerInstance.tick())
                    .save(consumer, FluxMachines.getResource("advancements/newworld"), existingFileHelper);
        }

        /**
         * I honestly only made this as a test. But I'm leaving it in because it's kind of funny?
         * @param builder
         * @param consumer
         * @param existingFileHelper
         */
        private static void buildDontFeedThatToAChicken (AdvancementHolder parent, Advancement.Builder builder, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            builder.parent(parent);
            builder.display(HARD_BOILED_EGG.stack(),
                    Component.translatable("advancement.fluxmachines.why.title"),
                    Component.translatable("advancement.fluxmachines.why.desc"),
                    null,
                    AdvancementType.GOAL,
                    true,
                    true,
                    false);

            builder.addCriterion("feed_chicken", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(HARD_BOILED_EGG),
                    Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.CHICKEN)))));
            builder.requirements(AdvancementRequirements.allOf(List.of("feed_chicken")));
            builder.save(consumer, FluxMachines.getResource("advancements/dont_feed_that_to_a_chicken"), existingFileHelper);
        }

    }

}
