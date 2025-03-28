package net.superscary.fluxmachines.core.hooks;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.superscary.fluxmachines.datagen.providers.recipes.FluxSmeltingRecipes;

public class BuildRecipeHooks {

    public static void buildRecipes (EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player) {

        }
    }

}
