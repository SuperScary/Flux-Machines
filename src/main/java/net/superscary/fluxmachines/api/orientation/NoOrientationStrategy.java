package net.superscary.fluxmachines.api.orientation;

import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.Collections;

class NoOrientationStrategy implements IOrientationStrategy {
    @Override
    public Collection<Property<?>> getProperties () {
        return Collections.emptyList();
    }
}
