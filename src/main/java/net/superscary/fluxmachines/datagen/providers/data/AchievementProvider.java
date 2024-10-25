package net.superscary.fluxmachines.datagen.providers.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.superscary.fluxmachines.core.FluxMachines;
import net.superscary.fluxmachines.core.registries.FMBlocks;
import net.superscary.fluxmachines.core.registries.FMItems;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.superscary.fluxmachines.core.registries.FMItems.STEEL_INGOT;
import static net.superscary.fluxmachines.core.registries.FMItems.HARD_BOILED_EGG;

public class AchievementProvider extends AdvancementProvider {

    public AchievementProvider (PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new AdvancementGenerator()));
    }

    private static final class AdvancementGenerator implements AdvancementProvider.AdvancementGenerator {

        static AdvancementHolder main;
        static AdvancementHolder parent;
        static AdvancementHolder strongerThanIron;
        static AdvancementHolder buildCasing;

        @Override
        public void generate (HolderLookup.@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> consumer, @NotNull ExistingFileHelper existingFileHelper) {
            buildParents(consumer, existingFileHelper);

            Advancement.Builder builder = Advancement.Builder.advancement();
            buildDontFeedThatToAChicken(parent, builder, consumer, existingFileHelper);
        }

        private static void buildParents (Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
            main = Advancement.Builder.advancement()
                    .display(FMItems.WRENCH,
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
                    .display(Items.MAP,
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

            strongerThanIron = Advancement.Builder.advancement()
                    .display(STEEL_INGOT,
                            Component.translatable("advancement.fluxmachines.strongerthaniron.title"),
                            Component.translatable("advancement.fluxmachines.strongerthaniron.desc"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .parent(parent)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("has_steel", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(STEEL_INGOT)))
                    .save(consumer, FluxMachines.getResource("advancements/stronger_than_iron"), existingFileHelper);

            buildCasing = Advancement.Builder.advancement()
                    .display(FMBlocks.MACHINE_CASING.block(),
                            Component.translatable("advancement.fluxmachines.justincase.title"),
                            Component.translatable("advancement.fluxmachines.justincase.desc"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .parent(strongerThanIron)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("has_machine_casing", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(FMBlocks.MACHINE_CASING)))
                    .save(consumer, FluxMachines.getResource("advancements/just_in_case"), existingFileHelper);

        }

        /**
         * I honestly only made this as a test. But I'm leaving it in because it's kind of funny?
         *
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
                    true);

            builder.requirements(AdvancementRequirements.Strategy.OR);
            builder.addCriterion("feed_chicken_hbe", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(HARD_BOILED_EGG), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.CHICKEN)))));
            builder.addCriterion("feed_chicken_chicken", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(Items.COOKED_CHICKEN), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.CHICKEN)))));
            builder.addCriterion("feed_chicken_raw_chicken", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(Items.CHICKEN), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.CHICKEN)))));
            builder.save(consumer, FluxMachines.getResource("advancements/dont_feed_that_to_a_chicken"), existingFileHelper);
        }

    }

}
